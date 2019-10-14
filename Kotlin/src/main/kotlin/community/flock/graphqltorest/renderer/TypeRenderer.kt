package community.flock.graphqltorest.renderer

internal interface TypeRenderer {

    fun nullableListOf(type: String): String

    fun nonNullableListOf(type: String): String

    fun String.toNullable(): String

    fun String.toNonNullable(): String

}
