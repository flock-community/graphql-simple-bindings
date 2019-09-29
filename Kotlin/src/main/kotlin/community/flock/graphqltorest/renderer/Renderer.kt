package community.flock.graphqltorest.renderer

import com.pinterest.ktlint.core.KtLint
import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import graphql.language.*

abstract class Renderer {

    open fun renderDocument(document: Document): String = document.definitions
            .mapNotNull { renderDefinition(it) }
            .joinToString("\n")
            .let {
                KtLint.Params(
                        text = "package community.flock.graphqltorest\n\nimport java.time.LocalDate\n\n$it",
                        ruleSets = setOf(StandardRuleSetProvider().get()),
                        cb = ::print,
                        debug = true)
            }.let {
                KtLint.lint(it)
                KtLint.format(it)
            }.also { println(it) }

    private fun print(e: LintError, ignore: Boolean) {
        println(e)
    }

    private fun renderDefinition(definition: Definition<Definition<*>>) = when (definition) {
        is ObjectTypeDefinition -> renderObjectTypeDefinition(definition)
        is InputObjectTypeDefinition -> renderInputObjectTypeDefinition(definition)
        is EnumTypeDefinition -> renderEnumTypeDefinition(definition)
        is InterfaceTypeDefinition -> renderInterfaceTypeDefinition(definition)
        else -> null
    }

    protected abstract fun renderObjectTypeDefinition(definition: ObjectTypeDefinition): String
    protected abstract fun renderInputObjectTypeDefinition(definition: InputObjectTypeDefinition): String
    protected abstract fun renderEnumTypeDefinition(definition: EnumTypeDefinition): String
    protected abstract fun renderInterfaceTypeDefinition(definition: InterfaceTypeDefinition): String
    protected abstract fun renderScalarTypeDefinition(definition: ScalarTypeDefinition): String
}
