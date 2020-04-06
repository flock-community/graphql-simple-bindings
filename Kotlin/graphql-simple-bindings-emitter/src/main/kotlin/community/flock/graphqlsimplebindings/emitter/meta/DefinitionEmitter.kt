package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.*

internal interface DefinitionEmitter {

    fun ObjectTypeDefinition.emitObjectTypeDefinition(document: Document): String

    fun ScalarTypeDefinition.emitScalarTypeDefinition(): String?

}
