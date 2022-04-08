destroy:
	docker rmi graphql-simple-bindings-kt
.PHONY: destroy

build:
	docker build -t graphql-simple-bindings-kt .
.PHONY: build

run:
	docker run -p 8080:8080 -it --name gsb-kt --rm -v $(shell pwd):/app -v $(shell pwd)/example:/app/example -v ~/.m2:/root/.m2 graphql-simple-bindings-kt bash
.PHONY: run
