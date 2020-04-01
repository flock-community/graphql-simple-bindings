package community.flock.graphqlsimplebindings.exceptions

import graphql.language.*

open class EmitterException(msg: String) : RuntimeException("This should not happen. $msg")

class TypeEmitterException(type: Type<Type<*>>) : EmitterException("New type? -> $type")

class DefinitionEmitterException(definition: Definition<Definition<*>>) : EmitterException("New definition? -> $definition")

class ScalarTypeDefinitionEmitterException(scalar: ScalarTypeDefinition) : EmitterException("New scalar? -> ${scalar.name}")

class InputObjectTypeDefinitionEmitterException(inputObject: InputObjectTypeDefinition) : EmitterException("New inputObject? -> $inputObject")

class EnumTypeDefinitionEmitterException(enum: EnumTypeDefinition) : EmitterException("New enum? -> $enum")
class EnumValueDefinitionEmitterException(enum: EnumValueDefinition) : EmitterException("New enum? -> $enum")

class InterfaceTypeDefinitionEmitterException(interfaceType: InterfaceTypeDefinition) : EmitterException("New interface? -> $interfaceType")
