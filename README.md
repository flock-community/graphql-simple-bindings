# GraphQL-Simple-Bindings

## FE to BE bindings based on GraphQL schemas

### Kotlin
```
make build-kt
make run-kt
```
In container:
```
./test.sh
```
### TypeScript
```
make build-ts
make run-ts
```
In container make sure you have a directory `/app/example/dist` then in `/app/TypeScript` run:
```
npm install
npm test
```