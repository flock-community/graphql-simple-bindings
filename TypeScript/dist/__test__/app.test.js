"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = __importDefault(require("fs"));
const fp_1 = require("../fp");
const graphql_import_1 = require("graphql-import");
const index_1 = require("../index");
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
    fp_1.pipe(graphql_import_1.importSchema('../example/app/app.graphql'), fp_1.also(it => expect(it).toEqual(input)), index_1.gql2ts, fp_1.also(it => fs_1.default.writeFileSync('../example/dist/app.ts', it)), fp_1.also(it => expect(output).toEqual(it)));
});
//# sourceMappingURL=app.test.js.map