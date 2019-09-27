import { parse } from 'graphql/language';
import fs from 'fs';
import * as prettier from 'prettier';
import { also, pipe } from '../fp';
import { importSchema } from 'graphql-import';
import { TypescriptRenderer } from '../ts-renderer';

test('render example/app/app.graphql', () => {
  const input = `type App {
  user: User
  createdAt: Date
}

type User {
  name: String
  email: String
  account: [Account]
}

scalar Date

type Account {
  id: Int
  user: User
}
`;

  const output = `export interface App {
  user?: User;
  createdAt?: Date;
}

export interface User {
  name?: string;
  email?: string;
  account: Account[];
}

export interface Account {
  id?: number;
  user?: User;
}
`;

  pipe(
    importSchema('../example/app/app.graphql'),
    also(it => expect(it).toBe(input)),
    parse,
    it => new TypescriptRenderer().renderDocument(it),
    it => prettier.format(it, { parser: 'typescript' }),
    also(it => fs.writeFileSync('../example/dist/app.ts', it)),
    also(it => expect(output).toEqual(it)),
  );
});
