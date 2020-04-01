package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.FieldDefinition

internal interface FieldDefinitionEmitter {

    fun List<FieldDefinition>.emitDefinitionFields(): String

    fun FieldDefinition.emitDefinitionField(): String

}
