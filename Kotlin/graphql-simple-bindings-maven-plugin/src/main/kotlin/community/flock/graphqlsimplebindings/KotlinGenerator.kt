package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.KotlinEmitter

class KotlinGenerator(
        targetDirectory: String,
        packageName: String,
        scalars: Map<String, String>
) : Generator(
        languageDirectory = "$targetDirectory/Kotlin",
        pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.capitalize()}.kt" } },
        emitter = KotlinEmitter(packageName, scalars),
        disclaimer = "/**\n* This is generated code\n* DO NOT MODIFY\n* It will be overwritten\n*/\n\n"
)
