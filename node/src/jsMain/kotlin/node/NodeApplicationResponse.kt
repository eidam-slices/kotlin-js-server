package cz.eidam.kotlinjs.server.node

import cz.eidam.kotlinjs.server.engine.BaseApplicationResponse
import cz.eidam.kotlinjs.server.http.content.OutgoingContent
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import js.typedarrays.toUint8Array
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class NodeApplicationResponse(
    private val response: NodeResponse
): BaseApplicationResponse() {


    override suspend fun respond(
        status: HttpStatusCode,
        headers: Headers,
        body: OutgoingContent
    ) {

        response.statusCode = status.value.toDouble()

        headers.forEach { name, values ->
            response.appendHeader(name, values.toTypedArray())
        }

        when (body) {
            is OutgoingContent.NoContent -> {
                response.end()
            }

            is OutgoingContent.Bytes -> {
                response.end(data = body.bytes.toUint8Array())
            }

            is OutgoingContent.Text -> {
                response.end(data = body.text)
            }

            is OutgoingContent.Stream -> {
                try {
                    body.flow.collect { chunk ->
                        val chunk = chunk.toUint8Array()
                        val canWriteMore = response.write(chunk)

                        // if buffer is full, wait for the "drain" event before continuing to write
                        if (!canWriteMore) {
                            suspendCancellableCoroutine { continuation ->
                                response.once("drain") { continuation.resume(Unit) }
                            }
                        }

                    }
                } finally {
                    response.end()
                }
            }
        }


    }
}
