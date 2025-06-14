package s4jer

import (
	"embed"

	"github.com/starter-go/application"
	"github.com/starter-go/starter"
)

const (
	theModuleName     = "github.com/starter-go/starter4j/tools/s4jer"
	theModuleVersion  = "0.0.2"
	theModuleRevision = 6
)

////////////////////////////////////////////////////////////////////////////////

//go:embed "src/main/resources"
var theModuleResFS embed.FS

const theModuleResPath = "src/main/resources"

////////////////////////////////////////////////////////////////////////////////

func Module() application.Module {
	mb := &application.ModuleBuilder{}
	mb.Name(theModuleName)
	mb.Version(theModuleVersion)
	mb.Revision(theModuleRevision)

	allcom := &myComponents{}
	mb.Components(allcom.all)
	mb.EmbedResources(theModuleResFS, theModuleResPath)

	mb.Depend(starter.Module())

	return mb.Create()
}

////////////////////////////////////////////////////////////////////////////////

type myComponents struct {
}

func (inst *myComponents) all(r1 application.ComponentRegistry) error {
	r2 := r1.NewRegistration()
	r2.ID = "myRunnerHolder"
	r2.Scope = "singleton"
	r2.NewFunc = func() any {
		return &myRunnerHolder{}
	}
	r2.InjectFunc = func(c application.InjectionExt, instance any) error {
		o := instance.(*myRunnerHolder)
		o.ac = c.GetContext()
		o.fs = nil
		return nil
	}
	return r1.Register(r2)
}
