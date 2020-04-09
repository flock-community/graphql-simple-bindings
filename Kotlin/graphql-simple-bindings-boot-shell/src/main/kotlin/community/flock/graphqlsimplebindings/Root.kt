package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.generated.Date
import community.flock.graphqlsimplebindings.generated.Product
import community.flock.graphqlsimplebindings.generated.User
import community.flock.graphqlsimplebindings.generated.Account

data class Root(
        val date: Date,
        val product: Product,
        val user: User,
        val accounts: List<Account>
)
