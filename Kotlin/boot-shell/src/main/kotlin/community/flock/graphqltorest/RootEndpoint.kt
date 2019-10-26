package community.flock.graphqltorest

import community.flock.graphqltorest.generated.Product
import community.flock.graphqltorest.generated.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController("/")
class RootEndpoint {

    @GetMapping
    fun getRoot() = Root(
            date = LocalDate.now(),
            product = Product("My Awesome Product"),
            user = User(
                    id = "0",
                    firstName = "Ernie",
                    lastName = "Sesamstraat"
            )
    )

}
