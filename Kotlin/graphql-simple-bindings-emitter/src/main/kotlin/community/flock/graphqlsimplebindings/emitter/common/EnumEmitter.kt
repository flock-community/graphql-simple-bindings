package community.flock.graphqlsimplebindings.emitter.common

import graphql.language.EnumTypeDefinition
import graphql.language.EnumValueDefinition

internal interface EnumEmitter {

    fun EnumTypeDefinition.emit(): String

    fun EnumValueDefinition.emit(): String

}
