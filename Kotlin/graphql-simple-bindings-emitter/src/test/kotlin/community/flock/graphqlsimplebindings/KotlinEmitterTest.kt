package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.KotlinEmitter
import community.flock.graphqlsimplebindings.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.FileReader
import java.io.Reader

internal class KotlinEmitterTest {

    @Test
    fun testTypes() = run("type")

    @Test
    fun testEnumTypes() = run("enum")

    @Test
    fun testInputTypes() = run("input")

    @Test
    fun testInterfaceTypes() = run("interface")

    private fun run(name: String) {
        val schemaFile = javaClass.classLoader.getResource("$name.graphql")
        val snapshotFile = javaClass.classLoader.getResource("$name.graphql.kt.snapshot")

        val targetReader: Reader = FileReader(schemaFile.file)
        val emitter = KotlinEmitter()
        val schema = Parser.parseSchema(targetReader)

        val res = emitter.emitDocument(schema)

        assertEquals(snapshotFile.readText(), res)
    }

}
