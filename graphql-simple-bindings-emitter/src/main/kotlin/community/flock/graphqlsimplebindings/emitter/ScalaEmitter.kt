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

class ScalaEmitter(
    private val packageName: String = "scala",
    private val scalars: Map<String, String> = mapOf(),
    private val enableOpenApiAnnotations: Boolean = false
) : Emitter() {

    private val swaggerImport: String = when (enableOpenApiAnnotations) {
        true -> "import io.swagger.v3.oas.annotations.media.Schema\n\n"
        false -> ""
    }

    override fun emitDocument(fileName: String, document: Document, multipleFiles: Boolean) =
        super.emitDocument(fileName, document, multipleFiles)
        .map { (fileName, doc) -> fileName to "package $packageName\n\n$swaggerImport$doc" }

    override fun ObjectTypeDefinition.emit(document: Document) =
        if (fieldDefinitions.size > 0)
            "case class $name(\n${
                fieldDefinitions.joinToString(",\n") {
                    SPACES + SPACES + it.emitOverwrite(this, document) + it.emit()
                }
            }\n)${emitInterfaces()}\n"
        else ""

    override fun FieldDefinition.emit() = "val ${name.escapeTypeKeyword()}: ${type.emitType()}"

    override fun InterfaceTypeDefinition.emit() =
        "trait ${name}{\n${fieldDefinitions.joinToString("\n") { SPACES + SPACES + it.emit() }}\n}\n"

    override fun InputObjectTypeDefinition.emit() =
        if (inputValueDefinitions.size > 0) "case class ${name}(\n${inputValueDefinitions.joinToString(",\n") { SPACES + SPACES + it.emit() }}\n)\n"
        else ""

    override fun InputValueDefinition.emit() = "val $name: ${type.emitType()}"

    override fun ScalarTypeDefinition.emit(): String = when {
        scalars.contains(name) -> "object ${name}Type {\n $SPACES type $name = ${scalars.getValue(name)}\n}\n"
        else -> throw ScalarTypeDefinitionEmitterException(this, "Scala")
    }

    override fun EnumTypeDefinition.emit(): String =
        "object ${name}s extends Enumeration {\n${SPACES}type $name = Value\n\n${SPACES}val ${enumValueDefinitions.joinToString(", ") { it.emit() }} = Value\n}\n"

    override fun EnumValueDefinition.emit(): String = name

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List[${type.emitType()}]"
    override fun String.toNullable(): String = "Option[${toNonNullable()}]"
    override fun String.toNonNullable(): String = when (this) {
        "ID" -> "String"
        else -> this
    }

    private fun ObjectTypeDefinition.emitInterfaces(): String =
        if (implements.isNotEmpty()) " extends " + (implements as List<TypeName>).joinToString(" with ") { it.name } else ""

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
            .flatMap { it.fieldDefinitions }
            .any { it.name == this.name }
            .let { if (it) "${annotation}override " else annotation }
    }

    companion object {
        const val SPACES = "    "

        fun String.escapeTypeKeyword() = when(this) {
            "type" -> "`type`"
            else -> this
        }
    }
}
