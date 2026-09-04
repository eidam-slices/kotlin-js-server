package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.application.Application

interface Routing: Route

fun Application.routing(configure: Routing.() -> Unit): RoutingRoot {
    return this.routing.apply(block = configure)
}