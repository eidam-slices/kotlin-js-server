@file:Suppress("Unused")

package cz.eidam.kotlinjs.server.http

value class HttpCharset(val value: String) {

    override fun toString(): String = value

    companion object {
        val Utf8 = HttpCharset("utf-8")

        val Utf16 = HttpCharset("utf-16")
        val Utf16Be = HttpCharset("utf-16be")
        val Utf16Le = HttpCharset("utf-16le")

        val Utf32 = HttpCharset("utf-32")
        val Utf32Be = HttpCharset("utf-32be")
        val Utf32Le = HttpCharset("utf-32le")

        val UsAscii = HttpCharset("us-ascii")
    }
}