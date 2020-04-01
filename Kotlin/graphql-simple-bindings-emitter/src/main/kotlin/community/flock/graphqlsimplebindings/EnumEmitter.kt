package community.flock.graphqlsimplebindings

import graphql.language.EnumTypeDefinition
import graphql.language.EnumValueDefinition
import graphql.language.FieldDefinition
import graphql.language.InterfaceTypeDefinition

internal interface EnumEmitter {

    fun EnumTypeDefinition.emitEnumTypeDefinition(): String

    fun InterfaceTypeDefinition.emitInterfaceTypeDefinition(): String

    fun List<EnumValueDefinition>.emitEnumFields(): String

    fun EnumValueDefinition.emitEnumField(): String

}
