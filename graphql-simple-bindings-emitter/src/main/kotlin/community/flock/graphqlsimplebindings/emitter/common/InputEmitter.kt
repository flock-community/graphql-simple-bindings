package community.flock.graphqlsimplebindings.emitter.common

import graphql.language.InputObjectTypeDefinition
import graphql.language.InputValueDefinition

internal interface InputEmitter {

    fun InputObjectTypeDefinition.emit(): String

    fun InputValueDefinition.emit(): String

}
