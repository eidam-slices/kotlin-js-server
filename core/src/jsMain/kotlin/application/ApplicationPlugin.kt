package cz.eidam.kotlinjs.server.application

fun interface ApplicationPlugin {
    fun install(pipeline: ApplicationPipeline)
}