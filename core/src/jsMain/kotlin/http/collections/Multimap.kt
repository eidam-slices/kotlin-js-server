@file:Suppress("Unused")

package cz.eidam.kotlinjs.server.http.collections

interface Multimap {
    val entries: Set<Entry>
    val names: Set<String>

    val size: Int
    fun isEmpty(): Boolean = size == 0

    fun getMulti(name: String): List<String>?
    fun getOne(name: String): String? = getMulti(name)?.firstOrNull()

    operator fun contains(name: String): Boolean
    operator fun iterator(): Iterator<Entry> = entries.iterator()

    open class Entry(
        open val name: String,
        open val values: List<String>,
    ) {
        operator fun component1() = name
        operator fun component2() = values

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Entry) return false
            return name == other.name && values == other.values
        }

        override fun hashCode(): Int = name.hashCode() xor values.hashCode()

        override fun toString(): String = "Entry(name=$name, values=$values)"
    }

    /* Convention Aliases */
    val multi: MultimapMultiView
        get() = MultimapMultiView(this)

    fun isNotEmpty(): Boolean = !isEmpty()

    operator fun get(name: String): String? = getOne(name)

    @Deprecated("Please use getMulti(), or multi[name] instead.", ReplaceWith("multi[name]"))
    fun getAll(name: String): List<String>? = getMulti(name)

    value class MultimapMultiView internal constructor(private val multimap: Multimap) {
        operator fun get(name: String): List<String>? = multimap.getMulti(name)
    }
}

interface MutableMultimap: Multimap {

    fun append(name: String, value: String)
    fun append(name: String, values: Iterable<String>)

    @Deprecated("Use append(name, values) instead.", ReplaceWith("append(name, values)"))
    fun appendAll(name: String, values: Iterable<String>) = append(name, values)


    fun unify(name: String, value: String)
    fun unify(name: String, values: Iterable<String>)

    operator fun set(name: String, value: String)
    operator fun set(name: String, values: Iterable<String>)

    fun remove(name: String): List<String>?

    fun clear()
}

interface MultimapValidator {
    fun validateName(name: String)
    fun validateValue(value: String)
    fun validateValues(values: Collection<String>) {
        for (value in values) {
            validateValue(value)
        }
    }

    fun validateValuesSize(values: Collection<String>)


    object None: MultimapValidator {
        override fun validateName(name: String) {}
        override fun validateValue(value: String) {}
        override fun validateValues(values: Collection<String>) {}
        override fun validateValuesSize(values: Collection<String>) {}
    }
}