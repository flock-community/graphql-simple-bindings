# GraphQL-Simple-Bindings

Generate kotlin and typescript bindings based on GraphQL schemas

## Kotlin

### Getting started

Add the following configuration to your maven pom.xml

```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>community.flock</groupId>
                <artifactId>graphql-simple-bindings-maven-plugin</artifactId>
                <version>0.2.0</version>
                <configuration>
                    <language>All</language>
                    <packageName>community.flock.eco.feature.member.graphql</packageName>
                    <sourceDirectory>${project.basedir}/src/main/graphql</sourceDirectory>
                    <targetDirectory>${project.build.directory}/generated-sources/graphql</targetDirectory>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ...
    <repositories>
        <repository>
            <id>bintray-flock-community</id>
            <name>bintray</name>
            <url>https://api.bintray.com/maven/flock-community/flock-maven</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

</project>
```

## Run in Docker
```
make build-kt
make run-kt
```
In container:
```
cd ./Kotlin
./test.sh
```
## TypeScript
```
make build-ts
make run-ts
```
In container make sure you have a directory `/app/example/dist` then in `/app/TypeScript` run:
```
npm install
npm test
```
