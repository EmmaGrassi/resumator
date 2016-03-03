The Resumator can be build as a plain JAR file or as a Docker container (preferred).

## Install Requirements

These are the minimum versions of the required software:

- JDK    8
- Maven  3.1
- Docker 1.9.1

### On Mac OS X

#### VirtualBox

Because Docker for Mac requires you to have VirtualBox installed, you will need to install this if you don't have it already.
To install VirtualBox you'll need to download the `.dmg` from the [Download page](https://www.virtualbox.org/wiki/Downloads) and run that file.

#### JDK
The JDK needs to be installed using a `.dmg` provided by Oracle which can be dowloaded from [this page](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

#### Maven
To install Maven on Mac OS X you will need [Homebrew](http://brew.sh/). If you do not have this installed already you can acquire it by running:

```bash
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
Now you are ready to install Maven. You do this by running:

```bash
brew install maven
```

#### Docker

Now the only thing remaining to install is Docker. Since we already have Homebrew we can use it to install Docker as well by running:

```bash
brew install docker
```
Since we are also using some utilities to make working with Docker easier you will also need to install `docker-compose` and `docker-machine`.
You do so by running:

```bash
brew install docker-compose; brew install docker-machine
```

Since we are running on a Mac we need to tell docker to use VirtualBox by default, we do this like so:
```bash
docker-machine create --driver virtualbox default
```
Now need to configure our shell by running:
```bash
eval $(docker-machine env default)
```
The `docker-machine` command would generate a config similar to this:

```bash
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.99.100:2376"
export DOCKER_CERT_PATH="/Users/<username>/.docker/machine/machines/default"
export DOCKER_MACHINE_NAME="default"
# Run this command to configure your shell:
# eval "$(docker-machine env default)"
```

The IP address listed in here is also the IP on which the application will run. We need to configure this in our `/etc/hosts` file.

```bash
##
# Host Database
#
# localhost is used to configure the loopback interface
# when the system is booting.  Do not change this entry.
##
127.0.0.1	localhost
255.255.255.255	broadcasthost
::1             localhost
192.168.99.100:8000  resumator.sytac.io # <--- Add this line
```

## First time run

In order for the project to run you'll need to make sure all the containers are build and the front-end assets are generated.

### Generating front-end assets

To generate the front-end assets you will need to run:
```bash
gulp production
```
This will transpile/compile and bundle the Javascript, LESS, images and minify the HTML.
This will also create the `resumator-ui` container which is needed in order to start the project.

### The backend service

The following sections explain how to build the Java backend of the Resumator. All commands are intended to be run from the `resumator-service` folder.

#### Build the uberjar

The following commands will create an uberjar of the `resumator-service` backend:

```shell
mvn clean install
```

#### Build the Docker container

After the uberjar has been created, you can create the Docker container using the following command:

```shell
docker build .
```
