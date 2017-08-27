FROM node:5.4.0-slim

ENV NODE_ENV dev
ENV APP_PATH /opt/bookmarks
ENV API_PATH /bk-api

WORKDIR ${APP_PATH}

COPY package.json "${APP_PATH}"
RUN mkdir -p "$APP_PATH/db" && mkdir -p "$APP_PATH/logs" && npm install

COPY src "${APP_PATH}/src"
COPY view "${APP_PATH}/view"
COPY public "${APP_PATH}/public"
COPY app.js "${APP_PATH}"
COPY conf "${APP_PATH}/conf"
COPY conf "${APP_PATH}/conf.default"
COPY files/prod "${APP_PATH}/files/prod"

ENV TMP_NAME=/tmp/bookmark-notes.tgz
RUN apt-get update && apt-get install -y curl
RUN mkdir $API_PATH && curl -L https://github.com/mageddo/bookmark-notes/releases/download/2.8.0/bk-api-2.8.0.tgz > $TMP_NAME && \
	tar -xvf $TMP_NAME -C $API_PATH && rm -f $TMP_NAME
CMD ["bash", "-c", "nohup npm start & ls /bk-api && /bk-api/bk-api"]
