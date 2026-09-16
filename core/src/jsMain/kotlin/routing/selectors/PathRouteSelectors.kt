package cz.eidam.kotlinjs.server.routing.selectors

import cz.eidam.kotlinjs.server.http.HttpStatusCode
import cz.eidam.kotlinjs.server.http.parametersOf
import cz.eidam.kotlinjs.server.routing.RouteResolver

data class PathSegmentConstantRouteSelector(val value: String): RouteSelector() {
    override fun evaluate(context: RouteResolver, segmentIndex: Int): RouteSelectorEvaluation {
        val segment = context.segments.getOrNull(segmentIndex)

        return if (segment == value) {
            RouteSelectorEvaluation.Success(
                quality = RouteSelectorEvaluation.Qualities.EXACT,
                increment = 1
            )
        } else {
            RouteSelectorEvaluation.Failure(
                quality = RouteSelectorEvaluation.Qualities.FAILED,
                code = HttpStatusCode.NotFound
            )
        }
    }

    override fun toString(): String {
        return value
    }
}

data class PathSegmentParameterRouteSelector(val name: String): RouteSelector() {
    override fun evaluate(context: RouteResolver, segmentIndex: Int): RouteSelectorEvaluation {
        val value = context.segments.getOrNull(segmentIndex)

        return if (!value.isNullOrEmpty()) {
            RouteSelectorEvaluation.Success(
                quality = RouteSelectorEvaluation.Qualities.PARAMETER,
                parameters = parametersOf(name, value),
                increment = 1
            )
        } else {
            RouteSelectorEvaluation.Failure(
                quality = RouteSelectorEvaluation.Qualities.FAILED,
                code = HttpStatusCode.NotFound,
            )
        }
    }

    override fun toString(): String {
        return "{$name}"
    }
}

data object PathSegmentWildcardRouteSelector: RouteSelector() {
    override fun evaluate(context: RouteResolver, segmentIndex: Int): RouteSelectorEvaluation {
        val segment = context.segments.getOrNull(segmentIndex)

        return if (segment != null) {
            RouteSelectorEvaluation.Success(
                quality = RouteSelectorEvaluation.Qualities.WILDCARD,
                increment = 1
            )
        } else {
            RouteSelectorEvaluation.Failure(
                quality = RouteSelectorEvaluation.Qualities.FAILED,
                code = HttpStatusCode.NotFound
            )
        }
    }

    override fun toString(): String {
        return "*"
    }
}