package community.flock.graphqltorest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlToRestApplication

fun main(args: Array<String>) {
    runApplication<GraphqlToRestApplication>(*args)
}
