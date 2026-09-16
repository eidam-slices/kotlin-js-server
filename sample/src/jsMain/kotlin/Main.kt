package cz.eidam.kotlinjs.server

import cz.eidam.kotlinjs.server.engine.embeddedServer
import cz.eidam.kotlinjs.server.node.Node
import cz.eidam.kotlinjs.server.response.respondText
import cz.eidam.kotlinjs.server.routing.get
import cz.eidam.kotlinjs.server.routing.routing

fun main() {
    val server = embeddedServer(
        factory = Node,
        port = 8080
    ) {
        install(ExceptionLogging)

        routing {
            get("/hello-world") {
                call.respondText("Hello world from Kotlin/JS Node.js server!")
            }
        }
    }.start(wait = true)

    println("server started on ${server.config.port}")
}