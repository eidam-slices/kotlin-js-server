package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.request.ApplicationRequest
import cz.eidam.kotlinjs.server.http.Parameters

class RoutingRequest(
    private val request: ApplicationRequest,
    val pathParameters: Parameters
): ApplicationRequest by request