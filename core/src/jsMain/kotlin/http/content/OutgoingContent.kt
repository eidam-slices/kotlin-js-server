package cz.eidam.kotlinjs.server.http.content

import kotlinx.coroutines.flow.Flow

// TODO: think about using value classes
sealed interface OutgoingContent {
    object NoContent: OutgoingContent

    class Text(val text: String): OutgoingContent

    class Bytes(val bytes: ByteArray): OutgoingContent

    class Stream(val flow: Flow<ByteArray>): OutgoingContent
}