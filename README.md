[![test-run](https://github.com/eataborda/api-rest-assured/actions/workflows/api-rest-assured-test-run.yml/badge.svg)](https://github.com/eataborda/api-rest-assured/actions/workflows/api-rest-assured-test-run.yml)
[![Junit5](https://img.shields.io/badge/Junit5-5.10.3-blue)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine)
[![REST-assured](https://img.shields.io/badge/RESTAssured-5.5.0-blue)](https://mvnrepository.com/artifact/io.rest-assured/rest-assured)
[![Gradle](https://img.shields.io/badge/Gradle-8.9-blue)](https://gradle.org/releases/)


# Basic API automation
Automation using Java + Junit5 + REST-Assured + Gradle
used to test the [Restful-Booker](https://restful-booker.herokuapp.com/) service created by Mark Winteringham, current service introduce intentionally errors
on the behavior of the service for study purposes and practice API tests

Contains:
- Basic build.gradle.kts config
- Basic API automation with general validations for the following request methods: POST, GET, PUT, PATCH, DELETE

## Use sample project locally
- Verify that you have `Git`
- Verify that you have `Java` installed, also that you already setup the following environment variables: `$PATH` and `$JAVA_HOME`
- Clone the repository and move inside that path:
```shellscript
$ gh repo clone eataborda/api-rest-assured
$ cd ./api-rest-assured
```
### Before run
Automation was setup to get some environment variables from repository to execute the tests on the workflows,
to execute the tests locally is necessary to set first the following environment variables:
```
$  export USER=admin PASSWORD=password123
```
Values can be consulted on the following restul-booker [apidoc](https://restful-booker.herokuapp.com/apidoc/index.html)

### Run tests
- Run all tests on the src:
```
$ ./gradlew
```
- Run all tests inside class using Junit5 tags (@Tags):
```
$ ./gradlew -DincludeTags="regression"
```
- Run a specific test method (@Test) inside class using Junit5 tags (@Tag):
```
$ ./gradlew -DincludeTags="status_code:200"
```
In this way you can use the following tags depending on the tests you need to run:

health_check, workflow, regression, smoke, get-method, post-method, put-method, patch-method,
delete-method, status-code:all, status-code:200, status-code:201, status-code:400, status-code:403,
status-code:404, status-code:405, status-code:500