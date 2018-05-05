package native

import (
	"log"
	"io"
)

//
// log using log package
//
type Printer struct {
	log *log.Logger
}

func(p *Printer) Printf(format string, args ...interface{}) {
	p.log.Printf(format, args...)
}

func(p *Printer) Println(args ...interface{}) {
	p.log.Println(args...)
}

func(p *Printer) SetFlags(flags int){
	p.log.SetFlags(flags)
}

func(p *Printer) SetOutput(out io.Writer){
	p.log.SetOutput(out)
}

func NewGologPrinter(out io.Writer, prefix string, flag int) *Printer {
	return &Printer{log: log.New(out, prefix, flag)}
}
