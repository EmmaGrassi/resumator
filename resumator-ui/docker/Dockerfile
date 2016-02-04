#
#  ╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐TM
#   ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘
#   ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─
#  ────────────────────────────────────────
#
# Licensed under MIT by the fine people of Sytac
# http://sytac.io

FROM nginx
MAINTAINER Tom Wieland <tom.wieland@sytac.io>

RUN cd /etc/nginx/conf.d/ && mv default.conf resumator.conf
COPY resumator/ /usr/share/nginx/html/
