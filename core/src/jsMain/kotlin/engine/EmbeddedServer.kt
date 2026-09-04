package cz.eidam.kotlinjs.server.engine

import cz.eidam.kotlinjs.server.application.Application

class EmbeddedServer<E: ApplicationEngine, C: ApplicationEngine.Configuration>(
    val engine: E,
    val config: C,
    val application: Application,
) {
    fun start(wait: Boolean = false): EmbeddedServer<E, C> {
        engine.start(wait)
        return this
    }

    fun stop() {
        engine.stop()
    }
}

fun <E: ApplicationEngine, C: ApplicationEngine.Configuration> embeddedServer(
    factory: ApplicationEngineFactory<E, C>,
    port: Int = 8080,
    host: String = "0.0.0.0",
    configure: C.() -> Unit = {},
    module: Application.() -> Unit
): EmbeddedServer<E, C> {
    val application = Application().apply(module)
    val config = factory.configuration(port, host, configure)
    val engine = factory.create(config, application)

    return EmbeddedServer(engine, config, application)
}