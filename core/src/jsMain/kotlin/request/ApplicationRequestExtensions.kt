package cz.eidam.kotlinjs.server.request

import kotlinx.coroutines.flow.toList

// region REQUEST EXTENSION FUNCTIONS
suspend fun ApplicationRequest.bytes(): ByteArray {
    val chunks = this.body().toList()

    return when (chunks.size) {
        0 -> ByteArray(0)
        1 -> chunks.first()
        else -> {
            val size = chunks.sumOf { array -> array.size }

            val result = ByteArray(size)
            var offset = 0

            for (chunk in chunks) {
                chunk.copyInto(result, offset)
                offset += chunk.size
            }
            result
        }
    }
}

// TODO: add support for charset from Content-Type header or custom through parameter
suspend fun ApplicationRequest.text(): String {
    val bytes = this.bytes()
    return bytes.decodeToString()
}
// endregion