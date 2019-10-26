package community.flock.graphqltorest

import community.flock.graphqltorest.generated.Date
import community.flock.graphqltorest.generated.Product
import community.flock.graphqltorest.generated.User

data class Root(
        val date: Date,
        val product: Product,
        val user: User
)
