package community.flock.graphqltorest.renderer

import graphql.language.FieldDefinition

internal interface FieldRenderer {

    fun List<FieldDefinition>.renderFields(): String

    fun FieldDefinition.renderField(): String

}
