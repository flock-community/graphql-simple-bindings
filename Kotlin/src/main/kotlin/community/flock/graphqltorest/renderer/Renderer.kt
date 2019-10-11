package community.flock.graphqltorest.renderer

import community.flock.graphqltorest.exceptions.DefinitionRenderException
import community.flock.graphqltorest.exceptions.TypeRenderException
import graphql.language.*
import graphql.schema.idl.TypeInfo

abstract class Renderer {

    open fun renderDocument(document: Document): String = document.definitions
            .mapNotNull { it.renderDefinition() }
            .joinToString("\n")

    private fun Definition<Definition<*>>.renderDefinition() = when (this) {
        is ObjectTypeDefinition -> renderObjectTypeDefinition()
        is ScalarTypeDefinition -> renderScalarTypeDefinition()
        is InputObjectTypeDefinition -> renderInputObjectTypeDefinition()
        is EnumTypeDefinition -> renderEnumTypeDefinition()
        is InterfaceTypeDefinition -> renderInterfaceTypeDefinition()
        else -> throw DefinitionRenderException(this)
    }

    protected abstract fun ObjectTypeDefinition.renderObjectTypeDefinition(): String
    protected abstract fun ScalarTypeDefinition.renderScalarTypeDefinition(): String?
    protected abstract fun InputObjectTypeDefinition.renderInputObjectTypeDefinition(): String
    protected abstract fun EnumTypeDefinition.renderEnumTypeDefinition(): String
    protected abstract fun InterfaceTypeDefinition.renderInterfaceTypeDefinition(): String

    protected fun List<FieldDefinition>.renderFields() = joinToString(",\n") { it.renderField() }
    protected abstract fun FieldDefinition.renderField(): String

    protected abstract fun nullableListOf(type: String): String
    protected abstract fun nonNullableListOf(type: String): String
    protected abstract fun String.toNullable(): String
    protected abstract fun String.toNonNullable(): String

    protected fun Type<Type<*>>.renderType(): String = when (this) {
        is NonNullType -> type.toNonNullableType()
        is ListType -> nullableListOf(type.renderType())
        is TypeName -> toName().toNullable()
        else -> throw TypeRenderException(this)
    }

    private fun Type<Type<*>>.toNonNullableType(): String = when (this) {
        is ListType -> nonNullableListOf(type.renderType())
        is TypeName -> toName().toNonNullable()
        else -> throw TypeRenderException(this)
    }

    private fun Type<Type<*>>.toName(): String = TypeInfo.typeInfo(this).name

}
