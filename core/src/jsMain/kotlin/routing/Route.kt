package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.routing.selectors.RouteSelector

typealias RouteHandler = suspend RoutingContext.() -> Unit

interface Route {
    val selector: RouteSelector
    val parent: Route?
    val children: List<Route>

    fun child(selector: RouteSelector): Route
    fun handle(handler: RouteHandler)
}

// TODO: consider using value class
class RoutingContext(val call: RoutingCall)