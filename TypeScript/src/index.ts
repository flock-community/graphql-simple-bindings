import { parse } from 'graphql';
import { TypescriptRenderer } from './ts-renderer';
import * as prettier from 'prettier';
import { pipe } from './fp';

export function gql2ts(graphql: string): string {
  return pipe(
    graphql,
    parse,
    it => new TypescriptRenderer().renderDocument(it),
    it => prettier.format(it, { parser: 'typescript' }),
  );
}
