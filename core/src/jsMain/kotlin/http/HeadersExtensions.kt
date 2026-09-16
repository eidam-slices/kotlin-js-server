@file:Suppress("Unused")

package cz.eidam.kotlinjs.server.http

/* region Factory Functions */
fun headersOf(): Headers = Headers.Empty
fun headersOf(name: String, values: List<String>): Headers = SingleHeaders(name, values)
fun headersOf(name: String, value: String): Headers = headersOf(name, listOf(value))
fun headersOf(name: String, vararg values: String): Headers = headersOf(name, values.asList())


inline fun headers(block: MutableHeaders.() -> Unit): Headers {
    val builder = MapMutableHeaders().apply(block)
    return builder.build()
}
/*endregion*/

/* region Headers Extensions */
// TODO: consider if this function is worth keeping and its functionality is clear
operator fun Headers.plus(other: Headers): Headers {
    if (other.isEmpty()) return this
    if (this.isEmpty()) return other

    return headers {
        append(this@plus)
        append(other)
    }
}

infix fun Headers.union(other: Headers): Headers {
    return headers {
        unify(this@union)
        unify(other)
    }
}

/*endregion*/

/* region MutableHeaders Extensions */
fun MutableHeaders.append(headers: Headers) {
    if (headers.isEmpty()) return
    for ((name, values) in headers.entries) {
        append(name, values)
    }
}

fun MutableHeaders.unify(headers: Headers) {
    if (headers.isEmpty()) return
    for ((name, values) in headers.entries) {
        unify(name, values)
    }
}

// TODO: consider if this function is worth keeping and its functionality is clear
operator fun MutableHeaders.plusAssign(other: Headers) = append(other)

/*endregion*/
