package community.flock.graphqltorest

import community.flock.graphqltorest.parser.Parser
import community.flock.graphqltorest.renderer.KotlinRenderer
import community.flock.graphqltorest.renderer.TypeScriptRenderer
import community.flock.graphqltorest.renderer.meta.Renderer
import graphql.language.Document
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest
class GraphqlToRestApplicationTests {

    @Value("\${exampleDirectory:#{null}}")
    var examples: String? = null

    private val input = GraphqlToRestApplicationTests::class.java.getResource("/input.graphql")
            .readText()
            .let { Parser.parseSchema(it) }

    @Test
    fun `Kotlin Renderer`() = input renderedWith KotlinRenderer writtenTo "App.kt".file

    @Test
    fun `TypeScript Renderer`() = input renderedWith TypeScriptRenderer writtenTo "appFromKt.ts".file

    private infix fun Document.renderedWith(renderer: Renderer) = renderer.renderDocument(this)

    private infix fun String.writtenTo(file: File?) = file?.writeText(this) ?: println(this)

    private val String.file
        get() = examples?.let { File("$it/dist/$this") }

}
