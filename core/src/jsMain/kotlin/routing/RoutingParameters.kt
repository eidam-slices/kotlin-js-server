package cz.eidam.kotlinjs.server.routing

import io.ktor.http.Parameters
import io.ktor.http.plus

data class RoutingParameters(
    val query: Parameters,
    val path: Parameters
) {
    val all: Parameters = query + path

    operator fun get(name: String): String? = all[name]
}