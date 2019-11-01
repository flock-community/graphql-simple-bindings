package community.flock.graphqlsimplebindings.renderer.meta

import graphql.language.FieldDefinition

internal interface FieldRenderer {

    fun List<FieldDefinition>.renderFields(): String

    fun FieldDefinition.renderField(): String

}
