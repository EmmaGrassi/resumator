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

## Putting it all together

WARNING: the following documentation is still in pre-alpha state, no guarantee for it to work quite yet!

The following instructions document how to make everything work on an OSX box equipped with [`docker-machine`](https://docs.docker.com/machine/).

### Prerequisites

- OS X 10.x+
- docker 1.9.x+
- nginx 1.x+

If you run the docker containers as above against the default docker machine, you will have two ports listening on the docker machine box:

- 9090: the Java server
- XXXX: the web Frontend

You now need to know what IP the docker machine is using:

```
docker-machine ip default
```

You can then take that IP and write the following nginx configuration:

```
server {
    server_name resumator-api.sytac.io;
    listen 80;

    location / {
        proxy_pass http://${docker-machine IP}:9090/;
        proxy_set_header  X-Real-IP $remote_addr;
        proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header  Host $http_host;
    }
}

# TODO: fix the frontend
server {
    server_name resumator.sytac.io;

    location / {
        root /Users/skuro/Development/Sytac/resumator/frontend/src;
    }
}
```
