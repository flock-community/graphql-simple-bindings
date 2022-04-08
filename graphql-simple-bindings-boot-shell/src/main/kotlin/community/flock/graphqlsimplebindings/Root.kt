package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.generated.kotlin.Account
import community.flock.graphqlsimplebindings.generated.kotlin.Date
import community.flock.graphqlsimplebindings.generated.kotlin.Product
import community.flock.graphqlsimplebindings.generated.kotlin.User

data class Root(
    val date: Date,
    val product: Product,
    val user: User,
    val accounts: List<Account>
)
