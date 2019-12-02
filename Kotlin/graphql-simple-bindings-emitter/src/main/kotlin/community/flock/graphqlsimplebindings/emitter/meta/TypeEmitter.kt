package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.Type

internal interface TypeEmitter {

    fun nullableListOf(type: Type<Type<*>>): String

    fun nonNullableListOf(type: Type<Type<*>>): String

    fun String.toNullable(): String

    fun String.toNonNullable(): String

}
