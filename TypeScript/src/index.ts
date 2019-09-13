import { parse } from 'graphql/language';
import fs from 'fs';
import { TypescriptRenderer } from './renderer';
import * as prettier from 'prettier';
import { also, pipe } from './fp';
import { importSchema } from 'graphql-import';
const { log } = console;

pipe(
  importSchema('../../example/app/app.graphql'),
  also(it => log('\nInput: \n-------\n\n' + it)),
  parse,
  it => new TypescriptRenderer().renderDocument(it),
  it => prettier.format(it, { parser: 'typescript' }),
  also(it => fs.writeFileSync('../../example/dist/app.ts', it)),
  also(it => log('\nOutput: \n-------\n\n' + it)),
);
