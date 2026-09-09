@file:Suppress("NOTHING_TO_INLINE")

package cz.eidam.kotlinjs.server.request

import cz.eidam.kotlinjs.server.application.ApplicationCall
import kotlinx.coroutines.flow.Flow

suspend inline fun ApplicationCall.receiveBytes(): ByteArray {
    return this.request.bytes()
}

suspend inline fun ApplicationCall.receiveText(): String {
    return this.request.text()
}

inline fun ApplicationCall.receiveStream(): Flow<ByteArray> {
    return this.request.body()
}