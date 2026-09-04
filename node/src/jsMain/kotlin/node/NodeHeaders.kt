package cz.eidam.kotlinjs.server.node

import io.ktor.http.Headers
import js.array.ReadonlyArray
import js.array.component1
import js.array.component2
import js.objects.Object
import node.Dict

class NodeHeaders(
    private val headers: Dict<ReadonlyArray<String>>
): Headers {

    private typealias Multimap = Map<String, List<String>>

    private var cached: Multimap? = null

    override val caseInsensitiveName: Boolean
        get() = true

    override fun get(name: String): String? {
        return headers[name.lowercase()]?.firstOrNull()
    }

    override fun getAll(name: String): List<String>? {
        return headers[name.lowercase()]?.asList()
    }

    override fun names(): Set<String> {
        return (cached ?: cache()).keys
    }

    override fun entries(): Set<Map.Entry<String, List<String>>> {
        return (cached ?: cache()).entries
    }

    override fun isEmpty(): Boolean {
        @OptIn(ExperimentalWasmJsInterop::class)
        return cached?.isEmpty() ?: Object.keys(headers).isEmpty()
    }

    private fun cache(): Multimap {
        val snapshot = cached
        if (snapshot == null) {
            val new = Object.entries(headers).associate { (key, value) ->
                @OptIn(ExperimentalWasmJsInterop::class)
                key to value.asList()
            }
            this.cached = new
            return new
        } else {
            return snapshot
        }
    }

}

