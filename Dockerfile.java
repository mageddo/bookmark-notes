FROM alpine
WORKDIR /app
ENV TMP_NAME=/app/bookmark-notes.zip
ENV BOOKMARK_NOTES_VERSION=3.0.3
RUN apk add --update curl &&\
curl -L "https://github.com/mageddo/bookmark-notes/releases/download/${BOOKMARK_NOTES_VERSION}/bookmark-notes-${BOOKMARK_NOTES_VERSION}.zip" > $TMP_NAME && \
tar -xvf $TMP_NAME -C /app/ && rm -f $TMP_NAME && apt-get purge --force-yes -y curl

FROM debian:9
COPY --from=BUILDER /app/bookmark-notes.zip /app
RUN unzip /app/bookmark-notes.zip -d /app && rm /app/bookmark*.zip
CMD /app/bookmark/bookmark-notes
