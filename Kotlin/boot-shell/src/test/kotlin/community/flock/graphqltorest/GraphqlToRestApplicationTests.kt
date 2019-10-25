package community.flock.graphqltorest

import community.flock.graphqltorest.renderer.KotlinRenderer
import community.flock.graphqltorest.renderer.TypeScriptRenderer
import community.flock.graphqltorest.renderer.meta.Renderer
import graphql.parser.Parser
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest
class GraphqlToRestApplicationTests {

    @Autowired
    lateinit var env: Environment

    private val input = """
            type App {
                user: User!
                createdAt: Date
            }

            type User {
                name: String
                email: String
                account: [Account]
            }

            scalar Date

            type Account {
                id: Int
                user: User
            }
		""".trimIndent()

    @Test
    fun `Kotlin Renderer`() = input renderedWith KotlinRenderer writtenTo "App.kt".file

    @Test
    fun `TypeScript Renderer`() = input renderedWith TypeScriptRenderer writtenTo "appFromKt.ts".file

    private infix fun String.renderedWith(renderer: Renderer) = Parser().parseDocument(this)
            .let { renderer.renderDocument(it) }

    private infix fun String.writtenTo(file: File?) = file?.writeText(this) ?: println(this)

    private val String.file
        get() = env.getProperty("exampleDirectory", String::class.java)
                ?.let { "$it/../example/dist/$this" }
                ?.let { File(it) }

}
