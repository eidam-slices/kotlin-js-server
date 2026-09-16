package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.http.Parameters
import cz.eidam.kotlinjs.server.http.plus

data class RoutingParameters(
    val query: Parameters,
    val path: Parameters
) {
    val all: Parameters = query + path

    operator fun get(name: String): String? = all[name]
}