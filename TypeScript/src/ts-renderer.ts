import {
  EnumTypeDefinitionNode,
  FieldDefinitionNode,
  InputObjectTypeDefinitionNode,
  InputValueDefinitionNode,
  InterfaceTypeDefinitionNode,
  ListTypeNode,
  NamedTypeNode,
  NameNode,
  NonNullTypeNode,
  ObjectTypeDefinitionNode,
  TypeNode,
} from 'graphql';
import { Renderer } from './renderer';

export class TypescriptRenderer extends Renderer {
  renderObjectTypeDefinition(node: ObjectTypeDefinitionNode) {
    const extend = node.interfaces.length === 0 ? '' : `extends ${node.interfaces.map(it => it.name.value).join(',')}`;
    return `
      export interface ${node.name.value} ${extend} {
        ${node.fields.map(field => this.renderFieldDefinition(field)).join(';')}
      }
    `;
  }

  renderInterfaceTypeDefinition(node: InterfaceTypeDefinitionNode): string {
    return `
      export interface ${node.name.value} {
        ${node.fields.map(field => this.renderFieldDefinition(field)).join(';')}
      }
    `;
  }

  renderInputObjectTypeDefinition(node: InputObjectTypeDefinitionNode) {
    return `
      export interface ${node.name.value} {
        ${node.fields.map(field => this.renderInputFieldDefinition(field)).join(';')}
      }
    `;
  }

  renderMethod(node: FieldDefinitionNode): string {
    return `${node.name.value}${this.renderArguments(node.arguments)}: ${this.renderType(node.type)}`;
  }

  renderArguments(args: ReadonlyArray<InputValueDefinitionNode>) {
    return `(${args.map(it => this.renderInputFieldDefinition(it)).join(', ')})`;
  }

  renderField(node: { name: NameNode; type: TypeNode }): string {
    return `${this.renderFieldName(node)}: ${this.renderType(node.type)}`;
  }

  renderFieldName(node: { name: NameNode; type: TypeNode }) {
    switch (node.type.kind) {
      case 'NamedType':
      case 'ListType':
        return `${node.name.value}?`;
      case 'NonNullType':
        return `${node.name.value}`;
    }
  }

  renderEnum(node: EnumTypeDefinitionNode) {
    return `export type ${node.name.value} = ${node.values.map(it => `'${it.name.value}'`).join(' | ')}`;
  }

  renderNonNullType(node: NonNullTypeNode) {
    return this.renderType(node.type);
  }

  renderListType(node: ListTypeNode) {
    return `${this.renderType(node.type)}[]`;
  }

  renderNamedType(namedType: NamedTypeNode) {
    return `${this.renderScalar(namedType.name.value)}`;
  }

  renderScalar(string: string) {
    switch (string) {
      case 'Date':
        return 'Date';
      case 'DateTime':
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
