package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.application.Application
import cz.eidam.kotlinjs.server.application.ApplicationCall
import cz.eidam.kotlinjs.server.routing.selectors.RootRouteSelector

class RoutingRoot(val application: Application): RouteNode(RootRouteSelector, null), Routing {

    fun resolve(call: ApplicationCall): RouteResolveResult {
        val context = RouteResolver(call)
        return context.resolve(this)
    }
}