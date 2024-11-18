package main

import (
	"github.com/starter-go/starter"
	"github.com/starter-go/starter4j/tools/s4jer"
)

func main() {
	args := []string{}
	m := s4jer.Module()
	i := starter.Init(args)
	i.MainModule(m)
	i.WithPanic(true).Run()
}
