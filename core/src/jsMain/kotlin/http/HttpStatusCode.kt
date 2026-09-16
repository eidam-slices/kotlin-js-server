@file:Suppress("Unused")
package cz.eidam.kotlinjs.server.http

value class HttpStatusCode(val value: Int): Comparable<HttpStatusCode> {

    val description: String?
        get() = description(value)

    override fun toString(): String {
        return description?.let { "$value $it" } ?: "$value"
    }

    override fun compareTo(other: HttpStatusCode): Int {
        return this.value.compareTo(other.value)
    }

    val informational: Boolean get() = value in 100..199
    val success: Boolean get() = value in 200..299
    val redirection: Boolean get() = value in 300..399
    val clientError: Boolean get() = value in 400..499
    val serverError: Boolean get() = value in 500..599

    companion object {

        val Continue: HttpStatusCode = HttpStatusCode(100)
        val SwitchingProtocols: HttpStatusCode = HttpStatusCode(101)
        val Processing: HttpStatusCode = HttpStatusCode(102)
        val EarlyHints: HttpStatusCode = HttpStatusCode(103)
        // 104 Temporarily assigned
        // 105..199 Unassigned

        val Ok: HttpStatusCode = HttpStatusCode(200)
        val Created: HttpStatusCode = HttpStatusCode(201)
        val Accepted: HttpStatusCode = HttpStatusCode(202)
        val NonAuthoritativeInformation: HttpStatusCode = HttpStatusCode(203)
        val NoContent: HttpStatusCode = HttpStatusCode(204)
        val ResetContent: HttpStatusCode = HttpStatusCode(205)
        val PartialContent: HttpStatusCode = HttpStatusCode(206)
        val MultiStatus: HttpStatusCode = HttpStatusCode(207)
        val AlreadyReported: HttpStatusCode = HttpStatusCode(208)
        // 209..225 Unassigned
        val IMUsed: HttpStatusCode = HttpStatusCode(226)
        // 227..299 Unassigned

        val MultipleChoices: HttpStatusCode = HttpStatusCode(300)
        val MovedPermanently: HttpStatusCode = HttpStatusCode(301)
        val Found: HttpStatusCode = HttpStatusCode(302)
        val SeeOther: HttpStatusCode = HttpStatusCode(303)
        val NotModified: HttpStatusCode = HttpStatusCode(304)
        val UseProxy: HttpStatusCode = HttpStatusCode(305)
        @Deprecated("HTTP status code 306 is unused by RFC 9110.", level = DeprecationLevel.WARNING)
        val SwitchProxy: HttpStatusCode = HttpStatusCode(306)
        val TemporaryRedirect: HttpStatusCode = HttpStatusCode(307)
        val PermanentRedirect: HttpStatusCode = HttpStatusCode(308)
        // 309..399 Unassigned

        val BadRequest: HttpStatusCode = HttpStatusCode(400)
        val Unauthorized: HttpStatusCode = HttpStatusCode(401)
        val PaymentRequired: HttpStatusCode = HttpStatusCode(402)
        val Forbidden: HttpStatusCode = HttpStatusCode(403)
        val NotFound: HttpStatusCode = HttpStatusCode(404)
        val MethodNotAllowed: HttpStatusCode = HttpStatusCode(405)
        val NotAcceptable: HttpStatusCode = HttpStatusCode(406)
        val ProxyAuthenticationRequired: HttpStatusCode = HttpStatusCode(407)
        val RequestTimeout: HttpStatusCode = HttpStatusCode(408)
        val Conflict: HttpStatusCode = HttpStatusCode(409)
        val Gone: HttpStatusCode = HttpStatusCode(410)
        val LengthRequired: HttpStatusCode = HttpStatusCode(411)
        val PreconditionFailed: HttpStatusCode = HttpStatusCode(412)
        val ContentTooLarge: HttpStatusCode = HttpStatusCode(413)
        val UriTooLong: HttpStatusCode = HttpStatusCode(414)
        val UnsupportedMediaType: HttpStatusCode = HttpStatusCode(415)
        val RangeNotSatisfiable: HttpStatusCode = HttpStatusCode(416)
        val ExpectationFailed: HttpStatusCode = HttpStatusCode(417)
        @Deprecated("HTTP status code 418 is unused by RFC 9110.", level = DeprecationLevel.WARNING)
        val IAmATeapot: HttpStatusCode = HttpStatusCode(418)
        // 419..420 Unassigned
        val MisdirectedRequest: HttpStatusCode = HttpStatusCode(421)
        val UnprocessableContent: HttpStatusCode = HttpStatusCode(422)
        val Locked: HttpStatusCode = HttpStatusCode(423)
        val FailedDependency: HttpStatusCode = HttpStatusCode(424)
        val TooEarly: HttpStatusCode = HttpStatusCode(425)
        val UpgradeRequired: HttpStatusCode = HttpStatusCode(426)
        val PreconditionRequired: HttpStatusCode = HttpStatusCode(428)
        val TooManyRequests: HttpStatusCode = HttpStatusCode(429)
        // 430 Unassigned
        val RequestHeaderFieldsTooLarge: HttpStatusCode = HttpStatusCode(431)
        // 432..450 Unassigned
        val UnavailableForLegalReasons: HttpStatusCode = HttpStatusCode(451)
        // 452..499 Unassigned

        val InternalServerError: HttpStatusCode = HttpStatusCode(500)
        val NotImplemented: HttpStatusCode = HttpStatusCode(501)
        val BadGateway: HttpStatusCode = HttpStatusCode(502)
        val ServiceUnavailable: HttpStatusCode = HttpStatusCode(503)
        val GatewayTimeout: HttpStatusCode = HttpStatusCode(504)
        val HttpVersionNotSupported: HttpStatusCode = HttpStatusCode(505)
        val VariantAlsoNegotiates: HttpStatusCode = HttpStatusCode(506)
        val InsufficientStorage: HttpStatusCode = HttpStatusCode(507)
        val LoopDetected: HttpStatusCode = HttpStatusCode(508)
        // 509 Unassigned
        @Deprecated("HTTP status code 510 Not Extended is obsolete.", level = DeprecationLevel.WARNING)
        val NotExtended: HttpStatusCode = HttpStatusCode(510)
        val NetworkAuthenticationRequired: HttpStatusCode = HttpStatusCode(511)
        // 512..599 Unassigned
    }
}

