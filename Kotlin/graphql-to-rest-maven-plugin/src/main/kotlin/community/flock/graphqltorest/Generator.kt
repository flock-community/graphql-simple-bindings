package community.flock.graphqltorest

import community.flock.graphqltorest.renderer.meta.Renderer
import graphql.language.Document
import java.io.File

typealias FullPath = String
typealias RenderedDocument = String

abstract class Generator {

    abstract fun generate(documents: List<Pair<String, Document>>)

    protected fun String.alsoMakeDir() = also { File(it).mkdirs() }

    protected infix fun List<Pair<FileName, Document>>.renderedWith(renderer: Renderer) = map {
        Pair(it.first, renderer.renderDocument(it.second))
    }

    protected infix fun List<Pair<FileName, RenderedDocument>>.extendedWith(block: RenderedDocument.() -> RenderedDocument) =
            map { Pair(it.first, it.second.block()) }


    protected infix fun List<Pair<FileName, RenderedDocument>>.andWrittenTo(pathTemplate: (FileName) -> FullPath) = forEach {
        File(pathTemplate(it.first)).writeText(it.second)
    }

}