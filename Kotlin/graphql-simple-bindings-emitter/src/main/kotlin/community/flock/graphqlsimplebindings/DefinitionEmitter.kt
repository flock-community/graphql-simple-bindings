package community.flock.graphqlsimplebindings

import graphql.language.*

internal interface DefinitionEmitter {

    fun ObjectTypeDefinition.emitObjectTypeDefinition(): String

    fun ScalarTypeDefinition.emitScalarTypeDefinition(): String?

    fun InputObjectTypeDefinition.emitInputObjectTypeDefinition(): String

}
