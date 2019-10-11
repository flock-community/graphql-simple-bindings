package community.flock.graphqltorest.renderer

import community.flock.graphqltorest.exceptions.FieldTypeRenderException
import graphql.language.*
import graphql.schema.idl.TypeInfo.typeInfo

class KotlinRenderer : Renderer() {

    override fun renderObjectTypeDefinition(definition: ObjectTypeDefinition) = """
        data class ${definition.name}(
            ${definition.fieldDefinitions.renderFields()}
        )
    """.trimIndent() + "\n".also {
        println(it)
    }

    private fun List<FieldDefinition>.renderFields() = joinToString(",\n") { it.renderField() }
    private fun FieldDefinition.renderField() = "val $name: ${type.renderType()}"

    private fun Type<Type<*>>.renderType(): String = when (this) {
        is NonNullType -> type.renderNonNullType()
        is ListType -> "List<${type.renderType()}>?"
        is TypeName -> "${typeInfo(this).name}?"
        else -> throw FieldTypeRenderException(this)
    }

    private fun Type<Type<*>>.renderNonNullType(): String = when (this) {
        is NonNullType -> type.renderNonNullType()
        is ListType -> "List<${type.renderType()}>"
        is TypeName -> typeInfo(this).name
        else -> throw FieldTypeRenderException(this)
    }

    override fun renderInputObjectTypeDefinition(definition: InputObjectTypeDefinition): String = "InputObjectType"
    override fun renderEnumTypeDefinition(definition: EnumTypeDefinition): String = "EnumType"
    override fun renderInterfaceTypeDefinition(definition: InterfaceTypeDefinition): String = "InterfaceType"
    override fun renderScalarTypeDefinition(definition: ScalarTypeDefinition): String? = when (val name = definition.name) {
        "Date" -> "typealias Date = java.time.LocalDate"
        else -> null
    }
}
