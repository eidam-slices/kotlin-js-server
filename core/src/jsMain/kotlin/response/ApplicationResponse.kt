package cz.eidam.kotlinjs.server.response

import cz.eidam.kotlinjs.server.http.content.OutgoingContent
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpStatusCode

/*
 UNIDIRECTIONAL FLOW
  Back reference to the call was intentionally removed to avoid circular dependency,
  when creating instances of ApplicationRequest and ApplicationResponse and "leaking this"
  during engine initialization.
  The call can be passed as a parameter or context() to functions that need it,
  instead of being stored in the request/response objects.

 Note: Ktor uses this approach with back reference to call.
*/

interface ApplicationResponse {

    val committed: Boolean
    val sent: Boolean

    val status: HttpStatusCode?
    fun status(value: HttpStatusCode)

    val headers: Headers
    fun headers(build: HeadersBuilder.() -> Unit)

    // TODO: cookies
    fun body(value: OutgoingContent)
}

interface CommitableResponse: ApplicationResponse {
    suspend fun commit()
}