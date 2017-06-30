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
COPY conf "${app}/conf.original"
COPY files/prod "${app}/prod/sql"


EXPOSE 3000
CMD ["npm", "start"]
