package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.JavaEmitter

class JavaGenerator(
    targetDirectory: String,
    packageName: String,
    scalars: Map<String, String>,
    enableOpenApiAnnotations: Boolean
) : Generator(
    languageDirectory = "$targetDirectory/${packageName.split(".").joinToString("/")}",
    pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.capitalize()}.java" } },
    emitter = JavaEmitter(packageName, scalars, enableOpenApiAnnotations),
    disclaimer = "/**\n * This is generated code\n * DO NOT MODIFY\n * It will be overwritten\n */\n\n"
)
