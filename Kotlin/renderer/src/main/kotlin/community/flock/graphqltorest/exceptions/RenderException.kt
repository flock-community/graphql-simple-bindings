package community.flock.graphqltorest.exceptions

import graphql.language.*

open class RenderException(msg: String) : RuntimeException(msg)

class TypeRenderException(type: Type<Type<*>>) : RenderException("This should not happen. New type? -> $type")

class DefinitionRenderException(definition: Definition<Definition<*>>) : RenderException("This should not happen. New definition? -> $definition")

class ScalarTypeDefinitionRenderException(scalar: ScalarTypeDefinition) : RenderException("This should not happen. New scalar? -> ${scalar.name}")

class InputObjectTypeDefinitionRenderException(inputObject: InputObjectTypeDefinition) : RenderException("This should not happen. New inputObject? -> $inputObject")

class EnumTypeDefinitionRenderException(enum: EnumTypeDefinition) : RenderException("This should not happen. New enum? -> $enum")

class InterfaceTypeDefinitionRenderException(interfaceType: InterfaceTypeDefinition) : RenderException("This should not happen. New interface? -> $interfaceType")
