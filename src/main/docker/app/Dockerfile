FROM openjdk:8-jdk AS BUILDER
WORKDIR /app
COPY ./ ./
RUN ./gradlew clean build reflectConfigFiles nativeImage

FROM frolvlad/alpine-glibc
COPY --from=BUILDER /app/build/graal/app /usr/bin
ENTRYPOINT ["/usr/bin/app"]
