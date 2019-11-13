package community.flock.graphqlsimplebindings.emitter

import community.flock.graphqlsimplebindings.exceptions.EnumTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.InputObjectTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.InterfaceTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.ScalarTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.emitter.meta.Emitter
import graphql.language.*

object TypeScriptEmitter : Emitter() {

    override fun ObjectTypeDefinition.emitObjectTypeDefinition() = "export interface $name {\n${fieldDefinitions.emitFields()}\n}\n"

    override fun List<FieldDefinition>.emitFields() = joinToString(";\n") { it.emitField() } + ";"
    override fun FieldDefinition.emitField() = "  $name${type.emitType()}"

    override fun InputObjectTypeDefinition.emitInputObjectTypeDefinition(): String = throw InputObjectTypeDefinitionEmitterException(this)
    override fun ScalarTypeDefinition.emitScalarTypeDefinition(): String? = when (name) {
        "Date" -> null
        else -> throw ScalarTypeDefinitionEmitterException(this)
    }

    override fun EnumTypeDefinition.emitEnumTypeDefinition(): String = throw EnumTypeDefinitionEmitterException(this)
    override fun InterfaceTypeDefinition.emitInterfaceTypeDefinition(): String = throw InterfaceTypeDefinitionEmitterException(this)

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
