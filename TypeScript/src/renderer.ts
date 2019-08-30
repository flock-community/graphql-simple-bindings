import {
  DefinitionNode,
  DocumentNode,
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

export abstract class Renderer {
  public renderDocument(document: DocumentNode): string {
    return document.definitions.map(def => this.renderDefinition(def)).join('\n');
  }

  private renderDefinition(definition: DefinitionNode): string {
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

  protected renderFieldDefinition(node: FieldDefinitionNode): string {
    const args = node.arguments;
    if (args == null || args.length === 0) {
      return this.renderField(node);
    }
    return this.renderMethod(node);
  }

  protected renderInputFieldDefinition(inputFieldDefinition: InputValueDefinitionNode): string {
    return this.renderField(inputFieldDefinition);
  }

  protected renderType(type: TypeNode): string {
    switch (type.kind) {
      case 'NamedType':
        return `${this.renderNamedType(type)}`;
      case 'ListType':
        return this.renderListType(type);
      case 'NonNullType':
        return this.renderNonNullType(type);
    }
  }

  protected abstract renderObjectTypeDefinition(definition: ObjectTypeDefinitionNode): string;
  protected abstract renderInterfaceTypeDefinition(definition: InterfaceTypeDefinitionNode): string;
  protected abstract renderInputObjectTypeDefinition(definition: InputObjectTypeDefinitionNode): string;
  protected abstract renderNamedType(namedType: NamedTypeNode): string;
  protected abstract renderArguments(args: ReadonlyArray<InputValueDefinitionNode>): string;
  protected abstract renderField({ name, type }: { name: NameNode; type: TypeNode }): string;
  protected abstract renderMethod(fieldDefinition: FieldDefinitionNode): string;
  protected abstract renderNonNullType(node: NonNullTypeNode): string;
  protected abstract renderListType(node: ListTypeNode): string;
  protected abstract renderEnum(definition: EnumTypeDefinitionNode): string;
}

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
        return `${node.name.value}?`;
      case 'NonNullType':
        return `${node.name.value}`;
      case 'ListType':
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
