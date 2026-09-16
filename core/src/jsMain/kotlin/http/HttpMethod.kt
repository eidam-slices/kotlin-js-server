@file:Suppress("Unused")

package cz.eidam.kotlinjs.server.http

value class HttpMethod(val value: String) {

    override fun toString(): String = value

    companion object {
        val Get = HttpMethod("GET")
        val Post = HttpMethod("POST")
        val Put = HttpMethod("PUT")
        val Patch = HttpMethod("PATCH")
        val Delete = HttpMethod("DELETE")
        val Head = HttpMethod("HEAD")
        val Options = HttpMethod("OPTIONS")
        val Trace = HttpMethod("TRACE")
        val Connect = HttpMethod("CONNECT")
        val Query = HttpMethod("QUERY")
    }

    val safe: Boolean
        get() = when (this) {
            Get, Head, Options, Trace, Query -> true
            else -> false
        }

    val idempotent: Boolean
        get() = when (this) {
            Get, Head, Put, Delete, Options, Trace, Query -> true
            else -> false
        }
}