package cz.eidam.kotlinjs.server.request

import cz.eidam.kotlinjs.server.http.Headers
import cz.eidam.kotlinjs.server.http.HttpMethod
import cz.eidam.kotlinjs.server.http.Parameters
import kotlinx.coroutines.flow.Flow

/*
 UNIDIRECTIONAL FLOW
  Back reference to the call was intentionally removed to avoid circular dependency,
  when creating instances of ApplicationRequest and ApplicationResponse and "leaking this"
  during engine initialization.
  The call can be passed as a parameter or context() to functions that need it,
  instead of being stored in the request/response objects.

 Note: Ktor uses this approach with back reference to call.
*/

interface ApplicationRequest {
    val uri: String
    val path: String get() = uri.substringBefore("?")
    val method: HttpMethod
    val headers: Headers

    // TODO: cookies
    val query: Parameters

    fun body(): Flow<ByteArray>
}
