@file:Suppress("Unused")

package cz.eidam.kotlinjs.server.response

import cz.eidam.kotlinjs.server.http.*

value class ResponseHeaders
internal constructor(
    private val delegate: MapMutableHeaders
): MutableHeaders by delegate {

    operator fun invoke(block: ResponseHeaders.() -> Unit) = apply(block)

    /* Type-safe Functions */
    fun contentType(value: String) = set(HttpHeaders.ContentType, value)
    fun contentType(value: ContentType) = contentType(value.value)

    fun contentLength(value: Long) {
        require(value >= 0) { "Content-Length header value must not be negative: $value" }
        set(HttpHeaders.ContentLength, "$value")
    }

    fun contentRange(value: String) = set(HttpHeaders.ContentRange, value)

    fun contentEncoding(values: Iterable<String>) = set(HttpHeaders.ContentEncoding, values)
    fun contentEncoding(value: String) = contentEncoding(listOf(value))

    fun contentLanguage(values: Iterable<String>) = set(HttpHeaders.ContentLanguage, values)
    fun contentLanguage(value: String) = contentLanguage(listOf(value))

    fun contentDisposition(value: String) = set(HttpHeaders.ContentDisposition, value)

    fun link(values: Iterable<String>) = set(HttpHeaders.Link, values)
    fun link(value: String) = link(listOf(value))

    fun location(value: String) = set(HttpHeaders.Location, value)

    fun etag(value: String) = set(HttpHeaders.ETag, value)

    fun lastModified(value: String) = set(HttpHeaders.LastModified, value)

    fun expires(value: String) = set(HttpHeaders.Expires, value)

    fun cacheControl(value: String) = set(HttpHeaders.CacheControl, value)

    fun vary(values: Iterable<String>) = set(HttpHeaders.Vary, values)
    fun vary(value: String) = vary(listOf(value))

    fun allow(values: Iterable<HttpMethod>) = set(HttpHeaders.Allow, values.map { it.value })
    fun allow(value: HttpMethod) = allow(listOf(value))

    fun retryAfter(value: String) = set(HttpHeaders.RetryAfter, value)
    fun retryAfter(seconds: Long) {
        require(seconds >= 0) { "Retry-After header number value must not be negative: $seconds" }
        retryAfter("$seconds")
    }

    fun server(value: String) = set(HttpHeaders.Server, value)

    /* Non-Public Functions */
    internal fun build(): Headers = delegate.build()
}

