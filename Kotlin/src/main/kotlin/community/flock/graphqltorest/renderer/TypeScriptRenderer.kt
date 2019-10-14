package community.flock.graphqltorest.renderer

import community.flock.graphqltorest.exceptions.EnumTypeDefinitionRenderException
import community.flock.graphqltorest.exceptions.InputObjectTypeDefinitionRenderException
import community.flock.graphqltorest.exceptions.InterfaceTypeDefinitionRenderException
import community.flock.graphqltorest.exceptions.ScalarTypeDefinitionRenderException
import community.flock.graphqltorest.renderer.meta.Renderer
import graphql.language.*

class TypeScriptRenderer : Renderer() {

    override fun ObjectTypeDefinition.renderObjectTypeDefinition() = "export interface $name {\n" +
            fieldDefinitions.renderFields() +
            "\n}\n"

    override fun List<FieldDefinition>.renderFields() = joinToString(";\n") { it.renderField() } + ";"
    override fun FieldDefinition.renderField() = "$name${type.renderType()}"

    override fun InputObjectTypeDefinition.renderInputObjectTypeDefinition(): String = throw InputObjectTypeDefinitionRenderException(this)
    override fun ScalarTypeDefinition.renderScalarTypeDefinition(): String? = when (name) {
        "Date" -> null
        else -> throw ScalarTypeDefinitionRenderException(this)
    }

    override fun EnumTypeDefinition.renderEnumTypeDefinition(): String = throw EnumTypeDefinitionRenderException(this)
    override fun InterfaceTypeDefinition.renderInterfaceTypeDefinition(): String = throw InterfaceTypeDefinitionRenderException(this)

    override fun nullableListOf(type: Type<Type<*>>): String = "?: ${type.render().render()}[]"
    override fun nonNullableListOf(type: Type<Type<*>>): String = ": ${type.render().render()}[]"
    override fun String.toNullable(): String = "?: ${render()}"
    override fun String.toNonNullable(): String = ": ${render()}"

    private fun String.render() = when (this) {
        "String" -> "string"
        "Int" -> "number"
        "Float" -> "number"
        "Boolean" -> "boolean"
        "ID" -> "string"
        else -> this
    }

}
