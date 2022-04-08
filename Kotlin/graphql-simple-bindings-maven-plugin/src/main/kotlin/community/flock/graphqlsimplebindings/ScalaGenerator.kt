package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.ScalaEmitter

class ScalaGenerator(
    targetDirectory: String,
    packageName: String,
    scalars: Map<String, String>,
    enableOpenApiAnnotations: Boolean
) : Generator(
    languageDirectory = "$targetDirectory/${packageName.split(".").joinToString("/")}",
    pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.capitalize()}.scala" } },
    emitter = ScalaEmitter(packageName, scalars, enableOpenApiAnnotations),
    disclaimer = "/**\n * This is generated code\n * DO NOT MODIFY\n * It will be overwritten\n */\n\n"
)
