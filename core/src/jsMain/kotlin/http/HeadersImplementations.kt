package cz.eidam.kotlinjs.server.http

import cz.eidam.kotlinjs.server.http.collections.AbstractMutableMultimap
import cz.eidam.kotlinjs.server.http.collections.Multimap
import cz.eidam.kotlinjs.server.http.collections.MultimapValidator

object EmptyHeaders: Headers {
    override val size: Int = 0
    override val entries: Set<Multimap.Entry> = emptySet()
    override val names: Set<String> = emptySet()

    override fun isEmpty(): Boolean = true

    override fun getOne(name: String): String? = null
    override fun getMulti(name: String): List<String>? = null

    override operator fun contains(name: String): Boolean = false

    override fun equals(other: Any?): Boolean = other is Headers && other.isEmpty()
    override fun hashCode(): Int = 0
    override fun toString(): String = "Headers []"
}

class SingleHeaders(
    val name: String,
    val values: List<String>,
    validate: Boolean = true
): Headers {

    private val validator: MultimapValidator
        get() = HeadersValidator

    init {
        if (validate) {
            validator.validateName(name)
            validator.validateValuesSize(values)
            validator.validateValues(values)
        }
    }

    override val entries: Set<Multimap.Entry> = setOf(Multimap.Entry(name, values))
    override val names: Set<String> = object: AbstractSet<String>() {
        override val size: Int get() = this@SingleHeaders.size
        override fun iterator(): Iterator<String> = listOf(name).iterator()
        override operator fun contains(element: String): Boolean = matches(element)
    }

    override val size: Int = 1
    override fun isEmpty(): Boolean = false

    override fun getMulti(name: String): List<String>? {
        return if (matches(name)) values else null
    }

    override operator fun contains(name: String): Boolean {
        return matches(name)
    }

    private fun matches(name: String): Boolean {
        return name.equals(this.name, ignoreCase = true)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Headers) return false
        if (other.size != size) return false
        return other.getMulti(name) == values
    }

    override fun hashCode(): Int {
        return (name.lowercase().hashCode() * 31) + values.hashCode()
    }

    override fun toString(): String {
        return buildString {
            append("Headers [")
            append(name)
            append("=")
            if (values.size == 1) append(values.first())
            else append(values)
            append("]")
        }
    }
}

class MapMutableHeaders: MutableHeaders, AbstractMutableMultimap(caseSensitive = false) {

    override val validator: MultimapValidator
        get() = HeadersValidator

    fun build(): Headers {
        return when (this.size) {
            0 -> EmptyHeaders
            1 -> {
                val (name, values) = map.values.first()
                SingleHeaders(name, values, validate = false)
            }
            else -> this
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Headers) return false
        if (other.size != this.size) return false

        for ((name, values) in map.values) {
            val otherValues = other.getMulti(name) ?: return false
            if (values != otherValues) return false
        }
        return true
    }

    override fun hashCode(): Int {
        return this.map.entries.sumOf { (key, entry) ->
            key.hashCode() * 31 + entry.values.hashCode()
        }
    }

    override fun toString(): String {
        return map.values.joinToString(
            prefix = "Headers [",
            postfix = "]",
            separator = ", "
        ) { (name, values) ->
            "$name=" + if (values.size > 1) "$values" else values.first()
        }
    }
}