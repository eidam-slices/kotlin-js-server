package cz.eidam.kotlinjs.server.engine

import cz.eidam.kotlinjs.server.http.content.OutgoingContent
import cz.eidam.kotlinjs.server.response.CommitableResponse
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpStatusCode

abstract class BaseApplicationResponse: CommitableResponse {

    protected abstract suspend fun respond(
        status: HttpStatusCode,
        headers: Headers,
        body: OutgoingContent
    )

    final override var status: HttpStatusCode? = null
        private set

    private val _headers: HeadersBuilder = HeadersBuilder()
    final override val headers: Headers get() = _headers.build()

    private var body: OutgoingContent? = null

    final override var committed: Boolean = false
        protected set

    final override var sent: Boolean = false
        protected set

    final override fun headers(build: HeadersBuilder.() -> Unit) {
        check(!committed) { ALREADY_COMMITTED }
        _headers.apply(build)
    }

    final override fun status(value: HttpStatusCode) {
        check(!committed) { ALREADY_COMMITTED }
        status = value
    }

    final override fun body(value: OutgoingContent) {
        check(!committed) { ALREADY_COMMITTED }
        body = value
    }

    final override suspend fun commit() {
        if (committed) return
        committed = true

        val status = this.status ?: DEFAULT_STATUS
        val body = this.body ?: OutgoingContent.NoContent

        val headers = this.headers


        respond(status, headers, body)
        sent = true
    }

    companion object {
        val DEFAULT_STATUS = HttpStatusCode.OK
    }

}

private const val ALREADY_COMMITTED = "Response is already committed."