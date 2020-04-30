"use strict";
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
const graphql_1 = require("graphql");
const ts_renderer_1 = require("./ts-renderer");
const prettier = __importStar(require("prettier"));
const fp_1 = require("./fp");
function gql2ts(graphql) {
    return fp_1.pipe(graphql, graphql_1.parse, it => new ts_renderer_1.TypescriptRenderer().renderDocument(it), it => prettier.format(it, { parser: 'typescript' }));
}
exports.gql2ts = gql2ts;
//# sourceMappingURL=index.js.map