destroy-ts:
	docker rmi graphql-to-rest-ts
.PHONY: destroy-ts

build-ts:
	docker build -f Dockerfile-ts -t graphql-to-rest-ts .
.PHONY: build-ts

run-ts:
	docker run -it --name gtr-ts --rm -v $(shell pwd)/TypeScript:/app/TypeScript -v $(shell pwd)/example:/app/example graphql-to-rest-ts bash
.PHONY: run-ts

destroy-kt:
	docker rmi graphql-to-rest-kt
.PHONY: destroy-kt

build-kt:
	docker build -f Dockerfile-kt -t graphql-to-rest-kt .
.PHONY: build-kt

run-kt:
	docker run -it --name gtr-kt --rm -v $(shell pwd)/Kotlin:/app/Kotlin -v $(shell pwd)/example:/app/example -v ~/.m2:/root/.m2 graphql-to-rest-kt bash
.PHONY: run-kt
