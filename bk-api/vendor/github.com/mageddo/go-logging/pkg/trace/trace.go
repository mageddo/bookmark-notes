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

type stack struct {
	FilePath string
	FileName string
	Line int
	FullFuncName string
	PackageName string
	Funcname string
}

var rx, _ = regexp.Compile(`\.func\d+$`)
func GetCallerFunction(backLevel int) *stack {

	var pc, tryAgain = make([]uintptr, backLevel + 4), true
	runtime.Callers(0, pc) // pass skip did different results for different go version eg 1.7 and 1.9

	var fn *runtime.Func
	pc = pc[backLevel + 2:]
	i:=0
	for ; i < len(pc) && tryAgain; i++ {
		fn = runtime.FuncForPC(pc[i])
		tryAgain = rx.MatchString(fn.Name())
	}
	if index := strings.LastIndex(fn.Name(), "."); index != -1 {
		f, l := fn.FileLine(pc[i])
		return &stack{f, f[strings.LastIndex(f, "/")+1:], l,
		fn.Name(), fn.Name()[:index], fn.Name()[index + 1:],
		}
	}
	return nil
}

func GetCallerFunctionName(backLevel int) string {
	s := GetCallerFunction(backLevel + 1)
	if s == nil {
		return ""
	}
	return s.Funcname
}
