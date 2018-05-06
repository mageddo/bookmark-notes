package trace

import (
	"runtime"
	"strings"
	"regexp"
)

//
// Return the caller function name
// 0 -> returns the current caller function
// 1 -> returns the current caller parent
// etc.
//
var rx, _ = regexp.Compile(`\.func\d+$`)
func GetCallerFunctionName(backLevel int) string {

	var pc, tryAgain = make([]uintptr, backLevel + 4), true
	runtime.Callers(0, pc) // pass skip did different results for different go version eg 1.7 and 1.9

	var fn *runtime.Func
	pc = pc[backLevel + 2:]
	for i:=0; i < len(pc) && tryAgain; i++ {
		fn = runtime.FuncForPC(pc[i])
		tryAgain = rx.MatchString(fn.Name())
	}
	if index := strings.LastIndex(fn.Name(), "."); index != -1 {
		return fn.Name()[index + 1:]
	}
	return ""
}