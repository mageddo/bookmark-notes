Bookmark Notes uses DNS proxy server for development purposes

Starting DNS server 

```bash
$ docker run --rm --hostname dns.mageddo \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /etc/resolv.conf:/etc/resolv.conf \
defreitas/dns-proxy-server
```


## Developing

__Starting database server__

	$ docker-compose -f docker-compose.yml up postgres

__Running on Intellij / Eclipse__

Start `Application` class

__Running on terminal__

	$ ./gradlew run

__Fully testing__

Building and testing
```bash
./gradlew build intTest
```

## Building archives

```
$ ./gradlew clean build nativeImage
$ ls build/distributions/ && ls build/graal/
bookmark-notes.zip # java binary
bookmark-notes # linux os native binary
```
