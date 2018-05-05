package logging

import (
	"bytes"
	"github.com/mageddo/go-logging/pkg/trace"
)

type defaultLogger struct {
	writer Printer
	level int
}

func New(p Printer, level ...int) *defaultLogger {
	if(len(level) > 0){
		return &defaultLogger{p, level[0]}
	}
	return &defaultLogger{p, 2}
}

func (l *defaultLogger) Debug(args ...interface{}) {
	args = append([]interface{}{withCallerMethod(withLevel(new(bytes.Buffer), "DEBUG"), l.level).String()}, args...)
	l.Printer().Println(args...)
}

func (l *defaultLogger) Debugf(format string, args ...interface{}) {
	l.Printer().Printf(withFormat(withCallerMethod(withLevel(new(bytes.Buffer), "DEBUG"), l.level), format).String(), args...)
}

func (l *defaultLogger) Info(args ...interface{}) {
	args = append([]interface{}{withCallerMethod(withLevel(new(bytes.Buffer), "INFO"), l.level).String()}, args...)
	l.Printer().Println(args...)
}
func (l *defaultLogger) Infof(format string, args ...interface{}) {
	l.Printer().Printf(withFormat(withCallerMethod(withLevel(new(bytes.Buffer), "INFO"), l.level), format).String(), args...)
}

func (l *defaultLogger) Warning(args ...interface{}) {
	args = append([]interface{}{withCallerMethod(withLevel(new(bytes.Buffer), "WARNING"), l.level).String()}, args...)
	l.Printer().Println(args...)
}
func (l *defaultLogger) Warningf(format string, args ...interface{}) {
	l.Printer().Printf(withFormat(withCallerMethod(withLevel(new(bytes.Buffer), "WARNING"), l.level), format).String(), args...)
}

func (l *defaultLogger) Error(args ...interface{}) {
	args = append([]interface{}{withCallerMethod(withLevel(new(bytes.Buffer), "ERROR"), l.level).String()}, args...)
	l.Printer().Println(args...)
}
func (l *defaultLogger) Errorf(format string, args ...interface{}) {
	l.Printer().Printf(withFormat(withCallerMethod(withLevel(new(bytes.Buffer), "ERROR"), l.level), format).String(), args...)
}

func (l *defaultLogger) Printer() Printer {
	return l.writer
}

// add method caller name to message
func withCallerMethod(buff *bytes.Buffer, level int) *bytes.Buffer {
	buff.WriteString("m=")
	buff.WriteString(trace.GetCallerFunctionName(level))
	buff.WriteString(" ")
	return buff;
}

// adding level to message
func withLevel(buff *bytes.Buffer, lvl string) *bytes.Buffer {
	buff.WriteString(lvl)
	buff.WriteString(" ")
	return buff;
}

// adding format string to message
func withFormat(buff *bytes.Buffer, format string) *bytes.Buffer {
	buff.WriteString(format)
	return buff
}
