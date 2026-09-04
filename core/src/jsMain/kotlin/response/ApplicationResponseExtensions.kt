package cz.eidam.kotlinjs.server.response

import cz.eidam.kotlinjs.server.http.content.OutgoingContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow

// region RESPONSE EXTENSION FUNCTIONS
fun ApplicationResponse.text(
    text: String,
    status: HttpStatusCode? = null,
    contentType: ContentType = ContentType.Text.Plain
) {
    val content = OutgoingContent.Text(text)
    status?.let { this.status(it) }
    headers {
        set(HttpHeaders.ContentType, contentType.toString())
    }
    this.body(content)
}

fun ApplicationResponse.bytes(
    bytes: ByteArray,
    status: HttpStatusCode? = null,
    contentType: ContentType = ContentType.Application.OctetStream
) {
    val content = OutgoingContent.Bytes(bytes)
    status?.let { this.status(it) }
    headers {
        set(HttpHeaders.ContentType, contentType.toString())
    }
    this.body(content)
}

fun ApplicationResponse.stream(
    flow: Flow<ByteArray>,
    status: HttpStatusCode? = null,
    contentType: ContentType = ContentType.Application.OctetStream,
    contentLength: Long? = null,
) {
    val content = OutgoingContent.Stream(flow)
    status?.let { this.status(it) }
    headers {
        set(HttpHeaders.ContentType, contentType.toString())
        contentLength?.let { length ->
            set(HttpHeaders.ContentLength, length.toString())
        }
    }
    this.body(content)
}

fun ApplicationResponse.empty(
    status: HttpStatusCode? = null,
) {
    status?.let { this.status(it) }
    val content = OutgoingContent.NoContent
    this.body(content)
}

fun ApplicationResponse.redirect(
    url: String,
    permanent: Boolean = false,
) {
    val content = OutgoingContent.NoContent
    this.status(value = if (permanent) HttpStatusCode.MovedPermanently else HttpStatusCode.Found)
    this.headers { set(HttpHeaders.Location, url) }
    this.body(content)
}
// endregion