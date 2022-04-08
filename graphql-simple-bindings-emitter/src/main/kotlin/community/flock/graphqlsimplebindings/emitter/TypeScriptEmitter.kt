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
import graphql.language.ObjectTypeDefinition
import graphql.language.ScalarTypeDefinition
import graphql.language.Type

class TypeScriptEmitter(private val scalars: Map<String, String> = mapOf()) : Emitter() {

    override fun ObjectTypeDefinition.emit(document: Document) =
        "export interface $name {\n${fieldDefinitions.joinToString(";\n") { it.emit() }};\n}\n"

    override fun FieldDefinition.emit() = "$SPACES$name${type.emitType()}"

    override fun InputObjectTypeDefinition.emit() =
        "export interface $name {\n${inputValueDefinitions.joinToString(";\n") { it.emit() }};\n}\n"

    override fun InputValueDefinition.emit() = "$SPACES$name${type.emitType()}"

    override fun InterfaceTypeDefinition.emit() = "// Not implemented: $name"

    override fun ScalarTypeDefinition.emit(): String? = when {
        scalars.contains(name) -> if (name == scalars.getValue(name)) null else "type $name = ${scalars.getValue(name)}\n"
        else -> throw ScalarTypeDefinitionEmitterException(this, "TypeScript")
    }

    override fun EnumTypeDefinition.emit(): String =
        "export enum $name {\n$SPACES${enumValueDefinitions.joinToString(", ") { it.emit() }}\n}\n"

    override fun EnumValueDefinition.emit(): String = name

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

    companion object {
        const val SPACES = "    "
    }

}
