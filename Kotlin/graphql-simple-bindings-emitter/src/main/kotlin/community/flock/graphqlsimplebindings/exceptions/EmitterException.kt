package community.flock.graphqlsimplebindings.exceptions

import graphql.language.*

open class EmitterException(msg: String) : RuntimeException("This should not happen. $msg")

class TypeEmitterException(type: Type<Type<*>>) : EmitterException("New type? -> $type")

class DefinitionEmitterException(definition: Definition<Definition<*>>) : EmitterException("New definition? -> $definition")

class ScalarTypeDefinitionEmitterException(scalar: ScalarTypeDefinition, language: String) : EmitterException("New scalar for $language? -> ${scalar.name}")
