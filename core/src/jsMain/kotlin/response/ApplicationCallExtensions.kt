@file:Suppress("NOTHING_TO_INLINE")

package cz.eidam.kotlinjs.server.response

import cz.eidam.kotlinjs.server.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow

inline fun ApplicationCall.respondText(
    text: String,
    status: HttpStatusCode? = null,
    contentType: ContentType = ContentType.Text.Plain
) {
    this.response.text(text, status, contentType)
}

inline fun ApplicationCall.respondBytes(
    bytes: ByteArray,
    status: HttpStatusCode? = null,
    contentType: ContentType = ContentType.Application.OctetStream
) {
    this.response.bytes(bytes, status, contentType)
}

inline fun ApplicationCall.respondStream(
    flow: Flow<ByteArray>,
    status: HttpStatusCode? = null,
    contentType: ContentType = ContentType.Application.OctetStream,
    contentLength: Long? = null,
) {
    this.response.stream(flow, status, contentType, contentLength)
}

inline fun ApplicationCall.respondEmpty(
    status: HttpStatusCode? = null,
) {
    this.response.empty(status)
}

inline fun ApplicationCall.respondRedirect(
    url: String,
    permanent: Boolean = false,
) {
    this.response.redirect(url, permanent)
}