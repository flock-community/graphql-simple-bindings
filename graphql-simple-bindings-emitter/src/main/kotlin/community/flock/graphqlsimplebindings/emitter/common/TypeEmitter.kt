package community.flock.graphqlsimplebindings.emitter.common

import graphql.language.Type

internal interface TypeEmitter {

    fun nullableListOf(type: Type<Type<*>>): String

    fun nonNullableListOf(type: Type<Type<*>>): String

    fun String.toNullable(): String

    fun String.toNonNullable(): String

}
