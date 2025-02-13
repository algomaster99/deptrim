# DepTrim <img src=".img/logo.svg" align="left" height="135px" alt="DepTrim logo"/>

[![build](https://github.com/castor-software/deptrim/actions/workflows/build.yml/badge.svg)](https://github.com/castor-software/deptrim/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=alert_status)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![codecov](https://codecov.io/gh/castor-software/deptrim/branch/main/graph/badge.svg?token=L70YMFGJ4D)](https://codecov.io/gh/castor-software/deptrim)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=security_rating)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=bugs)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=code_smells)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=ncloc)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=castor-software_deptrim&metric=sqale_index)](https://sonarcloud.io/dashboard?id=castor-software_deptrim)

## What is DepTrim?

DepTrim is a Maven plugin to automatically specialize and diversify the dependencies of a Maven project through software debloating.
The objective is hardening the [software supply chain](https://www.cesarsotovalero.net/blog/the-software-supply-chain.html) of a project by creating dependencies that only contain the types necessary to build the project.
DepTrim can also create different variants of the dependencies in the dependency tree of a project.
It can be executed as a Maven goal through the command line or integrated directly into the Maven build lifecycle (CI/CD).
DepTrim does not modify the original source code of the project nor its original `pom.xml`.

## Usage

Configure the `pom.xml` file of a Maven project to use DepTrim as part of the build (`pom-debloated-spl.xml` will be created in the root of the project):

```xml
<plugin>
  <groupId>se.kth.castor</groupId>
  <artifactId>deptrim-maven-plugin</artifactId>
  <version>{DEPTRIM_LATEST_VERSION}</version>
  <executions>
    <execution>
      <goals>
        <goal>deptrim</goal>
      </goals>
      <configurations>
        <createPomTrimmed>true</createPomTrimmed>
      </configurations>
    </execution>
  </executions>
</plugin>
```
Or you can run DepTrim directly from the command line.

```bash
cd {PATH_TO_MAVEN_PROJECT}
mvn compile   
mvn compiler:testCompile
mvn se.kth.castor:deptrim-maven-plugin:{DEPTRIM_LATEST_VERSION}:deptrim -DcreatePomTrimmed=true
```

## Optional Parameters

The `deptrim-maven-plugin` can be configured with the following additional parameters.

| Name                     |     Type      | Description                                                                                                                                                                                                                                                                                                                                                         | 
|:-------------------------|:-------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| 
| `<trimDependencies>`     | `Set<String>` | Add a list of dependencies, identified by their coordinates, to be trimmed by DepTrim. **Dependency format is:** `groupId:artifactId:version:scope`. An Empty string indicates that all the dependencies in the dependency tree of the project will be trimmed (default).                                                                                           |
| `<ignoreDependencies>`   | `Set<String>` | Add a list of dependencies, identified by their coordinates, to be ignored by DepTrim during the analysis. Useful to override incomplete result caused by bytecode-level analysis. **Dependency format is:** `groupId:artifactId:version:scope`.                                                                                                                    |
| `<ignoreScopes>`         | `Set<String>` | Add a list of scopes, to be ignored by DepTrim during the analysis. Useful to not analyze dependencies with scopes that are not needed at runtime. **Valid scopes are:** `compile`, `provided`, `test`, `runtime`, `system`, `import`. An Empty string indicates no scopes (default).                                                                               |
| `<createPomTrimmed>`     |   `boolean`   | If this is `true`, DepTrim creates a trimmed version of the `pom.xml` in the root of the project, called `pom-debloated-spl.xml`, that uses the variant of the trimmed the dependencies. **Default value is:** `false`.                                                                                                                                             |
| `<createAllPomsTrimmed>` |   `boolean`   | If this is `true`, DepTrim creates all the combinations of trimmed version of the `pom.xml` in the root of the project (i.e., `2^y`), called `pom-debloated-spl-n-x-y.xml`, where `n` is the combination number, `x` is the number of trimmed dependencies in this combination, and `y` is the total number of trimmed dependencies. **Default value is:** `false`. |
| `<ignoreTests>`          |   `boolean`   | If this is `true`, DepTrim will not analyze the test classes in the project, and, therefore, the dependencies that are only used for testing will be considered unused. This parameter is useful to detect dependencies that have `compile` scope but are only used for testing. **Default value is:** `false`.                                                     |
| `<createResultJson>`     |   `boolean`   | If this is `true`, DepTrim creates a JSON file of the dependency tree along with metadata of each dependency. The file is called `deptrim-results.json`, and is located in the `target` directory of the project. **Default value is:** `false`.                                                                                                                    |
| `<createCallGraphCsv>`   |   `boolean`   | If this is `true`, DepTrim creates a CSV file with the static call graph of the API members used in the project. The file is called `deptrim-callgraph.csv`, and is located in the `target` directory of the project. **Default value is:** `false`.                                                                                                                |
| `<skipDepTrim>`          |   `boolean`   | Skip plugin execution completely. **Default value is:** `false`.                                                                                                                                                                                                                                                                                                    |

[//]: # (TODO: Explain here how to integrate DepTrim in the CI/CD pipeline so that a different variant of the dependencies is used for each build.)

## How does DepTrim works?

DepTrim runs before executing the `package` phase of the Maven build lifecycle. 
It relies on [DepClean](https://github.com/castor-software/depclean) to statically collects all the types used by the project under analysis as well as in its dependencies. 
Then, it removes all the types in the dependencies that are not used by the project.

With this usage information, DepTrim constructs a new `pom-debloated-spl.xml` file that declares only the specialized versions of the dependencies.
DepTrim follows the following three steps:

1. Add all used dependencies as direct dependencies
2. For the used dependencies, it removes the types that are not used by the project
3. Modify the original pom so that it uses the trimmed variants of the used dependencies

## Installing and building from source

Prerequisites:

- [Java OpenJDK 11](https://openjdk.java.net) or above
- [Apache Maven](https://maven.apache.org/)

In a terminal clone the repository and switch to the cloned folder:

```bash
git clone https://github.com/castor-software/deptrim.git
cd deptrim
```

Then run the following Maven command to build the application and install the plugin locally:

```bash
mvn clean install
```

## License

Distributed under the MIT License. See [LICENSE](https://github.com/castor-software/depclean/blob/master/LICENSE.md) for more information.

## Funding

DepTrim is partially funded by the [Wallenberg Autonomous Systems and Software Program (WASP)](https://wasp-sweden.org).

<img src="https://github.com/castor-software/depclean/blob/master/.img/wasp.svg" height="50px" alt="Wallenberg Autonomous Systems and Software Program (WASP)"/>
