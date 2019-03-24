#### Running on Intellij / Eclipse

Start `Application` class

#### Running on terminal

	./gradlew run


#### Testing

```bash
$ docker-compose -f docker-compose-test.yml up --force-recreate
./gradlew build
```

#### Compiling Native Image

	./gradlew clean build nativeImage
