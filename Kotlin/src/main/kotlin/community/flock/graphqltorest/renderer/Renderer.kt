package community.flock.graphqltorest.renderer

import com.pinterest.ktlint.core.KtLint
import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import community.flock.graphqltorest.exceptions.DefinitionRenderException
import graphql.language.*

abstract class Renderer {

    open fun renderDocument(document: Document): String = document.definitions
            .mapNotNull { renderDefinition(it) }
            .joinToString("\n")
            .let { "package community.flock.graphqltorest\n\nimport java.time.LocalDate\n\n$it" }
            .let { KtLint.format(params(it)) }
            .also { println(it) }

    private fun params(text: String) = KtLint.Params(
            text = text,
            ruleSets = setOf(StandardRuleSetProvider().get()),
            cb = ::print,
            debug = true
    )

    private fun print(e: LintError, ignore: Boolean) = println(e)

    private fun renderDefinition(definition: Definition<Definition<*>>) = when (definition) {
        is ObjectTypeDefinition -> renderObjectTypeDefinition(definition)
        is ScalarTypeDefinition -> renderScalarTypeDefinition(definition)
        is InputObjectTypeDefinition -> renderInputObjectTypeDefinition(definition)
        is EnumTypeDefinition -> renderEnumTypeDefinition(definition)
        is InterfaceTypeDefinition -> renderInterfaceTypeDefinition(definition)
        else -> throw DefinitionRenderException(definition)
    }

    protected abstract fun renderObjectTypeDefinition(definition: ObjectTypeDefinition): String
    protected abstract fun renderScalarTypeDefinition(definition: ScalarTypeDefinition): String?
    protected abstract fun renderInputObjectTypeDefinition(definition: InputObjectTypeDefinition): String
    protected abstract fun renderEnumTypeDefinition(definition: EnumTypeDefinition): String
    protected abstract fun renderInterfaceTypeDefinition(definition: InterfaceTypeDefinition): String
}
