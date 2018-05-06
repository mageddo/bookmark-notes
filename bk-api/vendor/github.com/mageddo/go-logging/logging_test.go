package logging

import (
	"testing"
	"bytes"
	"log"
	"github.com/mageddo/go-logging/native"
	"os"
	"errors"
	"bufio"
	"strings"
)

func TestDebug(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Debug("name=", "elvis")

	expected := "DEBUG f=logging_test.go:14 pkg=github.com/mageddo/go-logging m=TestDebug  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestDebugf(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Debugf("name=%v", "elvis")

	expected := "DEBUG f=logging_test.go:26 pkg=github.com/mageddo/go-logging m=TestDebugf name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestInfo(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Info("name=", "elvis")

	expected := "INFO f=logging_test.go:38 pkg=github.com/mageddo/go-logging m=TestInfo  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestInfof(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Infof("name=%v", "elvis")

	expected := "INFO f=logging_test.go:50 pkg=github.com/mageddo/go-logging m=TestInfof name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestWarn(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Warning("name=", "elvis")

	expected := "WARNING f=logging_test.go:62 pkg=github.com/mageddo/go-logging m=TestWarn  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestWarnf(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Warningf("name=%v", "elvis")

	expected := "WARNING f=logging_test.go:74 pkg=github.com/mageddo/go-logging m=TestWarnf name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestError(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Error("name=", "elvis")

	expected := "ERROR f=logging_test.go:86 pkg=github.com/mageddo/go-logging m=TestError  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestErrorf(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Errorf("name=%v", "elvis")

	expected := "ERROR f=logging_test.go:98 pkg=github.com/mageddo/go-logging m=TestErrorf name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestErrorShouldLogStackTrace(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Error("name=", "elvis", errors.New("an error!"))

	expected := "ERROR f=logging_test.go:110 pkg=github.com/mageddo/go-logging m=TestErrorShouldLogStackTrace  name= elvis "
	firstLine, _, _ := bufio.NewReader(bytes.NewReader(buff.Bytes())).ReadLine()

	if actual := string(firstLine); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
	if strings.Index(buff.String(), "created by testing.(") == -1 {
		t.Error("Output mus have stacktrace! " + buff.String())
	}
}

func TestErrorfShouldLogStackTrace(t *testing.T){

	buff := new(bytes.Buffer)
	logger := NewNoFlagInstance(buff)
	logger.Errorf("name=%s", "elvis", errors.New("an error!"))

	expected := "ERROR f=logging_test.go:127 pkg=github.com/mageddo/go-logging m=TestErrorfShouldLogStackTrace name=elvis"
	firstLine, _, _ := bufio.NewReader(bytes.NewReader(buff.Bytes())).ReadLine()

	if actual := string(firstLine); actual != expected {
		log.Println(buff)
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
	if strings.Index(buff.String(), "created by testing.(") == -1 {
		log.Println(buff)
		t.Error("Output must have stacktrace! " + buff.String())
	}
}

func NewNoFlagInstance(buff *bytes.Buffer) Log {
	return New(native.NewGologPrinter(buff, "", 0));
}

//
// static methods test
//
func TestStaticDebug(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Debug("name=", "elvis")

	expected := "DEBUG f=logging_test.go:153 pkg=github.com/mageddo/go-logging m=TestStaticDebug  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticDebugf(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Debugf("name=%v", "elvis")

	expected := "DEBUG f=logging_test.go:165 pkg=github.com/mageddo/go-logging m=TestStaticDebugf name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected=%s, actual=%s", expected, actual)
	}
}

func TestStaticInfo(t *testing.T){
	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Info("name=", "elvis")

	expected := "INFO f=logging_test.go:177 pkg=github.com/mageddo/go-logging m=TestStaticInfo  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticInfof(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Infof("name=%v", "elvis")

	expected := "INFO f=logging_test.go:188 pkg=github.com/mageddo/go-logging m=TestStaticInfof name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticWarn(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Warning("name=", "elvis")

	expected := "WARNING f=logging_test.go:200 pkg=github.com/mageddo/go-logging m=TestStaticWarn  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expcted='%q', actual='%q'", expected, actual)
	}
}

func TestStaticWarnf(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Warningf("name=%v", "elvis")

	expected := "WARNING f=logging_test.go:212 pkg=github.com/mageddo/go-logging m=TestStaticWarnf name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticError(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Error("name=", "elvis")

	expected := "ERROR f=logging_test.go:224 pkg=github.com/mageddo/go-logging m=TestStaticError  name= elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticErrorf(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Errorf("name=%v", "elvis")

	expected := "ERROR f=logging_test.go:236 pkg=github.com/mageddo/go-logging m=TestStaticErrorf name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticErrorfUnknowFuncName(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	func(){
		func() {
			func() {
				func() {
					func() {
						func() {
							func() {
								func() {
									func() {
										Errorf("name=%v", "elvis")
									}()
								}()
							}()
						}()
					}()
				}()
			}()
		}()

	}()

	expected := "ERROR f=logging_test.go:262 pkg=github.com/mageddo/go-logging.TestStaticErrorfUnknowFuncName.func1.1.1.1.1.1.1.1 m=1 name=elvis\n"
	if actual := buff.String(); actual != expected {
		t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
	}
}

func TestStaticErrorfWithCallback(t *testing.T){

	buff := new(bytes.Buffer)
	setupStaticLoggerForTests(buff)
	Errorf("name=%v", "elvis")

	func(){
		expected := "ERROR f=logging_test.go:248 pkg=github.com/mageddo/go-logging m=TestStaticErrorfWithCallback name=elvis\n"
		if actual := buff.String(); actual != expected {
			t.Errorf("log format not expected, expected='%q', actual='%q'", expected, actual)
		}
	}()
}

func setupStaticLoggerForTests(buff *bytes.Buffer)  {
	logger := GetLog()
	printer := logger.Printer().(*native.Printer)
	printer.SetFlags(0)
	printer.SetOutput(buff)
}

func ExampleDebugf() {
	printer := GetLog().Printer().(*native.Printer)
	printer.SetOutput(os.Stdout)
	printer.SetFlags(0)

	Debugf("name=%q, age=%d", "John\nZucky", 21)

	// Output:
	// DEBUG f=logging_test.go:269 pkg=github.com/mageddo/go-logging m=ExampleDebugf name="John\nZucky", age=21
}

func BenchmarkDebugf(b *testing.B) {

	//go pprof.Lookup("block").WriteTo(os.Stdout, 2)
	//f, err := os.Open("./cpu.prof")
	//fmt.Println(err)
	//pprof.StartCPUProfile(f)
	//defer pprof.StopCPUProfile()
	GetLog().Printer().SetOutput(new(bytes.Buffer))
	log.SetOutput(new(bytes.Buffer))
	for i:=0; i < b.N; i++ {
		Debugf("i=%d", i)
		//log.Printf("i=%d", i)
	}
}

