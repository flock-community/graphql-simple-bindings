package community.flock.graphqltorest.exceptions

import graphql.language.Definition
import graphql.language.Type

open class RenderException(msg: String) : RuntimeException(msg)

class FieldTypeRenderException(type: Type<Type<*>>) : RenderException("This should not happen. New type? -> $type")

class DefinitionRenderException(def: Definition<Definition<*>>) : RenderException("This should not happen. New type? -> $def")
