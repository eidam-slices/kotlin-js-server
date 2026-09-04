package cz.eidam.kotlinjs.server.application

import cz.eidam.kotlinjs.server.routing.RoutingCall

typealias BeforeRoutingHandler = suspend ApplicationCall.() -> Unit
typealias AfterRoutingHandler = suspend RoutingCall.() -> Unit
typealias BeforeSendHandler = suspend ApplicationCall.() -> Unit
typealias AfterSendHandler = suspend ApplicationCall.() -> Unit

typealias OnErrorHandler = suspend ApplicationCall.(e: Throwable) -> Unit

class ApplicationPipeline {

    val beforeRouting: List<BeforeRoutingHandler>
        field = mutableListOf()
    val afterRouting: List<AfterRoutingHandler>
        field = mutableListOf()
    val beforeSend: List<BeforeSendHandler>
        field = mutableListOf()
    val afterSend: List<AfterSendHandler>
        field = mutableListOf()

    val onError: List<OnErrorHandler>
        field = mutableListOf()


    fun beforeRouting(handler: BeforeRoutingHandler) {
        this.beforeRouting.add(handler)
    }

    fun afterRouting(handler: AfterRoutingHandler) {
        this.afterRouting.add(handler)
    }

    fun beforeSend(handler: BeforeSendHandler) {
        this.beforeSend.add(handler)
    }

    fun afterSend(handler: AfterSendHandler) {
        this.afterSend.add(handler)
    }

    fun onError(handler: OnErrorHandler) {
        this.onError.add(handler)
    }
}