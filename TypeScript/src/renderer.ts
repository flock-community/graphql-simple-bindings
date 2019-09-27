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
