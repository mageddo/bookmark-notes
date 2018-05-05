With the same idea of [java 8 Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) go-optional turns your code nullsafe, see samples:

```go
notNullNumber := optional.OfNullable(nil).OrElse(1).(int)
fmt.Println(notNullNumber) // 1
```

Mapping

```go
formattedDate := optional.OfNullable(time.Now())
	.Map(func(date interface{}) interface{}{
		return date.Update.Format("2006-01-02 15:04:05")
	}).OrElse("").(string)
```