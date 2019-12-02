package community.flock.graphqlsimplebindings

import community.flock.graphqlsimplebindings.emitter.TypeScriptEmitter

class TypeScriptGenerator(targetDirectory: String) : Generator(
        languageDirectory = "$targetDirectory/TypeScript",
        pathTemplate = { languageDirectory -> { fileName -> "$languageDirectory/${fileName.toLowerCase()}.d.ts" } },
        emitter = TypeScriptEmitter,
        disclaimer = "// This is generated code\n// DO NOT MODIFY\n// It will be overwritten\n\n"
)
