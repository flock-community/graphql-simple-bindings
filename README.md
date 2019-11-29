# GraphQL-Simple-Bindings

## FE to BE bindings based on GraphQL schemas

###
Maven Plugin:

[ ![Download](https://api.bintray.com/packages/flock-community/flock-maven/graphql-simple-bindings-maven-plugin/images/download.svg?version=0.0.1) ](https://bintray.com/flock-community/flock-maven/graphql-simple-bindings-maven-plugin/0.0.1/link)

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