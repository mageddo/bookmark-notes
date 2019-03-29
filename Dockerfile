FROM alpine
WORKDIR /app
ENV TMP_NAME=/app/bookmark-notes.zip
RUN apk add --update curl &&\
curl -L https://github.com/mageddo/bookmark-notes/releases/download/2.10.4/bookmark-notes-linux-amd64-2.10.4.zip > $TMP_NAME && \
tar -xvf $TMP_NAME -C /app/ && rm -f $TMP_NAME && apt-get purge --force-yes -y curl

FROM openjdk:11-jdk
COPY --from=BUILDER /app/bookmark-notes.zip /app
RUN unzip /app/bookmark-notes.zip -d /app && rm /app/bookmark*.zip
CMD /app/bookmark/bookmark-notes
