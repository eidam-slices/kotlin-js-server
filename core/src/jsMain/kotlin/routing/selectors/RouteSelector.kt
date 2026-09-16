package cz.eidam.kotlinjs.server.routing.selectors

import cz.eidam.kotlinjs.server.http.HttpStatusCode
import cz.eidam.kotlinjs.server.http.Parameters
import cz.eidam.kotlinjs.server.routing.RouteResolver

abstract class RouteSelector {
    abstract fun evaluate(context: RouteResolver, segmentIndex: Int): RouteSelectorEvaluation

    companion object {
        fun fromString(segment: String): RouteSelector {
            val clean = segment.trim('/')

            return when {
                clean.isEmpty() -> RootRouteSelector
                clean == "*" -> PathSegmentWildcardRouteSelector
                clean.startsWith("{") && clean.endsWith("}") -> {
                    PathSegmentParameterRouteSelector(clean.removeSurrounding("{", "}"))
                }

                else -> PathSegmentConstantRouteSelector(clean)
            }
        }
    }
}

sealed class RouteSelectorEvaluation(val succeeded: Boolean) {
    data class Success(
        val quality: Double,
        val parameters: Parameters = Parameters.Empty,
        val increment: Int = 0,
    ): RouteSelectorEvaluation(succeeded = true)

    data class Failure(
        val quality: Double,
        val code: HttpStatusCode,
    ): RouteSelectorEvaluation(succeeded = false)

    object Qualities {
        const val EXACT = 1.0
        const val PARAMETER = 0.8
        const val WILDCARD = 0.5
        const val TRANSPARENT = 0.0
        const val FAILED = -1.0
    }
}

