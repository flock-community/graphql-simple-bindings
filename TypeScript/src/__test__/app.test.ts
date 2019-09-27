import fs from 'fs';
import { also, pipe } from '../fp';
import { importSchema } from 'graphql-import';
import { gql2ts } from '../index';

test('render example/app/app.graphql', () => {
  const input = `type App {
  user: User!
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
  user: User;
  createdAt?: Date;
}

export interface User {
  name?: string;
  email?: string;
  account?: Account[];
}

export interface Account {
  id?: number;
  user?: User;
}
`;

  pipe(
    importSchema('../example/app/app.graphql'),
    also(it => expect(it).toEqual(input)),
    gql2ts,
    also(it => fs.writeFileSync('../example/dist/app.ts', it)),
    also(it => expect(output).toEqual(it)),
  );
});
