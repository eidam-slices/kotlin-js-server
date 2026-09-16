@file:Suppress("Unused")

package cz.eidam.kotlinjs.server.http

/* region Factory Functions */
fun parametersOf(): Parameters = Parameters.Empty
fun parametersOf(name: String, value: String): Parameters = parametersOf(name, listOf(value))
fun parametersOf(name: String, values: List<String>): Parameters = SingleParameters(name, values)
fun parametersOf(name: String, vararg values: String): Parameters = parametersOf(name, values.asList())

inline fun parameters(block: MutableParameters.() -> Unit): Parameters {
    val builder = MapMutableParameters().apply(block)
    return builder.build()
}
/*endregion*/

/* region Parameters Extensions */
operator fun Parameters.plus(other: Parameters): Parameters {
    if (other.isEmpty()) return this
    if (this.isEmpty()) return other

    return parameters {
        append(this@plus)
        append(other)
    }
}

infix fun Parameters.union(other: Parameters): Parameters {
    return parameters {
        unify(this@union)
        unify(other)
    }
}
/*endregion*/

/* region MutableParameters Extensions */
fun MutableParameters.append(parameters: Parameters) {
    if (parameters.isEmpty()) return
    for ((name, values) in parameters.entries) {
        append(name, values)
    }
}

fun MutableParameters.unify(parameters: Parameters) {
    if (parameters.isEmpty()) return
    for ((name, values) in parameters.entries) {
        unify(name, values)
    }
}

// TODO: consider if this function is worth keeping and its functionality is clear
operator fun MutableParameters.plusAssign(other: Parameters) = append(other)
/*endregion*/
