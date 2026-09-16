package cz.eidam.kotlinjs.server.http

import cz.eidam.kotlinjs.server.http.collections.Multimap
import cz.eidam.kotlinjs.server.http.collections.MultimapValidator
import cz.eidam.kotlinjs.server.http.collections.MutableMultimap

interface Parameters: Multimap {
    companion object {
        val Empty: Parameters = EmptyParameters
    }
}

interface MutableParameters: Parameters, MutableMultimap

object ParametersValidator: MultimapValidator {
    override fun validateName(name: String) {
        require(name.isNotEmpty()) {
            "Parameter name must not be empty."
        }
    }

    override fun validateValue(value: String) {}

    override fun validateValuesSize(values: Collection<String>) {
        require(values.isNotEmpty()) {
            "Parameter values must not be empty."
        }
    }
}