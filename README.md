# GraphQL-Simple-Bindings

Parse a graphql schema and generate simple bindings for:
- Typescript
- Kotlin
- Scala


## Getting started

Add the following configuration to your maven pom.xml

```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>community.flock</groupId>
                <artifactId>graphql-simple-bindings-maven-plugin</artifactId>
                <version>0.7.0</version>
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
make build
make run
```
In container:
```
./test.sh
```
