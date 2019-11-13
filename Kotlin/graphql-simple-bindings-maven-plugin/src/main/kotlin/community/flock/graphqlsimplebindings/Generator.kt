package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.meta.Emitter
import graphql.language.Document
import java.io.File

typealias FullPath = String
typealias EmittedDocument = String

abstract class Generator {

    abstract fun generate(documents: List<Pair<String, Document>>)

    protected fun String.alsoMakeDir() = also { File(it).mkdirs() }

    protected infix fun List<Pair<FileName, Document>>.emittedWith(emitter: Emitter) = map {
        Pair(it.first, emitter.emitDocument(it.second))
    }

    protected infix fun List<Pair<FileName, EmittedDocument>>.extendedWith(block: EmittedDocument.() -> EmittedDocument) =
            map { Pair(it.first, it.second.block()) }


    protected infix fun List<Pair<FileName, EmittedDocument>>.andWrittenTo(pathTemplate: (FileName) -> FullPath) = forEach {
        File(pathTemplate(it.first)).writeText(it.second)
    }

}