package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import community.flock.graphqlsimplebindings.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.FileReader
import java.io.Reader


internal class TypeScriptEmitterTest {

    @Test
    fun testTypes() = run("type")

    @Test
    fun testEnumTypes() = run("enum")

    @Test
    fun testInputTypes() = run("input")

    private fun run(name: String) {
        val schemaFile = javaClass.classLoader.getResource("$name.graphql")
        val snapshotFile = javaClass.classLoader.getResource("$name.graphql.ts.snapshot")

        val targetReader: Reader = FileReader(schemaFile.file)
        val schema = Parser.parseSchema(targetReader)

        val res = TypeScriptEmitter().emitDocument(schema)

        assertEquals(snapshotFile.readText(), res)
    }

}
