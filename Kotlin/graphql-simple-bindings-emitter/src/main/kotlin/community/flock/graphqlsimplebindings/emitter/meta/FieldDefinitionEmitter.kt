package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.FieldDefinition
import graphql.language.ObjectTypeDefinition

internal interface FieldDefinitionEmitter {

    fun ObjectTypeDefinition.emitFields(): String

    fun FieldDefinition.emitDefinitionField(): String

}
