package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.common.Emitter
import graphql.language.Document
import java.io.File

typealias LanguageDirectory = String
typealias FullPath = String
typealias EmittedDocument = String

abstract class Generator(
    languageDirectory: LanguageDirectory,
    pathTemplate: (LanguageDirectory) -> (FileName) -> FullPath,
    private val emitter: Emitter,
    private val disclaimer: String
) {

    private val pathTemplate: (FileName) -> FullPath = pathTemplate(languageDirectory.alsoMakeDir())

    fun generate(documents: List<Pair<String, Document>>) =
        documents emittedWith emitter extendedWith { "$disclaimer$this" } andWrittenTo pathTemplate

    companion object {
        private fun String.alsoMakeDir() = also { File(it).mkdirs() }

        private infix fun List<Pair<FileName, Document>>.emittedWith(emitter: Emitter) = emitDoc(emitter::emitDocument)

        private infix fun List<Pair<FileName, EmittedDocument>>.extendedWith(block: EmittedDocument.() -> EmittedDocument) =
            map { (fileName, emittedDocument) -> fileName to emittedDocument.block() }

        private infix fun List<Pair<FileName, EmittedDocument>>.andWrittenTo(pathTemplate: (FileName) -> FullPath) =
            forEach { (fileName, emittedDocument) -> File(pathTemplate(fileName)).writeText(emittedDocument) }

        private fun List<Pair<FileName, Document>>.emitDoc(emit: (Document) -> String) =
            map { (fileName, document) -> fileName to emit(document) }
    }

}
