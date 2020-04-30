"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const renderer_1 = require("./renderer");
class TypescriptRenderer extends renderer_1.Renderer {
    renderObjectTypeDefinition(node) {
        const extend = node.interfaces.length === 0 ? '' : `extends ${node.interfaces.map(it => it.name.value).join(',')}`;
        return `
      export interface ${node.name.value} ${extend} {
        ${node.fields.map(field => this.renderFieldDefinition(field)).join(';')}
      }
    `;
    }
    renderInterfaceTypeDefinition(node) {
        return `
      export interface ${node.name.value} {
        ${node.fields.map(field => this.renderFieldDefinition(field)).join(';')}
      }
    `;
    }
    renderInputObjectTypeDefinition(node) {
        return `
      export interface ${node.name.value} {
        ${node.fields.map(field => this.renderInputFieldDefinition(field)).join(';')}
      }
    `;
    }
    renderMethod(node) {
        return `${node.name.value}${this.renderArguments(node.arguments)}: ${this.renderType(node.type)}`;
    }
    renderArguments(args) {
        return `(${args.map(it => this.renderInputFieldDefinition(it)).join(', ')})`;
    }
    renderField(node) {
        return `${this.renderFieldName(node)}: ${this.renderType(node.type)}`;
    }
    renderFieldName(node) {
        switch (node.type.kind) {
            case 'NamedType':
            case 'ListType':
                return `${node.name.value}?`;
            case 'NonNullType':
                return `${node.name.value}`;
        }
    }
    renderEnum(node) {
        return `export type ${node.name.value} = ${node.values.map(it => `'${it.name.value}'`).join(' | ')}`;
    }
    renderNonNullType(node) {
        return this.renderType(node.type);
    }
    renderListType(node) {
        return `${this.renderType(node.type)}[]`;
    }
    renderNamedType(namedType) {
        return `${this.renderScalar(namedType.name.value)}`;
    }
    renderScalar(string) {
        switch (string) {
            case 'Date':
                return 'Date';
            case 'String':
                return 'string';
            case 'Int':
                return 'number';
            case 'Float':
                return 'number';
            case 'ID':
                return 'string';
            default:
                // Not a scalar
                return string;
        }
    }
}
exports.TypescriptRenderer = TypescriptRenderer;
//# sourceMappingURL=ts-renderer.js.map