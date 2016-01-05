The Resumator can be build as a plain JAR file or as a Docker container (preferred).

## Install Requirements

These are the minimum versions of the required software:

* JDK    8
* Maven  3.1
* Docker 1.9.1

## The Resumator backend service

The following sections explain how to build the Java backend of the Resumator. All commands are intended to be run from the `resumator-service` folder.

### Build the uberjar

The following commands will create an uberjar of the `resumator-service` backend:

```shell
mvn clean install
```

### Build the Docker container

After the uberjar has been created, you can create the Docker container using the following command:

```shell
docker build .
```

## The Resumator frontend

The Resumator UI is a plain web single page application, which currently needs no building steps.
