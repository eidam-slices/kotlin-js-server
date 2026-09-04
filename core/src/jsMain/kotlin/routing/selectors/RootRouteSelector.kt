package cz.eidam.kotlinjs.server.routing.selectors

import cz.eidam.kotlinjs.server.routing.RouteResolver

data object RootRouteSelector: RouteSelector() {
    override fun evaluate(context: RouteResolver, segmentIndex: Int): RouteSelectorEvaluation {
        return RouteSelectorEvaluation.Success(
            quality = RouteSelectorEvaluation.Qualities.TRANSPARENT,
            increment = 0,
        )
    }

    override fun toString(): String {
        return "/"
    }
}