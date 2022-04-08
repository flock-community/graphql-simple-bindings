package community.flock.graphqlsimplebindings.emitter.common

import graphql.language.Document
import graphql.language.ObjectTypeDefinition
import graphql.language.ScalarTypeDefinition

internal interface DefinitionEmitter {

    fun ObjectTypeDefinition.emit(document: Document): String

    fun ScalarTypeDefinition.emit(): String?

}
