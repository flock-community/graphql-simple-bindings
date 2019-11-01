package community.flock.graphqltorest

import community.flock.graphqltorest.renderer.KotlinRenderer
import graphql.language.Document

class KotlinGenerator(targetDirectory: String, private val packageName: String) : Generator() {

    private val kotlinDir: String = "$targetDirectory/Kotlin".alsoMakeDir()

    override fun generate(documents: List<Pair<FileName, Document>>): Unit = documents renderedWith KotlinRenderer(packageName) extendedWith { disclaimer() } andWrittenTo ::kotlinPathTemplate

    private fun RenderedDocument.disclaimer() = "/**\n* This is generated code\n* DO NOT MODIFY\n* It will be overwritten\n*/\n\n$this"

    private fun kotlinPathTemplate(fileName: FileName) = "$kotlinDir/${fileName.capitalize()}.kt"

}
