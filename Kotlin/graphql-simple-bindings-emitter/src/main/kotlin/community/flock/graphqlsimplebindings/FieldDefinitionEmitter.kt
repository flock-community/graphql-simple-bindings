package community.flock.graphqlsimplebindings

import graphql.language.FieldDefinition

internal interface FieldDefinitionEmitter {

    fun List<FieldDefinition>.emitDefinitionFields(): String

    fun FieldDefinition.emitDefinitionField(): String

}
