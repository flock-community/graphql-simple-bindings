package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import java.io.File

class TypeScriptGenerator(targetDirectory: String, version: String) : Generator(
        languageDirectory = "$targetDirectory$typeDir",
        pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.toLowerCase()}.d.ts" } },
        emitter = TypeScriptEmitter,
        disclaimer = "// This is generated code\n// DO NOT MODIFY\n// It will be overwritten\n\n"
) {

    init {
        File("$targetDirectory$typeDir/package.json").writeText("{\n  \"name\": \"graphql-simple-bindings\",\n  \"version\": \"$version\"\n}\n")
    }

    companion object {
        const val typeDir = "/TypeScript"
    }

}
