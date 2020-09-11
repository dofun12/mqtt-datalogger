FROM grafana/grafana

FROM alpine:3.11

ARG TZ='America/Sao_Paulo'

ENV TZ ${TZ}
USER root
RUN apk upgrade --update \
    && apk add -U tzdata \
    && cp /usr/share/zoneinfo/${TZ} /etc/localtime && rm -rf /var/cache/apk/*

CMD ["ls"]


EXPOSE 3000
