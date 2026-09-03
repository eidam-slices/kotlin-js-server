package cz.eidam.kotlinjs.server.application

import cz.eidam.kotlinjs.server.request.ApplicationRequest
import cz.eidam.kotlinjs.server.response.ApplicationResponse

/*
 UNIDIRECTIONAL FLOW
   This ApplicationCall is intentionally kept minimal and does not carry a direct
   back-reference to Application for now.

   In this lightweight design we keep the lifecycle and routing orchestration in
   Application itself, and keep ApplicationCall as a thin request/response
   container to reduce allocations and avoid circular references between
   request/response and application.

   If a plugin system later requires app-scoped context, this can be extended
   with a dedicated plugin context or an optional application reference without
   changing the core request flow.

   NOTE: Ktor uses ApplicationCall as the central request context, because it needs:
   - application-scoped plugin access
   - attributes / request metadata
   - access to the current application instance during plugin execution
*/

interface ApplicationCall {
    val request: ApplicationRequest
    val response: ApplicationResponse
}