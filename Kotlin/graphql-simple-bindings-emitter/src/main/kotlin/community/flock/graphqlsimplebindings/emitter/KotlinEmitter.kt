package community.flock.graphqlsimplebindings.emitter

import community.flock.graphqlsimplebindings.emitter.common.Emitter
import community.flock.graphqlsimplebindings.exceptions.ScalarTypeDefinitionEmitterException
import graphql.language.Document
import graphql.language.EnumTypeDefinition
import graphql.language.EnumValueDefinition
import graphql.language.FieldDefinition
import graphql.language.InputObjectTypeDefinition
import graphql.language.InputValueDefinition
import graphql.language.InterfaceTypeDefinition
import graphql.language.NonNullType
import graphql.language.ObjectTypeDefinition
import graphql.language.ScalarTypeDefinition
import graphql.language.Type
import graphql.language.TypeName


class KotlinEmitter(
    private val packageName: String = "community.flock.graphqlsimplebindings.generated",
    private val scalars: Map<String, String> = mapOf(),
    private val enableOpenApiAnnotations: Boolean = false
) : Emitter() {

    private val swaggerImport: String = when (enableOpenApiAnnotations) {
        true -> "import io.swagger.v3.oas.annotations.media.Schema\n\n"
        false -> ""
    }

    override fun emitDocument(document: Document): String = super.emitDocument(document)
        .let { "package $packageName\n\n$swaggerImport$it" }

    override fun ObjectTypeDefinition.emit(document: Document) = if (fieldDefinitions.size > 0) {
        "data class $name(\n${
            fieldDefinitions.joinToString(",\n") {
                SPACES + SPACES + it.emitOverwrite(this, document) + it.emit()
            }
        }\n)${emitInterfaces()}\n"
    } else {
        ""
    }

    override fun FieldDefinition.emit() = "val $name: ${type.emitType()}"

    override fun InterfaceTypeDefinition.emit() =
        "interface ${name}{\n${fieldDefinitions.joinToString("\n") { SPACES + SPACES + it.emit() }}\n}\n"

    override fun InputObjectTypeDefinition.emit() = if (inputValueDefinitions.size > 0) {
        "data class ${name}(\n${inputValueDefinitions.joinToString(",\n") { SPACES + SPACES + it.emit() }}\n)\n"
    } else ""

    override fun InputValueDefinition.emit() = "val $name: ${type.emitType()}"

    override fun ScalarTypeDefinition.emit(): String = when {
        scalars.contains(name) -> "typealias $name = ${scalars.getValue(name)}\n"
        else -> throw ScalarTypeDefinitionEmitterException(this, "Kotlin")
    }

    override fun EnumTypeDefinition.emit(): String =
        "enum class $name{\n$SPACES${enumValueDefinitions.joinToString(", ") { it.emit() }}\n}\n"

    override fun EnumValueDefinition.emit(): String = name

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List<${type.emitType()}>"
    override fun String.toNullable(): String = "${toNonNullable()}?"
    override fun String.toNonNullable(): String = when (this) {
        "ID" -> "String"
        else -> this
    }

    private fun ObjectTypeDefinition.emitInterfaces(): String =
        if (implements.isNotEmpty()) ":" + (implements as List<TypeName>).joinToString(",") { it.name } else ""

    private fun FieldDefinition.emitOverwrite(definition: ObjectTypeDefinition, document: Document): String {
        val annotation = when (type is NonNullType && enableOpenApiAnnotations) {
            true -> "@field:Schema(required = true)\n"
            false -> ""
        }

        return document.definitions
            .filterIsInstance(InterfaceTypeDefinition::class.java)
            .filter {
                definition.implements
                    .map { typeName -> (typeName as TypeName).name }
                    .contains(it.name)
            }
            .flatMap { it.fieldDefinitions }.any { it.name == this.name }
            .let { if (it) "${annotation}override " else annotation }
    }

    companion object {
        const val SPACES = "    "
    }
}
