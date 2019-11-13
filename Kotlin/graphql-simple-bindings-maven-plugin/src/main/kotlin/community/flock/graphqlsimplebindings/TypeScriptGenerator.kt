package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import graphql.language.Document

class TypeScriptGenerator(targetDirectory: String) : Generator() {

    private val typeScriptDir: String = "$targetDirectory/TypeScript".alsoMakeDir()

    override fun generate(documents: List<Pair<String, Document>>): Unit = documents emittedWith TypeScriptEmitter extendedWith { disclaimer() } andWrittenTo ::typeScriptPathTemplate

    private fun EmittedDocument.disclaimer() = "// This is generated code\n// DO NOT MODIFY\n// It will be overwritten\n\n$this"

    private fun typeScriptPathTemplate(fileName: String) = "$typeScriptDir/${fileName.toLowerCase()}.d.ts"

}
