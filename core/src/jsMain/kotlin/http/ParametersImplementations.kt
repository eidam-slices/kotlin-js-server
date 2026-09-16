package cz.eidam.kotlinjs.server.http

import cz.eidam.kotlinjs.server.http.collections.AbstractMutableMultimap
import cz.eidam.kotlinjs.server.http.collections.Multimap
import cz.eidam.kotlinjs.server.http.collections.MultimapValidator

object EmptyParameters: Parameters {
    override val entries: Set<Multimap.Entry> = emptySet()
    override val names: Set<String> = emptySet()

    override val size: Int = 0
    override fun isEmpty(): Boolean = true

    override fun getMulti(name: String): List<String>? = null
    override fun getOne(name: String): String? = null

    override fun contains(name: String): Boolean = false

    override fun equals(other: Any?): Boolean = other is Parameters && other.isEmpty()
    override fun hashCode(): Int = 0
    override fun toString(): String = "Parameters []"
}

class SingleParameters(
    val name: String,
    val values: List<String>,
    validate: Boolean = true
): Parameters {

    private val validator: MultimapValidator
        get() = ParametersValidator

    init {
        if (validate) {
            validator.validateName(name)
            validator.validateValuesSize(values)
            validator.validateValues(values)
        }
    }

    override val entries: Set<Multimap.Entry> = setOf(Multimap.Entry(name, values))
    override val names: Set<String> = setOf(name)

    override val size: Int = 1
    override fun isEmpty(): Boolean = false

    override fun getMulti(name: String): List<String>? {
        return if (matches(name)) values else null
    }

    override fun contains(name: String): Boolean {
        return matches(name)
    }

    private fun matches(name: String): Boolean {
        return name.equals(this.name, ignoreCase = false)
    }

    override fun hashCode(): Int {
        return name.hashCode() * 31 + values.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Parameters) return false
        if (this.size != other.size) return false
        return values == other.getMulti(name)
    }

    override fun toString(): String = buildString {
        append("Parameters [")
        append(name)
        append('=')
        if (values.size > 1) append(values)
        else append(values.first())
        append(']')
    }
}

class MapMutableParameters: AbstractMutableMultimap(caseSensitive = true), MutableParameters {

    override val validator: MultimapValidator
        get() = ParametersValidator

    fun build(): Parameters {
        return when (this.size) {
            0 -> EmptyParameters
            1 -> {
                val (name, values) = map.values.first()
                SingleParameters(name, values, validate = false)
            }
            else -> this
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Parameters) return false
        if (this.size != other.size) return false

        for ((name, values) in map.values) {
            val otherValues = other.getMulti(name) ?: return false
            if (values != otherValues) return false
        }
        return true
    }

    override fun hashCode(): Int {
        return map.entries.sumOf { (key, entry) ->
            key.hashCode() * 31 + entry.values.hashCode()
        }
    }

    override fun toString(): String = map.values.joinToString(
        separator = ", ", prefix = "Parameters [", postfix = "]"
    ) { (name, values) ->
        "$name=" + if (values.size > 1) "$values" else values.first()
    }
}