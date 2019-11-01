destroy-ts:
	docker rmi graphql-simple-bindings-ts
.PHONY: destroy-ts

build-ts:
	docker build -f Dockerfile-ts -t graphql-simple-bindings-ts .
.PHONY: build-ts

run-ts:
	docker run -it --name gsb-ts --rm -v $(shell pwd)/TypeScript:/app/TypeScript -v $(shell pwd)/example:/app/example graphql-simple-bindings-ts bash
.PHONY: run-ts

destroy-kt:
	docker rmi graphql-simple-bindings-kt
.PHONY: destroy-kt

build-kt:
	docker build -f Dockerfile-kt -t graphql-simple-bindings-kt .
.PHONY: build-kt

run-kt:
	docker run -p 8080:8080 -it --name gsb-kt --rm -v $(shell pwd)/Kotlin:/app/Kotlin -v $(shell pwd)/example:/app/example -v ~/.m2:/root/.m2 graphql-simple-bindings-kt bash
.PHONY: run-kt
