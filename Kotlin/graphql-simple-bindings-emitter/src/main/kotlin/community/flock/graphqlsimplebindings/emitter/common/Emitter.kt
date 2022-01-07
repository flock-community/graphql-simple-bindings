package community.flock.graphqlsimplebindings.emitter.common

import community.flock.graphqlsimplebindings.exceptions.DefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.TypeEmitterException
import graphql.language.Definition
import graphql.language.Document
import graphql.language.EnumTypeDefinition
import graphql.language.InputObjectTypeDefinition
import graphql.language.InterfaceTypeDefinition
import graphql.language.ListType
import graphql.language.NonNullType
import graphql.language.ObjectTypeDefinition
import graphql.language.ScalarTypeDefinition
import graphql.language.Type
import graphql.language.TypeName
import graphql.schema.idl.TypeInfo

abstract class Emitter : DefinitionEmitter, EnumEmitter, InputEmitter, InterfaceEmitter, FieldDefinitionEmitter,
    TypeEmitter {

    open fun emitDocument(document: Document): String = document.definitions
        .mapNotNull { it.emitDefinition(document) }
        .joinToString("\n")

    private fun Definition<Definition<*>>.emitDefinition(document: Document) = when (this) {
        is ObjectTypeDefinition -> emit(document)
        is ScalarTypeDefinition -> emit()
        is InputObjectTypeDefinition -> emit()
        is EnumTypeDefinition -> emit()
        is InterfaceTypeDefinition -> emit()
        else -> throw DefinitionEmitterException(this)
    }

    protected val Type<Type<*>>.value: String get() = TypeInfo.typeInfo(this).name

    protected fun Type<Type<*>>.emitType(): String = when (this) {
        is NonNullType -> type.toNonNullableType()
        is ListType -> nullableListOf(type)
        is TypeName -> value.toNullable()
        else -> throw TypeEmitterException(this)
    }

    private fun Type<Type<*>>.toNonNullableType(): String = when (this) {
        is ListType -> nonNullableListOf(type)
        is TypeName -> value.toNonNullable()
        else -> throw TypeEmitterException(this)
    }

}
