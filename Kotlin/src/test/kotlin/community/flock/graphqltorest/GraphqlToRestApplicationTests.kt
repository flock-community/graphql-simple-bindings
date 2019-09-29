package community.flock.graphqltorest

import community.flock.graphqltorest.renderer.KotlinRenderer
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

    @Test
    fun contextLoads() {
        val input = """
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

        val file = File(env.getProperty("workdirectory") + "/../example/dist/App.kt")

        Parser().parseDocument(input)
                .let { KotlinRenderer().renderDocument(it) }
                .let { file.writeText(it) }

    }

}
