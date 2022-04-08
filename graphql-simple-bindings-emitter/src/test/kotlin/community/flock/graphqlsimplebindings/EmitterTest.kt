package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.Language.Java
import community.flock.graphqlsimplebindings.Language.Kotlin
import community.flock.graphqlsimplebindings.Language.Scala
import community.flock.graphqlsimplebindings.Language.TypeScript
import community.flock.graphqlsimplebindings.emitter.JavaEmitter
import community.flock.graphqlsimplebindings.emitter.KotlinEmitter
import community.flock.graphqlsimplebindings.emitter.ScalaEmitter
import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import community.flock.graphqlsimplebindings.emitter.common.Emitter
import community.flock.graphqlsimplebindings.parser.Parser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileReader
import java.io.Reader

abstract class EmitterTest(private val language: Language) {

    @Test
    fun testTypes() = run("type")

    @Test
    fun testEnumTypes() = run("enum")

    @Test
    fun testInputTypes() = run("input")

    @Test
    fun testInterfaceTypes() = run("interface")

    private fun run(name: String) {
        val schemaFileUrl = javaClass.classLoader.getResource("$name.graphql") ?: throw RuntimeException()
        val snapshotFileUrl = javaClass.classLoader.getResource("$name.graphql.${language.extension}.snapshot")
            ?: throw RuntimeException()

        val snapshotFile = File(snapshotFileUrl.file)

        val targetReader: Reader = FileReader(schemaFileUrl.file)
        val emitter: Emitter = when (language) {
            Kotlin -> KotlinEmitter()
            Scala -> ScalaEmitter()
            Java -> JavaEmitter()
            TypeScript -> TypeScriptEmitter()
        }
        val schema = Parser.parseSchema(targetReader)

        val res = emitter.emitDocument("", schema).first().second

        // Uncomment the line below to write snapshots
        // snapshotFile.writeText(res)

        Assertions.assertEquals(snapshotFile.readText(), res)
    }
}

enum class Language(val extension: String) {
    Kotlin("kt"), Scala("scala"), Java("java"), TypeScript("ts")
}
