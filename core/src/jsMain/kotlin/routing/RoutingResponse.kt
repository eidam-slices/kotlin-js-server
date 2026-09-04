package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.response.ApplicationResponse

class RoutingResponse(
    private val response: ApplicationResponse,
): ApplicationResponse by response