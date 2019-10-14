package community.flock.graphqltorest.renderer

import graphql.language.*

internal interface DefinitionRenderer {

    fun ObjectTypeDefinition.renderObjectTypeDefinition(): String

    fun ScalarTypeDefinition.renderScalarTypeDefinition(): String

    fun InputObjectTypeDefinition.renderInputObjectTypeDefinition(): String

    fun EnumTypeDefinition.renderEnumTypeDefinition(): String

    fun InterfaceTypeDefinition.renderInterfaceTypeDefinition(): String

}
