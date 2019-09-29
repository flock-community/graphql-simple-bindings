package community.flock.graphqltorest.renderer

import graphql.language.*
import graphql.schema.idl.TypeInfo.typeInfo

class KotlinRenderer : Renderer() {

    override fun renderObjectTypeDefinition(definition: ObjectTypeDefinition) = """
        data class ${definition.name}(
            ${definition.fieldDefinitions.renderFields()}
        )
    """.trimIndent() + "\n"

    private fun List<FieldDefinition>.renderFields() = joinToString(",\n") { it.renderField() }
    private fun FieldDefinition.renderField() = "val $name: ${renderType(type)}"

    private fun <T : Type<*>> renderType(type: Type<T>) = typeInfo(type).name.let {
        when (type) {
            is NonNullType -> it
            is ListType -> "List<${render(it)}>?"
            else -> "${render(it)}?"
        }
    }

    private fun render(type: String) = when (type) {
        "Date" -> "LocalDate"
        "ID" -> "String"
        else -> type
    }

    override fun renderInputObjectTypeDefinition(definition: InputObjectTypeDefinition): String = "InputObjectType"
    override fun renderEnumTypeDefinition(definition: EnumTypeDefinition): String = "EnumType"
    override fun renderInterfaceTypeDefinition(definition: InterfaceTypeDefinition): String = "InterfaceType"
    override fun renderScalarTypeDefinition(definition: ScalarTypeDefinition): String = when (val name = definition.name) {
        "Date" -> "LocalDate"
        else -> name
    }
}
