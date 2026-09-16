@file:OptIn(ExperimentalWasmJsInterop::class)

package cz.eidam.kotlinjs.server.node

import cz.eidam.kotlinjs.server.http.Parameters
import cz.eidam.kotlinjs.server.http.collections.Multimap
import js.array.component1
import js.array.component2
import js.iterable.iterator
import web.url.URLSearchParams

class NodeParameters(
    private val parameters: URLSearchParams
): Parameters {

    private var cached: Map<String, List<String>>? = null

    override val entries: Set<Multimap.Entry> = object: AbstractSet<Multimap.Entry>() {
        override val size: Int
            get() = this@NodeParameters.size

        override fun iterator(): Iterator<Multimap.Entry> {
            val iterator = cache().iterator()

            return object: Iterator<Multimap.Entry> {
                override fun next(): Multimap.Entry {
                    val (key, values) = iterator.next()
                    return Multimap.Entry(key, values)
                }

                override fun hasNext(): Boolean {
                    return iterator.hasNext()
                }
            }
        }
    }

    override val names: Set<String> = object: AbstractSet<String>() {
        override val size: Int
            get() = this@NodeParameters.size

        override fun iterator(): Iterator<String> {
            return cache().keys.iterator()
        }

        override fun contains(element: String): Boolean {
            return this@NodeParameters.contains(element)
        }
    }
    override val size: Int
        get() = cache().size

    override fun getOne(name: String): String? {
        val cached = cached

        return if (cached != null) {
            cached[name]?.firstOrNull()
        } else {
            parameters.get(name)
        }
    }

    override fun getMulti(name: String): List<String>? {
        val cached = cached
        return if (cached != null) {
            cached[name]
        } else {
            parameters.getAll(name)
                .takeIf { it.isNotEmpty() }
                ?.asList()
        }
    }

    override fun contains(name: String): Boolean {
        val cached = cached

        return if (cached != null) {
            cached.contains(name)
        } else {
            parameters.has(name)
        }
    }

    private fun cache(): Map<String, List<String>> {
        cached?.let { return it }

        val result: Map<String, List<String>> = buildMap<String, MutableList<String>> {
            for ((key, value) in parameters.entries()) {
                val existing = getOrPut(key) { mutableListOf() }
                existing.add(value)
            }
        }
        cached = result
        return result
    }
}