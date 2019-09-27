import { TypescriptRenderer } from './ts-renderer';
import { parse } from 'graphql/language';
import * as prettier from 'prettier';
import { also, pipe } from './fp';
import fs from 'fs';

const renderer = new TypescriptRenderer();

test('t', () => {
  const input = `type Foo {
  bar: [String]
}`;

  const output = `export interface Foo {
  bar?: string[];
}
`;

  expectItToBeRenderedAs({
    input,
    output,
  });
});

function expectItToBeRenderedAs({ input, output }: { input: string; output: string }) {
  pipe(
    input,
    parse,
    it => new TypescriptRenderer().renderDocument(it),
    it => prettier.format(it, { parser: 'typescript' }),
    also(it => expect(it).toEqual(output)),
  );
}
