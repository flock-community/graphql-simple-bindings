package community.flock.graphqlsimplebindings.emitter

import community.flock.graphqlsimplebindings.emitter.meta.Emitter
import community.flock.graphqlsimplebindings.exceptions.ScalarTypeDefinitionEmitterException
import graphql.language.*

const val SPACES = "    "

class KotlinEmitter(
        private val packageName: String = "community.flock.graphqlsimplebindings.generated",
        private val scalars: Map<String, String> = mapOf()) : Emitter() {


    override fun emitDocument(document: Document): String = super.emitDocument(document)
            .let { "package $packageName\n\n$it" }

    override fun ObjectTypeDefinition.emitObjectTypeDefinition(document: Document) = if (fieldDefinitions.size > 0) {
        "data class $name(\n${emitFields(document)}\n)${emitInterfaces()}\n"
    } else {
        ""
    }

    private fun ObjectTypeDefinition.emitInterfaces(): String? = if (implements.isNotEmpty()) {
        ":" + (implements as List<TypeName>).joinToString(",") { it.name }
    } else {
        ""
    }

    override fun ObjectTypeDefinition.emitFields(document: Document) = fieldDefinitions.joinToString(",\n") { SPACES + SPACES + it.emitOverwrite(this, document) + it.emitDefinitionField() }
    private fun FieldDefinition.emitOverwrite(definition: ObjectTypeDefinition, document: Document) = document.definitions
            .filterIsInstance(InterfaceTypeDefinition::class.java)
            .filter {
                definition.implements
                        .map { (it as TypeName).name }
                        .contains(it.name)
            }
            .flatMap { it.fieldDefinitions }
            .filter { it.name == this.name }
            .any()
            .let { if (it) "override " else "" }

    override fun FieldDefinition.emitDefinitionField() = "val $name: ${type.emitType()}"

    override fun InterfaceTypeDefinition.emitInterfaceTypeDefinition() = "interface ${name}{\n${emitFields()}\n}\n"
    override fun InterfaceTypeDefinition.emitFields() = fieldDefinitions.joinToString("\n") { SPACES + SPACES + it.emitField() }
    override fun FieldDefinition.emitField() = "val $name: ${type.emitType()}"

    override fun InputObjectTypeDefinition.emitInputObjectTypeDefinition() = if (inputValueDefinitions.size > 0) {
        "data class ${name}(\n${inputValueDefinitions.emitInputFields()}\n)\n"
    } else {
        ""
    }

    override fun List<InputValueDefinition>.emitInputFields() = joinToString(",\n") { SPACES + SPACES + it.emitInputField() }
    override fun InputValueDefinition.emitInputField() = "val $name: ${type.emitType()}"

    override fun ScalarTypeDefinition.emitScalarTypeDefinition(): String? = when {
        "Date" == name -> "typealias Date = java.time.LocalDate\n"
        "DateTime" == name -> "typealias DateTime = java.time.LocalDateTime\n"
        scalars.contains(this.name) -> "typealias $name = ${scalars.getValue(name)}\n"
        else -> throw ScalarTypeDefinitionEmitterException(this)
    }

    override fun EnumTypeDefinition.emitEnumTypeDefinition(): String = "enum class $name{\n${enumValueDefinitions.emitEnumFields()}\n}\n"
    override fun List<EnumValueDefinition>.emitEnumFields() = joinToString(",\n") { SPACES + it.emitEnumField() }
    override fun EnumValueDefinition.emitEnumField() = name

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List<${type.emitType()}>"
    override fun String.toNullable(): String = "${toNonNullable()}?"
    override fun String.toNonNullable(): String = when (this) {
        "ID" -> "String"
        else -> this
    }

}
