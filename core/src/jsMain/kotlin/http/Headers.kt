package cz.eidam.kotlinjs.server.http

import cz.eidam.kotlinjs.server.http.collections.Multimap
import cz.eidam.kotlinjs.server.http.collections.MultimapValidator
import cz.eidam.kotlinjs.server.http.collections.MutableMultimap

interface Headers: Multimap {
    companion object {
        val Empty: Headers = EmptyHeaders
    }
}

interface MutableHeaders: Headers, MutableMultimap

object HeadersValidator: MultimapValidator {
    private const val HTAB = 0x09
    private const val SP = 0x20
    private val VCHAR = 0x21..0x7E
    private val OBS_TEXT = 0x80..0xFF

    private fun Char.isHeaderNameChar(): Boolean {
        return when (this) {
            // tchar – allowed HTTP token characters
            in 'a'..'z',
            in 'A'..'Z',
            in '0'..'9',
            '!', '#', '$', '%', '&', '\'', '*',
            '+', '-', '.', '^', '_', '`', '|', '~' -> true
            else -> false
        }
    }

    private fun Char.isHeaderValueChar(): Boolean {
        return when (code) {
            HTAB -> true // horizontal tab ('\t')
            SP -> true // space (' ')

            in VCHAR -> true // all visible ASCII characters
            in OBS_TEXT -> true // legacy extended byte range

            else -> false
        }
    }

    private fun Char.isHeaderWhitespaceChar(): Boolean {
        return code == SP || code == HTAB
    }


    override fun validateName(name: String) {
        require(name.isNotEmpty()) {
            "Header name must not be empty."
        }
        require(name.all { it.isHeaderNameChar() }) {
            "Header name '$name' must not contain characters other than tchar."
        }
    }


    override fun validateValue(value: String) {
        if (value.isEmpty()) return

        require(!value.first().isHeaderWhitespaceChar() && !value.last().isHeaderWhitespaceChar()) {
            "Header value '$value' must not contain leading or trailing SP or HTAB characters."
        }
        require(value.all { it.isHeaderValueChar() }) {
            "Header value '$value' must not contain characters other than VCHAR, HTAB, SP, and obs-text."
        }
    }

    override fun validateValues(values: Collection<String>) {
        super.validateValues(values)
    }

    override fun validateValuesSize(values: Collection<String>) {
        require(values.isNotEmpty()) {
            "Header values must not be empty."
        }
    }
}
