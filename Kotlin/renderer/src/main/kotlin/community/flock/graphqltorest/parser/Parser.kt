package community.flock.graphqltorest.parser

import graphql.language.Document
import graphql.parser.Parser
import java.io.Reader

object Parser {

    fun parseSchema(reader: Reader): Document = Parser().parseDocument(reader)
    fun parseSchema(schema: String, sourceName: String? = null): Document = Parser().parseDocument(schema, sourceName)

}
