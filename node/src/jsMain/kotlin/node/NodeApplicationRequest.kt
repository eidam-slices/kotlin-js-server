package cz.eidam.kotlinjs.server.node

import cz.eidam.kotlinjs.server.engine.BaseApplicationRequest
import cz.eidam.kotlinjs.server.http.Headers
import cz.eidam.kotlinjs.server.http.HttpMethod
import cz.eidam.kotlinjs.server.http.Parameters
import js.buffer.ArrayBuffer
import js.typedarrays.Int8Array
import js.typedarrays.asByteArray
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import node.buffer.Buffer
import node.events.EventListener
import node.events.EventType
import web.url.URLSearchParams

// TODO: remove error invocations and handle nulls properly
class NodeApplicationRequest(
    private val request: NodeRequest,
): BaseApplicationRequest() {
    private typealias NodeBuffer = Buffer<*>


    override val method: HttpMethod
        get() = HttpMethod(request.method ?: error("Request method is null. TODO: handle."))

    override val query: Parameters by lazy(LazyThreadSafetyMode.NONE) {
        val url = request.url ?: error("Request url is null. TODO: handle.")
        val query = url
            .substringAfter('?', "")
            .substringBefore('#')
        val parameters = URLSearchParams(query)
        NodeParameters(parameters)
    }
    override val headers: Headers by lazy(LazyThreadSafetyMode.NONE) {
        val headers = request.headersDistinct
        NodeHeaders(headers)
    }
    override val uri: String
        get() = request.url ?: error("Request url is null. TODO: handle.")


    override fun createNetworkStream(): Flow<ByteArray> = callbackFlow {
        if (request.readableEnded) {
            close()
            return@callbackFlow
        }
        // event types:
        val onDataEvent = EventType("data")
        val onEndEvent = EventType("end")
        val onErrorEvent = EventType("error")

        // listeners:
        val onData = EventListener { chunk: Any? ->
            val buffer = chunk.unsafeCast<NodeBuffer>()
            val bytes = Int8Array(
                buffer = buffer.buffer.unsafeCast<ArrayBuffer>(),
                byteOffset = buffer.byteOffset,
                length = buffer.byteLength
            )
            trySend(bytes.asByteArray())
        }
        val onEnd = EventListener { _ ->
            close()
        }
        val onError = EventListener { e: Any? ->
            close(e as? Throwable ?: Exception("Unknown error occurred during request body reading."))
        }

        request.on(onDataEvent, onData)
        request.once(onEndEvent, onEnd)
        request.once(onErrorEvent, onError)

        awaitClose {
            request.off(onDataEvent, onData)
            request.off(onEndEvent, onEnd)
            request.off(onErrorEvent, onError)
        }
    }
}

