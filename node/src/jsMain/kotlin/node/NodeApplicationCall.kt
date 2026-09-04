package cz.eidam.kotlinjs.server.node

import cz.eidam.kotlinjs.server.application.ApplicationCall
import cz.eidam.kotlinjs.server.request.ApplicationRequest
import cz.eidam.kotlinjs.server.response.ApplicationResponse

class NodeApplicationCall(
    override val request: ApplicationRequest,
    override val response: ApplicationResponse,
): ApplicationCall {

    constructor(request: NodeRequest, response: NodeResponse): this(
        request = NodeApplicationRequest(request),
        response = NodeApplicationResponse(response)
    )

}
