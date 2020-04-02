package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.*

internal interface InputEmitter {

    fun InputObjectTypeDefinition.emitInputObjectTypeDefinition(): String

    fun List<InputValueDefinition>.emitInputFields(): String

    fun InputValueDefinition.emitInputField(): String

}
