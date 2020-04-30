"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function compose(...fns) {
    return (x) => fns.reduce((acc, fn) => fn(acc), x);
}
exports.compose = compose;
function pipe(value, ...fns) {
    return fns.reduce((currentValue, fn) => fn(currentValue), value);
}
exports.pipe = pipe;
function also(fn) {
    return function (it) {
        fn(it);
        return it;
    };
}
exports.also = also;
//# sourceMappingURL=fp.js.map