FROM node:5.4.0-slim

ENV NODE_ENV dev
ENV APP_PATH /opt/bookmarks
ENV API_PATH /bk-api

WORKDIR ${APP_PATH}

# INSTALLING APP FILES
COPY package.json "${APP_PATH}"
RUN mkdir -p "$APP_PATH/db" && mkdir -p "$APP_PATH/logs" && npm install

ENV PROFILE=PROD
ENV NODE_ENV=prod
ENV NODE_CONFIG_DIR=conf

ARG DOWNLOAD_API_FROM_REMOTE=1

COPY bookmark-notes "${APP_PATH}"
COPY src "${APP_PATH}/src"
COPY view "${APP_PATH}/view"
COPY public "${APP_PATH}/public"
COPY app.js "${APP_PATH}"
COPY conf.default "${APP_PATH}/conf"
COPY conf.default "${APP_PATH}/conf.default"
COPY files/prod "${APP_PATH}/files/prod"

# INSTALLING API FILES
COPY build/* /tmp/

# GETTING API FILES FROM WEB
RUN if [ "$DOWNLOAD_API_FROM_REMOTE" = "1" ] ; then apt-get update && apt-get install -y curl && \
	curl -L https://github.com/mageddo/bookmark-notes/releases/download/2.9.2/bk-api-2.9.2.tgz > /tmp/bk-api.tgz ; fi

RUN mkdir -p $API_PATH && tar -xvf /tmp/bk-api*.tgz -C $API_PATH && rm -rf /tmp/*
COPY bk-api/conf $API_PATH/conf

CMD $APP_PATH/bookmark-notes
