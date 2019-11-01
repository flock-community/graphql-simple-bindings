package community.flock.graphqltorest.renderer

import community.flock.graphqltorest.exceptions.EnumTypeDefinitionRenderException
import community.flock.graphqltorest.exceptions.InputObjectTypeDefinitionRenderException
import community.flock.graphqltorest.exceptions.InterfaceTypeDefinitionRenderException
import community.flock.graphqltorest.exceptions.ScalarTypeDefinitionRenderException
import community.flock.graphqltorest.renderer.meta.Renderer
import graphql.language.*

class KotlinRenderer(private val packageName: String = "community.flock.graphqltorest.generated") : Renderer() {

    override fun renderDocument(document: Document): String = super.renderDocument(document)
            .let { "package $packageName\n\n$it" }

    override fun ObjectTypeDefinition.renderObjectTypeDefinition() = "data class $name(\n${fieldDefinitions.renderFields()}\n)\n"

    override fun List<FieldDefinition>.renderFields() = joinToString(",\n") { it.renderField() }
    override fun FieldDefinition.renderField() = "        val $name: ${type.renderType()}"

    override fun InputObjectTypeDefinition.renderInputObjectTypeDefinition(): String = throw InputObjectTypeDefinitionRenderException(this)

    override fun ScalarTypeDefinition.renderScalarTypeDefinition(): String? = when (name) {
        "Date" -> "typealias Date = java.time.LocalDate\n"
        else -> throw ScalarTypeDefinitionRenderException(this)
    }

    override fun EnumTypeDefinition.renderEnumTypeDefinition(): String = throw EnumTypeDefinitionRenderException(this)
    override fun InterfaceTypeDefinition.renderInterfaceTypeDefinition(): String = throw InterfaceTypeDefinitionRenderException(this)

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List<${type.renderType()}>"
    override fun String.toNullable(): String = "${toNonNullable()}?"
    override fun String.toNonNullable(): String = this

}
