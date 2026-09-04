package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.application.ApplicationCall
import cz.eidam.kotlinjs.server.response.ApplicationResponse
import io.ktor.http.Parameters

class RoutingCall(
    val call: ApplicationCall,
    val pathParameters: Parameters,
    val route: Route
): ApplicationCall by call {

    override val request: RoutingRequest by lazy(LazyThreadSafetyMode.NONE) {
        RoutingRequest(call.request, pathParameters)
    }
    override val response: ApplicationResponse by lazy(LazyThreadSafetyMode.NONE) {
        RoutingResponse(call.response)
    }

    val parameters: RoutingParameters by lazy(LazyThreadSafetyMode.NONE) {
        RoutingParameters(
            path = pathParameters,
            query = request.query
        )
    }
}