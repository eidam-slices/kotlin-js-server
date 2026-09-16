@file:Suppress("ConstPropertyName", "SpellCheckingInspection", "Unused")

package cz.eidam.kotlinjs.server.http

object HttpHeaders {

    /* Content negotiation */
    const val Accept: String = "Accept"
    @Deprecated("Accept-Charset is deprecated by RFC 9110 because UTF-8 is nearly ubiquitous.")
    const val AcceptCharset: String = "Accept-Charset"
    const val AcceptEncoding: String = "Accept-Encoding"
    const val AcceptLanguage: String = "Accept-Language"
    const val AcceptPatch: String = "Accept-Patch"
    const val AcceptQuery: String = "Accept-Query"
    const val AcceptRanges: String = "Accept-Ranges"

    /* Representation metadata */
    const val ContentDisposition: String = "Content-Disposition"
    const val ContentEncoding: String = "Content-Encoding"
    const val ContentLanguage: String = "Content-Language"
    const val ContentLength: String = "Content-Length"
    const val ContentLocation: String = "Content-Location"
    const val ContentRange: String = "Content-Range"
    const val ContentType: String = "Content-Type"
    const val ETag: String = "ETag"
    const val LastModified: String = "Last-Modified"

    /* Digest and integrity */
    const val ContentDigest: String = "Content-Digest"
    const val ReprDigest: String = "Repr-Digest"
    const val WantContentDigest: String = "Want-Content-Digest"
    const val WantReprDigest: String = "Want-Repr-Digest"

    /* Caching */
    const val Age: String = "Age"
    const val CacheControl: String = "Cache-Control"
    const val CacheStatus: String = "Cache-Status"
    const val Expires: String = "Expires"
    @Deprecated("Pragma is deprecated by RFC 9111; use Cache-Control instead.")
    const val Pragma: String = "Pragma"
    const val Vary: String = "Vary"
    @Deprecated("Warning was obsoleted by RFC 9111.")
    const val Warning: String = "Warning"

    /* Conditional requests */
    const val IfMatch: String = "If-Match"
    const val IfModifiedSince: String = "If-Modified-Since"
    const val IfNoneMatch: String = "If-None-Match"
    const val IfRange: String = "If-Range"
    const val IfUnmodifiedSince: String = "If-Unmodified-Since"

    /* Range requests */
    const val Range: String = "Range"

    /* Authentication */
    const val AuthenticationInfo: String = "Authentication-Info"
    const val Authorization: String = "Authorization"
    const val ProxyAuthenticate: String = "Proxy-Authenticate"
    const val ProxyAuthenticationInfo: String = "Proxy-Authentication-Info"
    const val ProxyAuthorization: String = "Proxy-Authorization"
    const val WWWAuthenticate: String = "WWW-Authenticate"

    /* Request and response control */
    const val Allow: String = "Allow"
    const val Date: String = "Date"
    const val Expect: String = "Expect"
    const val From: String = "From"
    const val Host: String = "Host"
    const val Location: String = "Location"
    const val MaxForwards: String = "Max-Forwards"
    const val Prefer: String = "Prefer"
    const val PreferenceApplied: String = "Preference-Applied"
    const val RetryAfter: String = "Retry-After"
    const val Server: String = "Server"
    const val UserAgent: String = "User-Agent"

    /* Connection and transfer */
    const val ALPN: String = "ALPN"
    const val AltSvc: String = "Alt-Svc"
    const val Connection: String = "Connection"
    @Deprecated("HTTP2-Settings was obsoleted by RFC 9113 together with the HTTP/2 h2c upgrade mechanism.")
    const val Http2Settings: String = "HTTP2-Settings"
    const val TE: String = "TE"
    const val Trailer: String = "Trailer"
    const val TransferEncoding: String = "Transfer-Encoding"
    const val Upgrade: String = "Upgrade"
    const val Via: String = "Via"

    /* Cookies */
    const val Cookie: String = "Cookie"
    const val SetCookie: String = "Set-Cookie"

    /* CORS */
    const val AccessControlAllowCredentials: String = "Access-Control-Allow-Credentials"
    const val AccessControlAllowHeaders: String = "Access-Control-Allow-Headers"
    const val AccessControlAllowMethods: String = "Access-Control-Allow-Methods"
    const val AccessControlAllowOrigin: String = "Access-Control-Allow-Origin"
    const val AccessControlExposeHeaders: String = "Access-Control-Expose-Headers"
    const val AccessControlMaxAge: String = "Access-Control-Max-Age"
    const val AccessControlRequestHeaders: String = "Access-Control-Request-Headers"
    const val AccessControlRequestMethod: String = "Access-Control-Request-Method"
    const val Origin: String = "Origin"

