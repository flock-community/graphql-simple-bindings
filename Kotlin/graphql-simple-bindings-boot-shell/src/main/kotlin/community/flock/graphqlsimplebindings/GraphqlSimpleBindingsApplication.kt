package community.flock.graphqlsimplebindings

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class GraphqlSimpleBindingsApplication

fun main(args: Array<String>) {
    runApplication<GraphqlSimpleBindingsApplication>(*args)
}
