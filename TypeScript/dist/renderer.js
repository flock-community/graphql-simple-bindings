"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class Renderer {
    renderDocument(document) {
        return document.definitions.map(def => this.renderDefinition(def)).join('\n');
    }
    renderDefinition(definition) {
        switch (definition.kind) {
            case 'ObjectTypeDefinition':
                return this.renderObjectTypeDefinition(definition);
            case 'InputObjectTypeDefinition':
                return this.renderInputObjectTypeDefinition(definition);
            case 'EnumTypeDefinition':
                return this.renderEnum(definition);
            case 'InterfaceTypeDefinition':
                return this.renderInterfaceTypeDefinition(definition);
            default:
                return '';
        }
    }
    renderFieldDefinition(node) {
        const args = node.arguments;
        if (args == null || args.length === 0) {
            return this.renderField(node);
        }
        return this.renderMethod(node);
    }
    renderInputFieldDefinition(inputFieldDefinition) {
        return this.renderField(inputFieldDefinition);
    }
    renderType(type) {
        switch (type.kind) {
            case 'NamedType':
                return `${this.renderNamedType(type)}`;
            case 'ListType':
                return this.renderListType(type);
            case 'NonNullType':
                return this.renderNonNullType(type);
        }
    }
}
exports.Renderer = Renderer;
//# sourceMappingURL=renderer.js.map