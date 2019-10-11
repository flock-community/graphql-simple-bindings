package community.flock.graphqltorest.reader

import community.flock.graphqltorest.renderer.KotlinRenderer
import graphql.parser.Parser
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.File


@Component
class DirectoryReader(env: Environment) {

    init {
        if (env.getProperty("run", Boolean::class.java) == true) {
            env.getProperty("exampleDirectory")?.let { File(it) }
                    .let { it?.listFiles() ?: arrayOf() }
                    .map { File(it.canonicalPath) }
                    .apply { forEach { println(it) } }
                    .filter { it.isDirectory }
                    .first { it.canonicalPath.contains("example/shared") }
                    .let { it.listFiles() ?: arrayOf() }
                    .map { it.bufferedReader(Charsets.UTF_8) }
                    .map { Parser().parseDocument(it) }
                    .map { KotlinRenderer().renderDocument(it, false) }
                    .forEach { println("********** Output:\n$it\n**********") }
        }
    }

}
