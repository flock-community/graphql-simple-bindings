import { parse } from 'graphql/language';
import fs, { readFileSync } from 'fs';
import {TypescriptRenderer } from './renderer';
import * as prettier from 'prettier';
import { also, pipe } from './fp';
const { log } = console;

pipe(
  readFileSync('../../example/checkout/checkout.graphql', 'utf-8'),
  also(it => log('\nInput: \n-------\n\n' + it)),
  parse,
  it => new TypescriptRenderer().renderDocument(it),
  it => prettier.format(it, { parser: 'typescript' }),
  also(it => fs.writeFileSync('../../example/dist/schema.ts', it)),
  also(it => log('\nOutput: \n-------\n\n' + it)),
);
