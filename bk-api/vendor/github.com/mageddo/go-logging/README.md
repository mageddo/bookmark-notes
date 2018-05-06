A really simple and fast logger implementation independent

Example

```go
import "github.com/mageddo/go-logging"
...
logging.Debugf("hey %s", "Mark")
logging.Infof("hey %s", "Mark")
logging.Warnf("hey %s", "Mark")
logging.Errorf("hey %s", "Mark")
```

Testing it

    docker-compose up --abort-on-container-exit ci-build-test