package community.flock.graphqlsimplebindings.exceptions

import graphql.language.*

open class RenderException(msg: String) : RuntimeException("This should not happen. $msg")

class TypeRenderException(type: Type<Type<*>>) : RenderException("New type? -> $type")

class DefinitionRenderException(definition: Definition<Definition<*>>) : RenderException("New definition? -> $definition")

class ScalarTypeDefinitionRenderException(scalar: ScalarTypeDefinition) : RenderException("New scalar? -> ${scalar.name}")

class InputObjectTypeDefinitionRenderException(inputObject: InputObjectTypeDefinition) : RenderException("New inputObject? -> $inputObject")

class EnumTypeDefinitionRenderException(enum: EnumTypeDefinition) : RenderException("New enum? -> $enum")

class InterfaceTypeDefinitionRenderException(interfaceType: InterfaceTypeDefinition) : RenderException("New interface? -> $interfaceType")
