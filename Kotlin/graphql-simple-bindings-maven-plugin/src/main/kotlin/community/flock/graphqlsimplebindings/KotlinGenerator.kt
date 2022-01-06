package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.KotlinEmitter
import java.util.Locale

class KotlinGenerator(
        targetDirectory: String,
        packageName: String,
        scalars: Map<String, String>,
        enableOpenApiAnnotations: Boolean
) : Generator(
        languageDirectory = "$targetDirectory/Kotlin",
        pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.capitalize()}.kt" } },
        emitter = KotlinEmitter(packageName, scalars, enableOpenApiAnnotations),
        disclaimer = "/**\n* This is generated code\n* DO NOT MODIFY\n* It will be overwritten\n*/\n\n"
)

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