@Suppress("Deprecation")
private fun description(code: Int): String? = when (HttpStatusCode(code)) {
    HttpStatusCode.Continue -> "Continue"
    HttpStatusCode.SwitchingProtocols -> "Switching Protocols"
    HttpStatusCode.Processing -> "Processing"
    HttpStatusCode.EarlyHints -> "Early Hints"

    HttpStatusCode.Ok -> "OK"
    HttpStatusCode.Created -> "Created"
    HttpStatusCode.Accepted -> "Accepted"
    HttpStatusCode.NonAuthoritativeInformation -> "Non-Authoritative Information"
    HttpStatusCode.NoContent -> "No Content"
    HttpStatusCode.ResetContent -> "Reset Content"
    HttpStatusCode.PartialContent -> "Partial Content"
    HttpStatusCode.MultiStatus -> "Multi-Status"
    HttpStatusCode.AlreadyReported -> "Already Reported"
    HttpStatusCode.IMUsed -> "IM Used"

    HttpStatusCode.MultipleChoices -> "Multiple Choices"
    HttpStatusCode.MovedPermanently -> "Moved Permanently"
    HttpStatusCode.Found -> "Found"
    HttpStatusCode.SeeOther -> "See Other"
    HttpStatusCode.NotModified -> "Not Modified"
    HttpStatusCode.UseProxy -> "Use Proxy"
    HttpStatusCode.SwitchProxy -> "Switch Proxy"
    HttpStatusCode.TemporaryRedirect -> "Temporary Redirect"
    HttpStatusCode.PermanentRedirect -> "Permanent Redirect"

    HttpStatusCode.BadRequest -> "Bad Request"
    HttpStatusCode.Unauthorized -> "Unauthorized"
    HttpStatusCode.PaymentRequired -> "Payment Required"
    HttpStatusCode.Forbidden -> "Forbidden"
    HttpStatusCode.NotFound -> "Not Found"
    HttpStatusCode.MethodNotAllowed -> "Method Not Allowed"
    HttpStatusCode.NotAcceptable -> "Not Acceptable"
    HttpStatusCode.ProxyAuthenticationRequired -> "Proxy Authentication Required"
    HttpStatusCode.RequestTimeout -> "Request Timeout"
    HttpStatusCode.Conflict -> "Conflict"
    HttpStatusCode.Gone -> "Gone"
    HttpStatusCode.LengthRequired -> "Length Required"
    HttpStatusCode.PreconditionFailed -> "Precondition Failed"
    HttpStatusCode.ContentTooLarge -> "Content Too Large"
    HttpStatusCode.UriTooLong -> "URI Too Long"
    HttpStatusCode.UnsupportedMediaType -> "Unsupported Media Type"
    HttpStatusCode.RangeNotSatisfiable -> "Range Not Satisfiable"
    HttpStatusCode.ExpectationFailed -> "Expectation Failed"
    HttpStatusCode.IAmATeapot -> "I'm a teapot"
    HttpStatusCode.MisdirectedRequest -> "Misdirected Request"
    HttpStatusCode.UnprocessableContent -> "Unprocessable Content"
    HttpStatusCode.Locked -> "Locked"
    HttpStatusCode.FailedDependency -> "Failed Dependency"
    HttpStatusCode.TooEarly -> "Too Early"
    HttpStatusCode.UpgradeRequired -> "Upgrade Required"
    HttpStatusCode.PreconditionRequired -> "Precondition Required"
    HttpStatusCode.TooManyRequests -> "Too Many Requests"
    HttpStatusCode.RequestHeaderFieldsTooLarge -> "Request Header Fields Too Large"
    HttpStatusCode.UnavailableForLegalReasons -> "Unavailable For Legal Reasons"

    HttpStatusCode.InternalServerError -> "Internal Server Error"
    HttpStatusCode.NotImplemented -> "Not Implemented"
    HttpStatusCode.BadGateway -> "Bad Gateway"
    HttpStatusCode.ServiceUnavailable -> "Service Unavailable"
    HttpStatusCode.GatewayTimeout -> "Gateway Timeout"
    HttpStatusCode.HttpVersionNotSupported -> "HTTP Version Not Supported"
    HttpStatusCode.VariantAlsoNegotiates -> "Variant Also Negotiates"
    HttpStatusCode.InsufficientStorage -> "Insufficient Storage"
    HttpStatusCode.LoopDetected -> "Loop Detected"
    HttpStatusCode.NotExtended -> "Not Extended"
    HttpStatusCode.NetworkAuthenticationRequired -> "Network Authentication Required"

    else -> null
}