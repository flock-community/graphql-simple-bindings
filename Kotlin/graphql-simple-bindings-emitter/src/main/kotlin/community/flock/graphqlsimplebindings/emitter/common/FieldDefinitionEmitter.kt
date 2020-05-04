package community.flock.graphqlsimplebindings.emitter.common

import graphql.language.FieldDefinition

internal interface FieldDefinitionEmitter {

    fun FieldDefinition.emit(): String

}
