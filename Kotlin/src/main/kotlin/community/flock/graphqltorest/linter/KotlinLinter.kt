package community.flock.graphqltorest.linter

import com.pinterest.ktlint.core.KtLint
import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider

object KotlinLinter {

    fun lint(text: String): String = KtLint.format(params(text))

    private fun params(text: String) = KtLint.Params(
            text = text,
            ruleSets = setOf(StandardRuleSetProvider().get()),
            cb = ::print,
            debug = true
    )

    private fun print(e: LintError, ignore: Boolean) = println(e)

}
