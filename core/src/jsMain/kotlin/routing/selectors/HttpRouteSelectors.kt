package cz.eidam.kotlinjs.server.routing.selectors

import cz.eidam.kotlinjs.server.http.HttpMethod
import cz.eidam.kotlinjs.server.http.HttpStatusCode
import cz.eidam.kotlinjs.server.routing.RouteResolver

data class HttpMethodRouteSelector(val method: HttpMethod): RouteSelector() {
    override fun evaluate(context: RouteResolver, segmentIndex: Int): RouteSelectorEvaluation {
        return if (context.call.request.method == method) {
            RouteSelectorEvaluation.Success(
                quality = RouteSelectorEvaluation.Qualities.EXACT,
                increment = 0,
            )
        } else {
            RouteSelectorEvaluation.Failure(
                quality = RouteSelectorEvaluation.Qualities.FAILED,
                code = HttpStatusCode.MethodNotAllowed
            )
        }
    }

    override fun toString(): String {
        return method.value
    }
}
