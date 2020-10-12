# Worst Movie

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- Add your CSV file to src/main/resources/csv folder, with name "movielist.csv"

## Running the application locally

You can run the application in two ways. First is to execute the `main` method in the `com.brunogaleski.worstmovie.WorstMovieApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

In the project root folder:
```shell
mvn spring-boot:run
```

In order to run with integration tests, do the following in the project root folder:

```shell
mvn clean install
```

## REST API

By default, the application will run in the port 8080

## Get award intervals

### Request

`GET /awardintervals`

    curl -i -H 'Accept: application/json' http://localhost:8080/awardintervals/

### Response Example

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 12 Oct 2020 20:32:25 GMT

    {
      "min": [
        {
          "producer": "Joel Silver",
          "interval": 1,
          "previousWin": 1990,
          "followingWin": 1991
        }
      ],
      "max": [
        {
          "producer": "Matthew Vaughn",
          "interval": 13,
          "previousWin": 2002,
          "followingWin": 2015
        }
      ]
    }



