export function compose<F>(f: F): F;

export function compose<F1, F2, R>(f1: (x1: F1) => F2, f2: (x2: F2) => R): (x1: F1) => R;

export function compose<F1, F2, F3, R>(f1: (x1: F1) => F2, f2: (x2: F2) => F3, f3: (x2: F3) => R): (x1: F1) => R;
export function compose<F1, F2, F3, F4, R>(
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => R,
): (x1: F1) => R;
export function compose<F1, F2, F3, F4, F5, R>(
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => F5,
  f5: (x2: F5) => R,
): (x1: F1) => R;
export function compose<F1, F2, F3, F4, F5, R>(
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => F5,
  f5: (x2: F5) => R,
): (x1: F1) => R;
export function compose<F1, F2, F3, F4, F5, F6, R>(
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => F5,
  f5: (x2: F5) => F5,
  f6: (x2: F6) => R,
): (x1: F1) => R;

export function compose(...fns: Function[]) {
  return (x: any) => fns.reduce((acc, fn) => fn(acc), x);
}

export function pipe<F>(f: F): F;
export function pipe<F1, R>(value: F1, f1: (x1: F1) => R): R;
export function pipe<F1, F2, R>(value: F1, f1: (x1: F1) => F2, f2: (x2: F2) => R): R;
export function pipe<F1, F2, F3, R>(value: F1, f1: (x1: F1) => F2, f2: (x2: F2) => F3, f3: (x2: F3) => R): R;
export function pipe<F1, F2, F3, F4, R>(
  value: F1,
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => R,
): R;
export function pipe<F1, F2, F3, F4, F5, R>(
  value: F1,
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => F5,
  f5: (x2: F5) => R,
): R;
export function pipe<F1, F2, F3, F4, F5, F6, R>(
  value: F1,
  f1: (x1: F1) => F2,
  f2: (x2: F2) => F3,
  f3: (x2: F3) => F4,
  f4: (x2: F4) => F5,
  f5: (x2: F5) => F6,
  f6: (x2: F6) => R,
): R;
export function pipe(value: any, ...fns: Function[]) {
  return fns.reduce((currentValue, fn) => fn(currentValue), value);
}

export function also<X>(fn: (x: X) => void) {
  return function(it: X) {
    fn(it);
    return it;
  };
}
