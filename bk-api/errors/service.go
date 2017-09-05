package errors

import "fmt"

type ServiceError struct {
	msg string
	Cause *ServiceError
}

func (e *ServiceError) Stack() string {
	var stack string
	if e.Cause != nil {
		stack = e.Cause.Stack()
	}
	return fmt.Sprintf("%s -> %s", e.msg, stack)
}

func (e *ServiceError) Error() string {
	return e.msg
}

func NewServiceError(error string) *ServiceError {
	return &ServiceError{msg: error}
}

func NewServiceErrorFromCause(error string, cause *ServiceError) *ServiceError {
	return &ServiceError{error, cause}
}
