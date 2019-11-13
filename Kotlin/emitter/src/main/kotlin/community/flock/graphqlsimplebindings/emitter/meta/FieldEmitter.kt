package community.flock.graphqlsimplebindings.emitter.meta

import graphql.language.FieldDefinition

internal interface FieldEmitter {

    fun List<FieldDefinition>.emitFields(): String

    fun FieldDefinition.emitField(): String

}
