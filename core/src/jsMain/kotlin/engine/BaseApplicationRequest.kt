package cz.eidam.kotlinjs.server.engine

import cz.eidam.kotlinjs.server.request.ApplicationRequest
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow

abstract class BaseApplicationRequest: ApplicationRequest {
    abstract override val uri: String
    abstract override val headers: Headers
    abstract override val method: HttpMethod
    abstract override val query: Parameters

    private var consumed: Boolean = false

    final override fun body(): Flow<ByteArray> {
        check(!consumed) { "Request body has already been consumed. Request body can be read only once." }
        consumed = true
        return createNetworkStream()
    }

    protected abstract fun createNetworkStream(): Flow<ByteArray>
}