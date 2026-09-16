package cz.eidam.kotlinjs.server.application

import cz.eidam.kotlinjs.server.http.HttpStatusCode
import cz.eidam.kotlinjs.server.response.ApplicationResponse
import cz.eidam.kotlinjs.server.response.CommitableResponse
import cz.eidam.kotlinjs.server.routing.RouteResolveResult
import cz.eidam.kotlinjs.server.routing.RoutingCall
import cz.eidam.kotlinjs.server.routing.RoutingContext
import cz.eidam.kotlinjs.server.routing.RoutingRoot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class Application(
    parentCoroutineContext: CoroutineContext = Dispatchers.Default,
): CoroutineScope {
    private val job = SupervisorJob(parentCoroutineContext[Job])
    override val coroutineContext = parentCoroutineContext + job

    private val pipeline = ApplicationPipeline()
    val routing = RoutingRoot(this)

    fun install(plugin: ApplicationPlugin) {
        plugin.install(this.pipeline)
    }

    suspend fun execute(call: ApplicationCall) {
        try {
            // invoke handlers before route is resolved
            for (handler in pipeline.beforeRouting) handler.invoke(call)

            val result = routing.resolve(call)

            when (result) {
                is RouteResolveResult.Success -> {
                    val routingCall = RoutingCall(call, result.parameters, result.route)

                    // invoke handlers after route is resolved
                    for (handler in pipeline.afterRouting) handler.invoke(routingCall)

                    val routingContext = RoutingContext(routingCall)
                    for (handler in result.route.handlers) {
                        handler.invoke(routingContext)
                    }
                }

                is RouteResolveResult.Failure -> {
                    call.response.status(result.code)
                }
            }

        } catch (e: Throwable) {
            call.response.status(HttpStatusCode.InternalServerError)
            // run on error
            for (handler in pipeline.onError) handler.invoke(call, e)
        }

        for (handler in pipeline.beforeSend) handler.invoke(call)

        call.response.commit()

        for (handler in pipeline.afterSend) handler.invoke(call)
    }

    fun dispose() {
        job.cancel()
    }

    // TODO: maybe think of some more robust way to get CommitableResponse instance
    private suspend fun ApplicationResponse.commit() {
        val casted = this as? CommitableResponse
            ?: error("Passed ApplicationRequest instance could not be casted to CommitableResponse.")
        casted.commit()
    }
}