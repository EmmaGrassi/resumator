To run the Resumator you need to have the following things in place:

- the Java application is up and running
- the web frontend is available at some URL
- the web frontend can connect to Java service at `//${host}/${resumator-ui path}/api`

## Running the Java application

### As a Docker container

The preferred way to run the Resumator service is by its Docker container. Once you have [built](running.md) the container, you should be able to run it as follows:

```shell
docker run -i -t --rm -p 9090:9090 ${image} -Dresumator.http.context.path=api/
```

The port mapping makes sure that the default Resumator port `9090` is visible on the Docker host as well. If you [configure](configure.md) it to run at some other port, you need to adjust the port mapping accordingly.

The `resumator.http.context.path` configuration entry is required to enable interaction with the frontend application.

### As a Java application

Once you have [built](building.md) the uberjar, you can run it as follows:

```shell
java -Dresumator.http.context.path=api/ -jar resumator-${version}.jar
```

## Running the Frontend application

TBD