    /* Security */
    const val ClearSiteData: String = "Clear-Site-Data"
    const val ContentSecurityPolicy: String = "Content-Security-Policy"
    const val ContentSecurityPolicyReportOnly: String = "Content-Security-Policy-Report-Only"
    const val CrossOriginEmbedderPolicy: String = "Cross-Origin-Embedder-Policy"
    const val CrossOriginEmbedderPolicyReportOnly: String = "Cross-Origin-Embedder-Policy-Report-Only"
    const val CrossOriginOpenerPolicy: String = "Cross-Origin-Opener-Policy"
    const val CrossOriginOpenerPolicyReportOnly: String = "Cross-Origin-Opener-Policy-Report-Only"
    const val CrossOriginResourcePolicy: String = "Cross-Origin-Resource-Policy"
    const val PermissionsPolicy: String = "Permissions-Policy"
    const val Referrer: String = "Referer"
    const val ReferrerPolicy: String = "Referrer-Policy"
    const val ReportingEndpoints: String = "Reporting-Endpoints"
    const val StrictTransportSecurity: String = "Strict-Transport-Security"
    const val XContentTypeOptions: String = "X-Content-Type-Options"
    const val XFrameOptions: String = "X-Frame-Options"
    @Deprecated("HTTP Public Key Pinning (HPKP) is no longer supported by modern browsers and should not be used.")
    const val PublicKeyPins: String = "Public-Key-Pins"
    @Deprecated("HTTP Public Key Pinning (HPKP) is no longer supported by modern browsers and should not be used.")
    const val PublicKeyPinsReportOnly: String = "Public-Key-Pins-Report-Only"

    /* WebSocket */
    const val SecWebSocketAccept: String = "Sec-WebSocket-Accept"
    const val SecWebSocketExtensions: String = "Sec-WebSocket-Extensions"
    const val SecWebSocketKey: String = "Sec-WebSocket-Key"
    const val SecWebSocketProtocol: String = "Sec-WebSocket-Protocol"
    const val SecWebSocketVersion: String = "Sec-WebSocket-Version"

    /* WebDAV and CalDAV */
    const val DASL: String = "DASL"
    const val DAV: String = "DAV"
    const val Depth: String = "Depth"
    const val Destination: String = "Destination"
    const val If: String = "If"
    const val IfScheduleTagMatch: String = "If-Schedule-Tag-Match"
    const val LockToken: String = "Lock-Token"
    const val OrderingType: String = "Ordering-Type"
    const val Overwrite: String = "Overwrite"
    const val Position: String = "Position"
    const val ScheduleReply: String = "Schedule-Reply"
    const val ScheduleTag: String = "Schedule-Tag"
    const val Timeout: String = "Timeout"

    /* MIME and publishing */
    const val MIMEVersion: String = "MIME-Version"
    const val SLUG: String = "SLUG"

    /* Linking */
    const val Link: String = "Link"

    /* Server-Sent Events */
    const val LastEventID: String = "Last-Event-ID"

    /* Forwarding and proxies */
    const val Forwarded: String = "Forwarded"
    const val XForwardedFor: String = "X-Forwarded-For"
    const val XForwardedHost: String = "X-Forwarded-Host"
    const val XForwardedPort: String = "X-Forwarded-Port"
    const val XForwardedProto: String = "X-Forwarded-Proto"
    const val XForwardedServer: String = "X-Forwarded-Server"

    /* Proxy diagnostics */
    const val ProxyStatus: String = "Proxy-Status"

    /* Request tracing and application metadata */
    const val XCorrelationId: String = "X-Correlation-ID"
    const val XRequestId: String = "X-Request-ID"
    const val XTotalCount: String = "X-Total-Count"

    /* Method overrides */
    const val XHttpMethodOverride: String = "X-HTTP-Method-Override"

    /* Text and Data Mining */
    const val TDMPolicy: String = "TDM-Policy"
    const val TDMReservation: String = "TDM-Reservation"

    /* Observability */
    const val ServerTiming: String = "Server-Timing"
    const val Traceparent: String = "traceparent"
    const val Tracestate: String = "tracestate"

    /* Lifecycle */
    const val Deprecation: String = "Deprecation"
    const val Sunset: String = "Sunset"

    /* Fetch metadata */
    const val SecFetchDest: String = "Sec-Fetch-Dest"
    const val SecFetchMode: String = "Sec-Fetch-Mode"
    const val SecFetchSite: String = "Sec-Fetch-Site"
    const val SecFetchUser: String = "Sec-Fetch-User"
}