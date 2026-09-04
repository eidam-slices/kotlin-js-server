package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.routing.selectors.HttpMethodRouteSelector
import cz.eidam.kotlinjs.server.routing.selectors.RouteSelector
import io.ktor.http.HttpMethod

fun Route.route(path: String, build: Route.() -> Unit): Route {
    val segments = path.trim('/').split('/').filter { it.isNotEmpty() }

    var current = this
    for (segment in segments) {
        current = current.child(RouteSelector.fromString(segment))
    }
    current.build()
    return current
}

fun Route.get(handler: RouteHandler) = method(HttpMethod.Get) { handle(handler) }
fun Route.get(path: String, handler: RouteHandler) = route(path) { get(handler) }

fun Route.post(handler: RouteHandler) = method(HttpMethod.Post) { handle(handler) }
fun Route.post(path: String, handler: RouteHandler) = route(path) { post(handler) }

fun Route.put(handler: RouteHandler) = method(HttpMethod.Put) { handle(handler) }
fun Route.put(path: String, handler: RouteHandler) = route(path) { put(handler) }

fun Route.delete(handler: RouteHandler) = method(HttpMethod.Delete) { handle(handler) }
fun Route.delete(path: String, handler: RouteHandler) = route(path) { delete(handler) }


private fun Route.method(method: HttpMethod, build: Route.() -> Unit): Route {
    return child(HttpMethodRouteSelector(method)).apply(block = build)
}
