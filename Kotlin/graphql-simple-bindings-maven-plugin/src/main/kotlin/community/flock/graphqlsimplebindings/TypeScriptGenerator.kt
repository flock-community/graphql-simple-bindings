package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter
import java.io.File
import java.util.Locale

class TypeScriptGenerator(targetDirectory: String, version: String, scalars: Map<String, String> = mapOf()) : Generator(
    languageDirectory = "$targetDirectory$typeDir",
    pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.lowercase(Locale.getDefault())}.d.ts" } },
    emitter = TypeScriptEmitter(scalars),
    disclaimer = "// This is generated code\n// DO NOT MODIFY\n// It will be overwritten\n\n"
) {

    init {
        File("$targetDirectory$typeDir/package.json").writeText("{\n  \"name\": \"graphql-simple-bindings\",\n  \"version\": \"$version\"\n}\n")
    }

    companion object {
        const val typeDir = "/TypeScript"
    }

}
