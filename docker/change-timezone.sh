#!/bin/bash

ARG TZ='America/Sao_Paulo'

ENV TZ ${TZ}

apk upgrade --update /
apk add -U tzdata
RUN cp /usr/share/zoneinfo/${TZ} /etc/localtime
RUN apk del tzdata
RUN rm -rf /var/cache/apk/*