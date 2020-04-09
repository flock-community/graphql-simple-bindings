package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.Document
import graphql.language.FieldDefinition
import graphql.language.ObjectTypeDefinition

internal interface FieldDefinitionEmitter {

    fun ObjectTypeDefinition.emitFields(document: Document): String

    fun FieldDefinition.emitDefinitionField(): String

}
