import { parse } from 'graphql/language';
import fs, { readFileSync } from 'fs';
import { KotlinRenderer, TypescriptRenderer } from './renderer';
import * as prettier from 'prettier';
import { also, pipe } from './fp';
const { log } = console;

const response = `
export interface Error {
  type: string
  message: string
}

export interface Response<T> {
  data: T
  errors: Error[]
}

`;
pipe(
  readFileSync('schema.graphql', 'utf-8'),
  also(it => log('\nInput: \n-------\n\n' + it)),
  parse,
  it => response + new TypescriptRenderer().renderDocument(it),
  it => prettier.format(it, { parser: 'typescript' }),
  also(it => fs.writeFileSync('schema.ts', it)),
  also(it => log('\nOutput: \n-------\n\n' + it)),
);

// pipe(
//   readFileSync('schema.graphql', 'utf-8'),
//   also(it => log('\nInput: \n-------\n\n' + it)),
//   parse,
//   it => new KotlinRenderer().renderDocument(it),
//   // it => prettier.format(it, { parser: 'typescript' }),
//   also(it => fs.writeFileSync('schema.kt', it)),
//   also(it => log('\nOutput: \n-------\n\n' + it)),
// )
