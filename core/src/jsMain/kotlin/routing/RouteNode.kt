package cz.eidam.kotlinjs.server.routing

import cz.eidam.kotlinjs.server.routing.selectors.PathSegmentConstantRouteSelector
import cz.eidam.kotlinjs.server.routing.selectors.PathSegmentParameterRouteSelector
import cz.eidam.kotlinjs.server.routing.selectors.PathSegmentWildcardRouteSelector
import cz.eidam.kotlinjs.server.routing.selectors.RouteSelector

open class RouteNode(
    override val selector: RouteSelector,
    override val parent: RouteNode? = null,
): Route {

    final override val children: List<RouteNode>
        field = mutableListOf()

    val handlers: List<RouteHandler>
        field = mutableListOf()

    override fun child(selector: RouteSelector): RouteNode {
        val existing = children.find { it.selector == selector }
        if (existing != null) return existing

        val child = RouteNode(selector, this)
        children.add(child)
        return child
    }

    override fun handle(handler: RouteHandler) {
        handlers.add(handler)
    }

    fun path(): String {
        val parent = parent?.path()?.trimEnd('/')
        val current = when (selector) {
            is PathSegmentConstantRouteSelector -> (selector as PathSegmentConstantRouteSelector).value
            is PathSegmentParameterRouteSelector -> "{${(selector as PathSegmentParameterRouteSelector).name}}"
            is PathSegmentWildcardRouteSelector -> "*"
            else -> null
        }

        return when {
            parent == null && current == null -> "/"
            parent == null -> "/$current"
            current == null -> parent
            else -> "$parent/$current"
        }
    }
}
