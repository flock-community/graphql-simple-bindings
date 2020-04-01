package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.*

internal interface DefinitionEmitter {

    fun ObjectTypeDefinition.emitObjectTypeDefinition(): String

    fun ScalarTypeDefinition.emitScalarTypeDefinition(): String?

    fun InputObjectTypeDefinition.emitInputObjectTypeDefinition(): String

    fun InterfaceTypeDefinition.emitInterfaceTypeDefinition(): String

}
