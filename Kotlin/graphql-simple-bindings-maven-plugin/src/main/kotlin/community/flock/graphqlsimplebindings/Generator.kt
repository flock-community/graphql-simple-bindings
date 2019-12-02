package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.meta.Emitter
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

    fun generate(documents: List<Pair<String, Document>>) = documents emittedWith emitter extendedWith { "$disclaimer$this" } andWrittenTo pathTemplate

    companion object {
        private fun String.alsoMakeDir() = also { File(it).mkdirs() }

        private infix fun List<Pair<FileName, Document>>.emittedWith(emitter: Emitter) = mapDoc(emitter::emitDocument)

        private infix fun List<Pair<FileName, EmittedDocument>>.extendedWith(block: EmittedDocument.() -> EmittedDocument) = map { Pair(it.first, it.second.block()) }

        private infix fun List<Pair<FileName, EmittedDocument>>.andWrittenTo(pathTemplate: (FileName) -> FullPath) = forEach { File(pathTemplate(it.first)).writeText(it.second) }

        private fun List<Pair<FileName, Document>>.mapDoc(mapper: (Document) -> String) = map { it.first to mapper(it.second) }
    }

}
