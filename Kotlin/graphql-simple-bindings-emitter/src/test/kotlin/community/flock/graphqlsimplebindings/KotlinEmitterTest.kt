package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.KotlinEmitter
import community.flock.graphqlsimplebindings.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.FileReader
import java.io.Reader


internal class KotlinEmitterTest {

    @Test
    fun testTypes() {
        val schemaFile = javaClass.classLoader.getResource("type.graphql")
        val snapshotFile = javaClass.classLoader.getResource("type.graphql.snapshot")

        val targetReader: Reader = FileReader(schemaFile.file)
        val emitter = KotlinEmitter()
        val schema = Parser.parseSchema(targetReader)

        val res = emitter.emitDocument(schema)

        assertEquals(snapshotFile.readText(), res)
    }

    @Test
    fun testEnumTypes() {
        val schemaFile = javaClass.classLoader.getResource("enum.graphql")
        val snapshotFile = javaClass.classLoader.getResource("enum.graphql.snapshot")

        val targetReader: Reader = FileReader(schemaFile.file)
        val emitter = KotlinEmitter()
        val schema = Parser.parseSchema(targetReader)

        val res = emitter.emitDocument(schema)

        assertEquals(snapshotFile.readText(), res)
    }

}
