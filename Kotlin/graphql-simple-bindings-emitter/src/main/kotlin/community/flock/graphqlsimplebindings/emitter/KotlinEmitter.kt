package community.flock.graphqlsimplebindings.emitter

import community.flock.graphqlsimplebindings.exceptions.EnumTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.InputObjectTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.InterfaceTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.exceptions.ScalarTypeDefinitionEmitterException
import community.flock.graphqlsimplebindings.emitter.meta.Emitter
import graphql.language.*

class KotlinEmitter(private val packageName: String = "community.flock.graphqlsimplebindings.generated") : Emitter() {

    override fun emitDocument(document: Document): String = super.emitDocument(document)
            .let { "package $packageName\n\n$it" }

    override fun ObjectTypeDefinition.emitObjectTypeDefinition() = "data class $name(\n${fieldDefinitions.emitFields()}\n)\n"

    override fun List<FieldDefinition>.emitFields() = joinToString(",\n") { it.emitField() }
    override fun FieldDefinition.emitField() = "        val $name: ${type.emitType()}"

    override fun InputObjectTypeDefinition.emitInputObjectTypeDefinition(): String = throw InputObjectTypeDefinitionEmitterException(this)

    override fun ScalarTypeDefinition.emitScalarTypeDefinition(): String? = when (name) {
        "Date" -> "typealias Date = java.time.LocalDate\n"
        else -> throw ScalarTypeDefinitionEmitterException(this)
    }

    override fun EnumTypeDefinition.emitEnumTypeDefinition(): String = throw EnumTypeDefinitionEmitterException(this)
    override fun InterfaceTypeDefinition.emitInterfaceTypeDefinition(): String = throw InterfaceTypeDefinitionEmitterException(this)

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List<${type.emitType()}>"
    override fun String.toNullable(): String = "${toNonNullable()}?"
    override fun String.toNonNullable(): String = this

}
