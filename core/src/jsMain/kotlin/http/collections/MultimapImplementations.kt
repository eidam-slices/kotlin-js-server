package cz.eidam.kotlinjs.server.http.collections

abstract class AbstractMutableMultimap(
    protected val caseSensitive: Boolean = true
): MutableMultimap {
    protected class MutableEntry(
        override var name: String,
        override val values: MutableList<String> = mutableListOf()
    ): Multimap.Entry(name, values)

    protected val map = LinkedHashMap<String, MutableEntry>()

    override val entries: Set<Multimap.Entry> = EntrySetView()
    override val names: Set<String> = NameSetView()

    override val size: Int
        get() = map.size

    override fun getMulti(name: String): List<String>? {
        return map[key(name)]?.values
    }

    override operator fun contains(name: String): Boolean {
        return map.contains(key(name))
    }

    override fun append(name: String, value: String) {
        validator.validateValue(value)

        modify(
            name = name,
            update = {
                this.values.add(value)
            },
            create = {
                validator.validateName(name)
                MutableEntry(name, mutableListOf(value))
            }
        )
    }

    override fun append(name: String, values: Iterable<String>) {
        val values = values.asCollection()
        validator.validateValuesSize(values)
        validator.validateValues(values)

        modify(
            name = name,
            update = {
                this.values.addAll(values)
            },
            create = {
                validator.validateName(name)
                MutableEntry(name, values.toMutableList())
            }
        )
    }

    override fun unify(name: String, value: String) {
        modify(
            name = name,
            update = {
                if (value !in this.values) {
                    validator.validateValue(value)
                    this.values.add(value)
                }
            },
            create = {
                validator.validateName(name)
                validator.validateValue(value)
                MutableEntry(name, mutableListOf(value))
            }
        )
    }

    override fun unify(name: String, values: Iterable<String>) {
        val values = values.asCollection()
        validator.validateValuesSize(values)

        modify(
            name = name,
            update = {
                for (value in values) {
                    if (value !in this.values) {
                        validator.validateValue(value)
                        this.values.add(value)
                    }
                }
            },
            create = {
                validator.validateName(name)

                MutableEntry(name).apply {
                    for (value in values) {
                        if (value !in this.values) {
                            validator.validateValue(value)
                            this.values.add(value)
                        }
                    }
                }
            }
        )
    }


    override operator fun set(name: String, value: String) {
        validator.validateName(name)
        validator.validateValue(value)

        modify(
            name = name,
            update = {
                this.name = name
                this.values.clear()
                this.values.add(value)
            },
            create = {
                MutableEntry(name, mutableListOf(value))
            }
        )
    }

    override operator fun set(name: String, values: Iterable<String>) {
        val values = values.asCollection()
        validator.validateName(name)
        validator.validateValuesSize(values)
        validator.validateValues(values)

        modify(
            name = name,
            update = {
                this.name = name
                this.values.clear()
                this.values.addAll(values)
            },
            create = {
                MutableEntry(name, values.toMutableList())
            }
        )
    }

    override fun remove(name: String): List<String>? {
        return map.remove(key(name))?.values
    }

    override fun clear() {
        map.clear()
    }

    /* Validation */
    abstract val validator: MultimapValidator

    /* Utility functions */
    private inline fun modify(name: String, update: MutableEntry.() -> Unit, create: () -> MutableEntry) {
        val key = key(name)
        val entry = map[key]

        if (entry == null) {
            map[key] = create()
        } else {
            entry.update()
        }
    }

    protected fun key(name: String): String {
        return if (caseSensitive) name else name.lowercase()
    }

    protected fun <T> Iterable<T>.asCollection(): Collection<T> {
        return (this as? Collection<T>) ?: this.toList()
    }

    /* Optimized Set Views */
    private inner class EntrySetView: AbstractSet<Multimap.Entry>() {
        override val size: Int
            get() = map.size

        override fun iterator(): Iterator<Multimap.Entry> {
            return map.values.iterator()
        }
    }

    private inner class NameSetView: AbstractSet<String>() {
        override val size: Int
            get() = map.size

        override fun iterator(): Iterator<String> = object: Iterator<String> {
            val it = map.values.iterator()
            override fun next(): String = it.next().name
            override fun hasNext(): Boolean = it.hasNext()
        }

        override fun contains(element: String): Boolean = map.contains(key(element))
    }
}