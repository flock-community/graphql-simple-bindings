package community.flock.graphqltorest.reader

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDate


@Component
class DirectoryReader(env: Environment) {

    private final val answer = Unit
//            .let { File(env.getProperty("workdirectory") + "/../example").listFiles() ?: arrayOf() }
//            .map { File(it.canonicalPath) }
//            .filter { it.isDirectory }
//            .first { it.canonicalPath.contains("app") }
//            .let { it.listFiles() ?: arrayOf() }
//            .first()
//            .also { println(it.canonicalPath) }
//            .bufferedReader(Charsets.UTF_8)
//            .let { Parser().parseDocument(it) }
//            .also { println(it) }
//            .let { KotlinRenderer().renderDocument(it) }
//            .also { println(it) }

}
