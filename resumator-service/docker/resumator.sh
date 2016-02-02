#!/usr/bin/env bash

#  ╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐TM
#   ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘
#   ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─
# ─────────────────────────────────── v 0.1

PARAMS=${@}
java ${PARAMS} -agentlib:jdwp=transport=dt_socket,server=y,address=5005,suspend=n -jar ./resumator.jar