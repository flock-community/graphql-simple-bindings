package community.flock.graphqlsimplebindings.renderer.meta

import graphql.language.Type

internal interface TypeRenderer {

    fun nullableListOf(type: Type<Type<*>>): String

    fun nonNullableListOf(type: Type<Type<*>>): String

    fun String.toNullable(): String

    fun String.toNonNullable(): String

}
