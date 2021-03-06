package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.generated.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController("/")
class RootEndpoint {

    @GetMapping
    fun getRoot() = Root(
            date = LocalDate.now(),
            product = Product("My Awesome Product", 10),
            user = User(
                    id = "0",
                    firstName = "Ernie",
                    lastName = "Sesamstraat",
                    type = Type.A,
                    keyValues = listOf(mapOf())
            ),
            accounts = listOf(
                    AccountPassword(
                            id = "0",
                            lastLogin = DateTime.now()
                    ),
                    AccountKey(
                            id = "0",
                            key = "123",
                            lastLogin = DateTime.now()
                    )
            )
    )

}
