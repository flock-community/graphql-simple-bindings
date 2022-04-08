package community.flock.graphqlsimplebindings.emitter.common

import graphql.language.InterfaceTypeDefinition

internal interface InterfaceEmitter {

    fun InterfaceTypeDefinition.emit(): String

}
