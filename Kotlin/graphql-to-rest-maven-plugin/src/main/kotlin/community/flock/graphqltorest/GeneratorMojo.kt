package community.flock.graphqltorest

import community.flock.graphqltorest.Language.*
import community.flock.graphqltorest.renderer.KotlinRenderer
import graphql.language.Document
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import java.io.File

typealias FileName = String

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
class GeneratorMojo : AbstractMojo() {

    @Parameter
    private var language: Language = Kotlin

    @Parameter(required = true)
    private lateinit var sourceDirectory: String

    @Parameter(required = true)
    private lateinit var targetDirectory: String

    override fun execute() {
        File(targetDirectory).mkdirs()
        (File(sourceDirectory).listFiles() ?: arrayOf())
                .map { Pair(it.name.split(".")[0], it.bufferedReader(Charsets.UTF_8)) }
                .map { Pair(it.first, KotlinRenderer.parseSchema(it.second)) }
                .generate()
    }

    private fun List<Pair<FileName, Document>>.generate() = when (language) {
        Kotlin -> generateKotlin()
        TypeScript -> generateTypeScript()
        All -> generateAll()
    }.also { log.info("Generating ${language.name}") }

    private fun List<Pair<FileName, Document>>.generateAll() {
        generateKotlin()
        generateTypeScript()
    }

    private fun List<Pair<FileName, Document>>.generateKotlin() = KotlinGenerator(targetDirectory).generate(this)
    private fun List<Pair<FileName, Document>>.generateTypeScript() = TypeScriptGenerator(targetDirectory).generate(this)

}
