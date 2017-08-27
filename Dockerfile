FROM node:5.4.0-slim

ENV NODE_ENV dev
ENV app /opt/bookmarks

WORKDIR ${app}

COPY package.json "${app}"
RUN mkdir -p "$app/db" && mkdir -p "$app/logs" && npm install

COPY src "${app}/src"
COPY view "${app}/view"
COPY public "${app}/public"
COPY app.js "${app}"
COPY conf "${app}/conf"
COPY conf "${app}/conf.default"
COPY files/prod "${app}/files/prod"

ENV TMP_NAME=/tmp/bookmark-notes.tgz
RUN apt-get update && apt-get install -y curl
RUN mkdir /bk-api && curl -L https://github.com/mageddo/bookmark-notes/releases/download/2.8.0/bk-api-2.8.0.tgz > $TMP_NAME && \
	tar -xvf $TMP_NAME -C /app/ && rm -f $TMP_NAME
CMD ["bash", "-c", "nohup npm start & /app/bookmark-notes"]
