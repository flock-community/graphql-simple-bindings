package community.flock.graphqltorest.exceptions

open class RenderException(msg: String) : RuntimeException(msg)

class FieldTypeRenderException(msg: String) : RenderException(msg)
