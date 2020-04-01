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

    override fun ObjectTypeDefinition.emitObjectTypeDefinition() = "data class $name(\n${fieldDefinitions.emitDefinitionFields()}\n)\n"

    override fun List<FieldDefinition>.emitDefinitionFields() = joinToString(",\n") { it.emitDefinitionField() }
    override fun FieldDefinition.emitDefinitionField() = "\tval $name: ${type.emitType()}"

    override fun InputObjectTypeDefinition.emitInputObjectTypeDefinition(): String = throw InputObjectTypeDefinitionEmitterException(this)

    override fun ScalarTypeDefinition.emitScalarTypeDefinition(): String? = when (name) {
        "Date" -> "typealias Date = java.time.LocalDate\n"
        else -> throw ScalarTypeDefinitionEmitterException(this)
    }

    override fun EnumTypeDefinition.emitEnumTypeDefinition(): String = "enum $name{\n${enumValueDefinitions.emitEnumFields()}\n}\n"
    override fun List<EnumValueDefinition>.emitEnumFields() = joinToString(",\n") { it.emitEnumField() }
    override fun EnumValueDefinition.emitEnumField() = "\t$name"

    override fun nullableListOf(type: Type<Type<*>>): String = nonNullableListOf(type).toNullable()
    override fun nonNullableListOf(type: Type<Type<*>>): String = "List<${type.emitType()}>"
    override fun String.toNullable(): String = "${toNonNullable()}?"
    override fun String.toNonNullable(): String = this

}
