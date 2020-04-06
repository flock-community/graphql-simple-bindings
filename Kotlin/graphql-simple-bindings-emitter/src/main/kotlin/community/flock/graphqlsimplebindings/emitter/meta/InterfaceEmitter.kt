package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.FieldDefinition
import graphql.language.InterfaceTypeDefinition
import graphql.language.ObjectTypeDefinition

internal interface InterfaceEmitter {

    fun InterfaceTypeDefinition.emitInterfaceTypeDefinition(): String

    fun InterfaceTypeDefinition.emitFields(): String

    fun FieldDefinition.emitField(): String

}
