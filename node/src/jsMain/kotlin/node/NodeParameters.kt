package cz.eidam.kotlinjs.server.node

import io.ktor.http.Parameters
import js.array.component1
import js.array.component2
import js.iterable.toList
import js.iterable.toSet
import web.url.URLSearchParams

class NodeParameters(
    private val parameters: URLSearchParams
): Parameters {

    override val caseInsensitiveName: Boolean
        get() = false

    override fun get(name: String): String? {
        @OptIn(ExperimentalWasmJsInterop::class)
        return parameters.get(name)
    }

    override fun getAll(name: String): List<String>? {
        return parameters.getAll(name).asList().takeIf { it.isNotEmpty() }
    }

    override fun names(): Set<String> {
        return parameters.keys().toSet()
    }

    override fun entries(): Set<Map.Entry<String, List<String>>> {
        return parameters.entries()
            .toList()
            .groupBy({ it.component1() }, { it.component2() })
            .entries
    }

    override fun isEmpty(): Boolean {
        return parameters.size <= 0
    }

}