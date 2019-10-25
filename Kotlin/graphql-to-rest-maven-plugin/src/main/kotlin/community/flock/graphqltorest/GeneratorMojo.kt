package community.flock.graphqltorest

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter

@Mojo(name = "generate")
class GeneratorMojo : AbstractMojo() {

    @Parameter
    private var language: Language = Language.Kotlin

    @Parameter
    private var sourceDirectory: String = "Default Directory"

    override fun execute() {
        log.info("Generating ${language.name}")
        log.warn(sourceDirectory)
    }

}
