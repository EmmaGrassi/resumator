#
#  ╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐TM
#   ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘
#   ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─
#  ────────────────────────────────────────
#
# Licensed under MIT by the fine people of Sytac
# http://sytac.io

FROM java:openjdk-8-jre
MAINTAINER Carlo Sciolla <carlo.sciolla@sytac.io>

# Prepare the app root folder
RUN mkdir -p /usr/local/resumator
WORKDIR /usr/local/resumator

# Install the app
COPY target/resumator-0.1-SNAPSHOT.jar /usr/local/resumator/resumator.jar

# TODO: write the config!

# Run the app
CMD ["java", "-jar", "resumator.jar"]

