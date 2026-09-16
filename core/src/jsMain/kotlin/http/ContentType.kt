@file:Suppress("ConstPropertyName", "Nothing_To_Inline", "Unused")

package cz.eidam.kotlinjs.server.http

value class ContentType(val value: String) {

    fun parameter(name: String): String? {
        val length = value.length
        var current = value.indexOf(';')

        if (current.isNotFound()) return null
        current++

        while (current < length) {
            current = value.skipSpaces(current, length)
            if (current >= length) break

            val nameStart = current

            var equals = -1
            var end = length

            var inQuotes = false
            var isEscaped = false

            while (current < length) {
                val char = value[current]

                if (isEscaped) {
                    isEscaped = false
                    current++
                    continue
                }

                when (char) {
                    '\\' -> {
                        if (inQuotes) isEscaped = true
                    }
                    '"' -> {
                        inQuotes = !inQuotes
                    }
                    '=' -> {
                        if (!inQuotes && equals.isNotFound()) {
                            equals = current
                        }
                    }
                    ';' -> {
                        if (!inQuotes) {
                            end = current
                            break
                        }
                    }
                }

                current++
            }

            if (!equals.isNotFound()) {
                val nameEnd = value.skipSpacesBackwards(equals, nameStart)
                val nameLength = nameEnd - nameStart

                val matches =
                    nameLength == name.length &&
                            value.regionMatches(
                                nameStart,
                                name,
                                0,
                                nameLength,
                                ignoreCase = true
                            )

                if (matches) {
                    var valueStart = value.skipSpaces(equals + 1, end)
                    var valueEnd = value.skipSpacesBackwards(end, valueStart)

                    val quoted =
                        valueEnd - valueStart >= 2 &&
                                value[valueStart] == '"' &&
                                value[valueEnd - 1] == '"'

                    if (quoted) {
                        valueStart++
                        valueEnd--
                    }

                    return value.substring(valueStart, valueEnd)
                }
            }

            current = end + 1
        }

        return null
    }

    override fun toString(): String = value

    object Text {
        const val type = "text"

        val Plain = ContentType("$type/plain")
        val Calendar = ContentType("$type/calendar")

        val JavaScript = ContentType("$type/javascript")

        val Html = ContentType("$type/html")
        val Css = ContentType("$type/css")

        val Markdown = ContentType("$type/markdown")
        val Xml = ContentType("$type/xml")
        val Csv = ContentType("$type/csv")


        val EventStream = ContentType("$type/event-stream")
    }

    object Application {
        const val type = "application"

        val OctetStream = ContentType("$type/octet-stream")

        val Zip = ContentType("$type/zip")
        val Gzip = ContentType("$type/gzip")
        val Zstd = ContentType("$type/zstd")

        @Deprecated(
            "Since RFC 9239 publish, text/javascript is the only official MIME for JavaScript.",
            replaceWith = ReplaceWith("ContentType.Text.JavaScript")
        )
        val JavaScript = ContentType("$type/javascript")
        val Json = ContentType("$type/json")
        val Cbor = ContentType("$type/cbor")
        val Protobuf = ContentType("$type/protobuf")
        val ProtobufJson = ContentType("$type/protobuf+json")
        val Wasm = ContentType("$type/wasm")
        val Xml = ContentType("$type/xml")
        val Rtf = ContentType("$type/rtf")

        val Pdf = ContentType("$type/pdf")
        val Ogg = ContentType("$type/ogg")

        val FormUrlEncoded = ContentType("$type/x-www-form-urlencoded")

        val ProblemJson = ContentType("$type/problem+json")
        val ProblemXml = ContentType("$type/problem+xml")
    }

    object Multipart {
        const val type = "multipart"

        val FormData = ContentType("$type/form-data")
        val Mixed = ContentType("$type/mixed")
        val Alternative = ContentType("$type/alternative")
        val Related = ContentType("$type/related")
        val ByteRanges = ContentType("$type/bytesranges")
    }

    object Image {
        const val type = "image"

        val Icon = ContentType("$type/x-icon")
        val Jpeg = ContentType("$type/jpeg")
        val Png = ContentType("$type/png")
        val Webp = ContentType("$type/webp")
        val Svg = ContentType("$type/svg+xml")
        val Tiff = ContentType("$type/tiff")
        val Avif = ContentType("$type/avif")
        val Bmp = ContentType("$type/bmp")
        val Heic = ContentType("${type}/heic")
        val Heif = ContentType("${type}/heif")

        val Apng = ContentType("$type/apng")
        val Gif = ContentType("$type/gif")

    }

    object Audio {
        const val type = "audio"

        val Wav = ContentType("$type/wav")
        val Webm = ContentType("$type/webm")
        val Aac = ContentType("$type/aac")
        val Mp4 = ContentType("$type/mp4")
        val Mpeg = ContentType("$type/mpeg")
        val Ogg = ContentType("$type/ogg")
        val Midi = ContentType("$type/midi")
        val Flac = ContentType("$type/flac")
    }

    object Video {
        const val type = "video"

        val Mp4 = ContentType("$type/mp4")
        val Mpeg = ContentType("$type/mpeg")
        val Ogg = ContentType("$type/ogg")
        val Webm = ContentType("$type/webm")
        val QuickTime = ContentType("$type/quicktime")
        val Avi = ContentType("$type/x-msvideo")
        val Matroska = ContentType("$type/matroska")
        val Av1 = ContentType("$type/AV1")

    }

    object Font {
        const val type = "font"

        val Otf = ContentType("$type/otf")
        val Ttf = ContentType("$type/ttf")
        val Woff = ContentType("$type/woff")
        val Woff2 = ContentType("$type/woff2")
    }

    companion object {
        val Any = ContentType("*/*")
    }
}

/* Extensions */
fun ContentType.withoutParameters(): ContentType {
    val semicolon = value.indexOf(';')
    if (semicolon.isNotFound()) return this

    val end = value.skipSpacesBackwards(semicolon, 0)
    return ContentType(value.substring(0, end))
}

fun ContentType.withParameter(name: String, value: String): ContentType {
    return ContentType("${this.value}; $name=$value")
}

fun ContentType.withCharset(charset: HttpCharset, force: Boolean = true): ContentType {
    return if (!force && !value.startsWith(ContentType.Text.type + "/", ignoreCase = true)) {
        this
    } else {
        this.withParameter("charset", charset.value)
    }
}

/* Private Utils */
private inline fun Int.isNotFound(): Boolean = equals(-1)

private inline fun Char.isWhitespaceChar(): Boolean {
    return this == ' ' || this == '\t'
}

private inline fun String.skipSpaces(from: Int, to: Int): Int {
    var idx = from
    while (idx < to && this[idx].isWhitespaceChar()) idx++
    return idx
}

private inline fun String.skipSpacesBackwards(to: Int, from: Int): Int {
    var idx = to
    while (idx > from && this[idx - 1].isWhitespaceChar()) idx--
    return idx
}