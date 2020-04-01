package community.flock.graphqlsimplebindings.emitter

import community.flock.graphqlsimplebindings.exceptions.ScalarTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.emitter.meta.Emitter
import graphql.language.*

const val SPACES = "        "

class KotlinEmitter(private val packageName: String = "community.flock.graphqlsimplebindings.generated") : Emitter() {

    override fun emitDocument(document: Document): String = super.emitDocument(document)
            .let { "package $packageName\n\n$it" }

    override fun ObjectTypeDefinition.emitObjectTypeDefinition() = "data class $name(\n${fieldDefinitions.emitDefinitionFields()}\n)\n"

    override fun List<FieldDefinition>.emitDefinitionFields() = joinToString(",\n") { it.emitDefinitionField() }
    override fun FieldDefinition.emitDefinitionField() = "${SPACES}val $name: ${type.emitType()}"

    override fun InputObjectTypeDefinition.emitInputObjectTypeDefinition() = "data class ${name}(\n${inputValueDefinitions.emitInputFields()}\n)\n"
    override fun List<InputValueDefinition>.emitInputFields() = joinToString(",\n") { it.emitInputField() }
    override fun InputValueDefinition.emitInputField() = "${SPACES}val $name: ${type.emitType()}"

    override fun InterfaceTypeDefinition.emitInterfaceTypeDefinition() = throw NotImplementedError()

    override fun ScalarTypeDefinition.emitScalarTypeDefinition(): String? = when (name) {
        "Date" -> "typealias Date = java.time.LocalDate\n"
        else -> throw ScalarTypeDefinitionEmitterException(this)
    }

    override fun EnumTypeDefinition.emitEnumTypeDefinition(): String = "enum class $name{\n${enumValueDefinitions.emitEnumFields()}\n}\n"
    override fun List<EnumValueDefinition>.emitEnumFields() = joinToString(",\n") { it.emitEnumField() }
    override fun EnumValueDefinition.emitEnumField() = "${SPACES}$name"

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List<${type.emitType()}>"
    override fun String.toNullable(): String = "${toNonNullable()}?"
    override fun String.toNonNullable(): String = this

}
