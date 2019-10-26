package community.flock.graphqltorest

import community.flock.graphqltorest.renderer.TypeScriptRenderer
import graphql.language.Document

class TypeScriptGenerator(targetDirectory: String) : Generator() {

    private val typeScriptDir: String = "$targetDirectory/TypeScript".alsoMakeDir()

    override fun generate(documents: List<Pair<String, Document>>): Unit = documents renderedWith TypeScriptRenderer extendedWith { disclaimer() } andWrittenTo ::typeScriptPathTemplate

    private fun RenderedDocument.disclaimer() = "// This is generated code\n// DO NOT MODIFY\n// It will be overwritten\n\n$this"

    private fun typeScriptPathTemplate(fileName: String) = "$typeScriptDir/$fileName.d.ts"

}
