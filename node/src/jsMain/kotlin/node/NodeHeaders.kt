@file:OptIn(ExperimentalWasmJsInterop::class)

package cz.eidam.kotlinjs.server.node

import cz.eidam.kotlinjs.server.http.Headers
import cz.eidam.kotlinjs.server.http.collections.Multimap
import js.array.ReadonlyArray
import js.array.component1
import js.array.component2
import js.objects.Object
import node.Dict

class NodeHeaders(
    private val headers: Dict<ReadonlyArray<String>>
): Headers {

    private var cached: Map<String, List<String>>? = null

    override val size: Int
        get() = cached?.size ?: Object.keys(headers).size

    override val entries: Set<Multimap.Entry> = object: AbstractSet<Multimap.Entry>() {
        override val size: Int
            get() = this@NodeHeaders.size

        override fun iterator(): Iterator<Multimap.Entry> {
            val iterator = cache().iterator()
            return object: Iterator<Multimap.Entry> {
                override fun next(): Multimap.Entry {
                    val next = iterator.next()
                    return Multimap.Entry(next.key, next.value)
                }

                override fun hasNext(): Boolean {
                    return iterator.hasNext()
                }
            }
        }
    }

    override val names: Set<String> = object: AbstractSet<String>() {
        override val size: Int
            get() = this@NodeHeaders.size

        override fun iterator(): Iterator<String> {
            return cache().keys.iterator()
        }

        override fun contains(element: String): Boolean {
            return this@NodeHeaders.contains(element)
        }
    }


    override fun getMulti(name: String): List<String>? {
        val key = key(name)
        val cached = cached

        return if (cached != null) {
            cached[key]
        } else {
            headers[key]?.asList()
        }
    }

    override fun contains(name: String): Boolean {
        val key = key(name)
        val cached = cached

        return if (cached != null) {
            cached.contains(key)
        } else {
            headers[key] != null
        }
    }

    private fun cache(): Map<String, List<String>> {
        cached?.let { return it }

        val result = buildMap {
            for ((name, values) in Object.entries(headers)) {
                put(name, values.asList())
            }
        }
        cached = result
        return result
    }

    private fun key(name: String): String = name.lowercase()
}

