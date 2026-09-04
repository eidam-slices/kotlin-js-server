package cz.eidam.kotlinjs.server.engine

import cz.eidam.kotlinjs.server.application.Application

interface ApplicationEngine {

    abstract class Configuration(
        open var port: Int,
        open var host: String,
    )

    fun start(wait: Boolean = false): ApplicationEngine
    fun stop()
}

interface ApplicationEngineFactory<E: ApplicationEngine, C: ApplicationEngine.Configuration> {
    fun configuration(port: Int, host: String, configure: C.() -> Unit): C
    fun create(configuration: C, application: Application): E
}


