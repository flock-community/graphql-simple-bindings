package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.parser.Parser
import community.flock.graphqlsimplebindings.emitter.KotlinEmitter
import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import community.flock.graphqlsimplebindings.emitter.common.Emitter
import graphql.language.Document
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest
class GraphqlSimpleBindingsApplicationTests {

    @Value("\${exampleDirectory:#{null}}")
    var examples: String? = null

    val scalars: Map<String, String> = mapOf("Date" to "java.time.LocalDate")

    private val input = GraphqlSimpleBindingsApplicationTests::class.java.getResource("/input.graphql")
            .readText()
            .let { Parser.parseSchema(it) }

    @Test
    fun `Kotlin Emitter`() = input emittedWith KotlinEmitter(scalars = scalars) writtenTo "App.kt".file

    @Test
    fun `TypeScript Emitter`() = input emittedWith TypeScriptEmitter(scalars = scalars) writtenTo "appFromKt.ts".file

    private infix fun Document.emittedWith(emitter: Emitter) = emitter.emitDocument(this)

    private infix fun String.writtenTo(file: File?) = file?.writeText(this) ?: println(this)

    private val String.file
        get() = examples?.let { if (File("$it/dist").exists()) File("$it/dist/$this") else null }

}
