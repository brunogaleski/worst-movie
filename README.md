# Worst Movie

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- Add your CSV file to src/main/resources/csv folder, with name "movielist.csv"

## Running the application locally

You can run the application in two ways. First is to execute the `main` method in the `com.brunogaleski.worstmovie.WorstMovieApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

In order to run with integration tests, do the following:

```shell
mvn clean install
```
