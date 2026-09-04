package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.application.ApplicationCall
import cz.eidam.kotlinjs.server.routing.selectors.RouteSelectorEvaluation
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.plus

class RouteResolver(
    val call: ApplicationCall
) {
    val segments: List<String> = call.request.path.split('/').filter { it.isNotEmpty() }

    private var code: HttpStatusCode = HttpStatusCode.NotFound
    private var priority: Int = 0
    private var depth: Int = -1

    fun resolve(root: RouteNode): RouteResolveResult {
        // begin lookup from top
        val success = lookup(root, 0, Parameters.Empty)
        if (success != null) return success

        // use the most specific failure code if available, otherwise default to 404
        return RouteResolveResult.Failure(code)
    }

    /*
      TODO:
        Sort children by selector quality (descending),
        so exact matches take precedence over parameters and wildcards (e.g., /users/me before /users/{id})
     */
    private fun lookup(
        node: RouteNode,
        segmentIndex: Int,
        parameters: Parameters
    ): RouteResolveResult.Success? {

        val evaluation = node.selector.evaluate(context = this, segmentIndex)
        if (evaluation is RouteSelectorEvaluation.Failure) {
            failure(evaluation.code, depth = segmentIndex)
            return null
        }

        check(evaluation is RouteSelectorEvaluation.Success)

        // calculate cumulated parameters and next index
        val cumulatedParameters = parameters + evaluation.parameters
        val nextIndex = segmentIndex + evaluation.increment

        // if lookup is done, return
        val lookupDone = nextIndex == segments.size && node.handlers.isNotEmpty()
        if (lookupDone) {
            return RouteResolveResult.Success(node, cumulatedParameters)
        }

        // recursively lookup for children
        for (child in node.children) {
            val result = lookup(child, nextIndex, cumulatedParameters)
            if (result != null) return result
        }

        return null
    }

    private fun failure(code: HttpStatusCode, depth: Int) {
        // calculate current and new code priorities
        val currentPriority = this.priority
        val newPriority = code.priority()

        val currentDepth = this.depth
        val newDepth = depth

        // use new code if it's deeper or has higher priority at the same depth
        if (newDepth > currentDepth || (newDepth == currentDepth && newPriority > currentPriority)) {
            this.priority = newPriority
            this.depth = newDepth
            this.code = code
        }
    }
}

sealed interface RouteResolveResult {
    data class Success(
        val route: RouteNode,
        val parameters: Parameters,
    ): RouteResolveResult

    data class Failure(
        val code: HttpStatusCode,
    ): RouteResolveResult
}

private fun HttpStatusCode.priority(): Int = when (this) {
    HttpStatusCode.UnsupportedMediaType, HttpStatusCode.NotAcceptable -> 30
    HttpStatusCode.MethodNotAllowed -> 20
    HttpStatusCode.BadRequest -> 10
    else -> 0
}