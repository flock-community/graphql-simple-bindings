package community.flock.graphqlsimplebindings.emitter

import community.flock.graphqlsimplebindings.emitter.meta.Emitter
import community.flock.graphqlsimplebindings.exceptions.ScalarTypeDefinitionEmitterException
import graphql.language.*

object TypeScriptEmitter : Emitter() {

    override fun ObjectTypeDefinition.emitObjectTypeDefinition() = "export interface $name {\n${emitFields()};\n}\n"

    override fun ObjectTypeDefinition.emitFields() = fieldDefinitions.joinToString(";\n") { it.emitDefinitionField() }
    override fun FieldDefinition.emitDefinitionField() = "  $name${type.emitType()}"

    override fun InputObjectTypeDefinition.emitInputObjectTypeDefinition() = "export interface $name {\n${inputValueDefinitions.emitInputFields()};\n}\n"
    override fun List<InputValueDefinition>.emitInputFields() = joinToString(";\n") { it.emitInputField() }
    override fun InputValueDefinition.emitInputField() = "  $name${type.emitType()}"

    override fun InterfaceTypeDefinition.emitInterfaceTypeDefinition() = throw NotImplementedError()
    override fun InterfaceTypeDefinition.emitFields() = throw NotImplementedError()
    override fun FieldDefinition.emitField() = throw NotImplementedError()

    override fun ScalarTypeDefinition.emitScalarTypeDefinition(): String? = when (name) {
        "Date" -> null
        "DateTime" -> null
        else -> throw ScalarTypeDefinitionEmitterException(this)
    }

    override fun EnumTypeDefinition.emitEnumTypeDefinition(): String = "export enum $name {\n${enumValueDefinitions.emitEnumFields()}\n}\n"
    override fun List<EnumValueDefinition>.emitEnumFields() = joinToString(",\n") { it.emitEnumField() }
    override fun EnumValueDefinition.emitEnumField() = "\t$name"

    override fun nullableListOf(type: Type<Type<*>>): String = "?${nonNullableListOf(type)}"
    override fun nonNullableListOf(type: Type<Type<*>>): String = ": ${type.value.emit()}[]"
    override fun String.toNullable(): String = "?${toNonNullable()}"
    override fun String.toNonNullable(): String = ": ${emit()}"

    private fun String.emit() = when (this) {
        "String" -> "string"
        "Int" -> "number"
        "Float" -> "number"
        "Boolean" -> "boolean"
        "ID" -> "string"
        else -> this
    }

}
