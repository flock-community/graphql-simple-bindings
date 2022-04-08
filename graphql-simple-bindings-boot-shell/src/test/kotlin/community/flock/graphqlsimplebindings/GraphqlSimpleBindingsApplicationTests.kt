package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.JavaEmitter
import community.flock.graphqlsimplebindings.emitter.KotlinEmitter
import community.flock.graphqlsimplebindings.emitter.ScalaEmitter
import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import community.flock.graphqlsimplebindings.emitter.common.Emitter
import community.flock.graphqlsimplebindings.parser.Parser
import graphql.language.Document
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File

@ExtendWith(SpringExtension::class)
@SpringBootTest
class GraphqlSimpleBindingsApplicationTests {

    @Value("\${exampleDirectory:#{null}}")
    var examples: String? = null

    private val scalarsKotlin = mapOf("Date" to "java.time.LocalDate")

    private val scalarsScala = mapOf("Date" to "java.time.LocalDate")

    private val scalarsJava = mapOf("Date" to "java.time.LocalDate")

    private val scalarsTypeScript = mapOf("Date" to "Date")

    private val input = readInput().readText().let { Parser.parseSchema(it) }

    @Test
    fun `Kotlin Emitter`() = input emittedWith KotlinEmitter(packageName = "kotlin", scalars = scalarsKotlin, enableOpenApiAnnotations = false) writtenTo "App.kt".file

    @Test
    fun `Scala Emitter`() = input emittedWith ScalaEmitter(packageName = "scala", scalars = scalarsScala, enableOpenApiAnnotations = false) writtenTo "App.scala".file

    @Test
    fun `Java Emitter`() = input emittedWith JavaEmitter(packageName = "java", scalars = scalarsJava, enableOpenApiAnnotations = false) writtenTo "App.java".file

    @Test
    fun `TypeScript Emitter`() = input emittedWith TypeScriptEmitter(scalars = scalarsTypeScript) writtenTo "appFromKt.ts".file

    private fun readInput() = GraphqlSimpleBindingsApplicationTests::class.java.getResource("/input.graphql")
        ?: throw RuntimeException("No /input.graphql found")

    private infix fun Document.emittedWith(emitter: Emitter) = emitter
        .emitDocument("App", this, true)
        .joinToString("\n") { (_, string) -> string }

    private infix fun String.writtenTo(file: File?) = file?.writeText(this) ?: println(this)

    private val String.file
        get() = examples?.let { if (File("$it/dist").exists()) File("$it/dist/$this") else null }

}
