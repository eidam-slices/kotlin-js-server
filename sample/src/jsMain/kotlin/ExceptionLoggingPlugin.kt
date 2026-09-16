package cz.eidam.kotlinjs.server

import cz.eidam.kotlinjs.server.application.ApplicationPlugin

val ExceptionLogging = ApplicationPlugin { pipeline ->
    pipeline.onError { e ->
        val log = buildString {
            appendLine("[ERROR] ${request.method} ${request.path} failed:")
            appendLine(e.stackTraceToString())
        }
        console.error(log)
    }
}