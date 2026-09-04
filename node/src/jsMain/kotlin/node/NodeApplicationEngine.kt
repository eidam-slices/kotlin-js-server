package cz.eidam.kotlinjs.server.node

import cz.eidam.kotlinjs.server.application.Application
import cz.eidam.kotlinjs.server.engine.ApplicationEngine
import cz.eidam.kotlinjs.server.engine.ApplicationEngineFactory
import kotlinx.coroutines.launch
import node.http.createServer


class NodeApplicationEngine(
    val configuration: Configuration,
    val application: Application,
): ApplicationEngine {

    private var server: NodeServer? = null
    val running: Boolean get() = server != null

    override fun start(wait: Boolean): ApplicationEngine {
        if (running) return this

        val instance: NodeServer = createServer { request, response ->
            application.launch {
                val call = NodeApplicationCall(request, response)
                application.execute(call)
            }
        }
        instance.listen(
            port = configuration.port,
            hostname = configuration.host
        )

        this.server = instance
        return this
    }

    override fun stop() {
        val server = this.server ?: return

        server.close {
            this.server = null
        }
    }

    data class Configuration(
        override var port: Int,
        override var host: String
    ): ApplicationEngine.Configuration(port, host)
}

object Node: ApplicationEngineFactory<NodeApplicationEngine, NodeApplicationEngine.Configuration> {
    override fun configuration(
        port: Int,
        host: String,
        configure: NodeApplicationEngine.Configuration.() -> Unit
    ): NodeApplicationEngine.Configuration {
        return NodeApplicationEngine.Configuration(
            port = port,
            host = host
        ).apply(block = configure)
    }

    override fun create(
        configuration: NodeApplicationEngine.Configuration,
        application: Application
    ): NodeApplicationEngine {
        return NodeApplicationEngine(configuration, application)
    }

}