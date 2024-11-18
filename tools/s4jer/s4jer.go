package s4jer

import (
	"bytes"
	"crypto/sha256"
	"fmt"
	"io/fs"
	"os"
	"sort"
	"strconv"
	"strings"
	"text/template"

	"github.com/starter-go/afs"
	"github.com/starter-go/application"
	"github.com/starter-go/application/properties"
	"github.com/starter-go/base/lang"
	"github.com/starter-go/vlog"
)

////////////////////////////////////////////////////////////////////////////////

type myContext struct {
	ac         application.Context
	wd         afs.Path
	configFile afs.Path // the file named 's4jer.config'

	resJavaFileNamePrefix string
	resJavaFileNameSuffix string

	trees     []*myResourceTree
	templates map[string]string // 缓存的模板 : map[name]text
}

func (inst *myContext) loadTemplate(name string) (string, error) {

	// try get in cache
	text := inst.templates[name]
	if text != "" {
		return text, nil
	}

	// do load
	res := inst.ac.GetResources()
	text, err := res.ReadText(name)
	if err != nil {
		return "", err
	}

	// store
	inst.templates[name] = text
	return text, nil
}

////////////////////////////////////////////////////////////////////////////////

type myResourceTree struct {
	context    *myContext
	folderSrc  afs.Path
	folderDst  afs.Path
	folderCode afs.Path
	folderRes  afs.Path

	targetPackage string
	items         []*myResourceFile
}

////////////////////////////////////////////////////////////////////////////////

type myResourceFile struct {
	context         *myContext
	tree            *myResourceTree
	fileSrc         afs.Path
	fileDst         afs.Path
	simpleClassName string
	shortResPath    string
	hash            lang.Hex
	size            int // 资源原始数据的大小
}

////////////////////////////////////////////////////////////////////////////////

type myEmbedResJavaFileBuilder struct {
	meta       *myResourceFile
	data       []byte
	code       string // 生成的 java 代码
	list1token string
}

func (inst *myEmbedResJavaFileBuilder) create() error {
	steps := make([]func() error, 0)

	steps = append(steps, inst.doLoadRawData)
	steps = append(steps, inst.doComputeSHA256Sum)
	steps = append(steps, inst.doPrepareNames)
	steps = append(steps, inst.doGenCode)
	steps = append(steps, inst.doWriteToFile)

	for _, step := range steps {
		err := step()
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myEmbedResJavaFileBuilder) doLoadRawData() error {
	src := inst.meta.fileSrc
	data, err := src.GetIO().ReadBinary(nil)
	if err != nil {
		return err
	}
	inst.data = data
	return nil
}

func (inst *myEmbedResJavaFileBuilder) doComputeSHA256Sum() error {

	at := inst.meta.shortResPath
	size := len(inst.data)
	head := strconv.Itoa(size) + "/" + at

	b := &bytes.Buffer{}
	b.WriteString(head)
	b.WriteByte(0)
	b.Write(inst.data)

	sum := sha256.Sum256(b.Bytes())
	hex := lang.HexFromBytes(sum[:])
	inst.meta.hash = hex
	inst.meta.size = size
	return nil
}

func (inst *myEmbedResJavaFileBuilder) doPrepareNames() error {

	ctx := inst.meta.context
	prefix := ctx.resJavaFileNamePrefix
	suffix := ctx.resJavaFileNameSuffix
	longHex := inst.meta.hash.String()
	shortHex := longHex[0:7]
	dir2 := inst.meta.tree.folderDst

	simpleName := prefix + shortHex
	fileName := prefix + shortHex + suffix
	file2 := dir2.GetChild(fileName)

	inst.meta.simpleClassName = simpleName
	inst.meta.fileDst = file2
	return nil
}

func (inst *myEmbedResJavaFileBuilder) doGenCode() error {

	out := &strings.Builder{}
	params := make(map[string]string)
	templateName := "templates/res-java-template.txt"
	templateText := ""
	ctx := inst.meta.context

	inst.loadParams(params)

	templateText, err := ctx.loadTemplate(templateName)
	if err != nil {
		return nil
	}

	templateInst, err := template.New(templateName).Parse(templateText)
	if err != nil {
		return nil
	}

	err = templateInst.Execute(out, params)
	if err != nil {
		return nil
	}

	code := out.String()
	code = inst.injectDataListToCode(code)
	inst.code = code
	return nil
}

func (inst *myEmbedResJavaFileBuilder) injectDataListToCode(code string) string {
	const (
		stringMark     = '"'
		space          = "    "
		maxBytesPerRow = 25
	)
	b := &strings.Builder{}
	data := inst.data
	size := len(data)

	// make list
	b.WriteRune('\n')
	offset := 0
	for offset < size {
		cb := size - offset
		if maxBytesPerRow < cb {
			cb = maxBytesPerRow
		}
		hex := lang.HexFromBytes(data[offset : offset+cb])
		b.WriteString(space)
		b.WriteString(space)
		b.WriteString("out.append(")
		b.WriteRune(stringMark)
		b.WriteString(hex.String())
		b.WriteRune(stringMark)
		b.WriteString(");\n")
		offset += cb
	}

	// replace
	str1 := inst.list1token
	str2 := b.String()
	return strings.Replace(code, str1, str2, 1)
}

func (inst *myEmbedResJavaFileBuilder) loadParams(p map[string]string) {

	const (
		keyPackage = "package_name"
		keyClass   = "class_name" // class-simple-name
		keySize    = "data_size"
		keyPath    = "res_path"
		keyList1   = "for_list_1"
	)

	size := inst.meta.size
	hash := inst.meta.hash

	strList1 := "token_" + hash.String()
	inst.list1token = strList1

	strPackage := inst.meta.tree.targetPackage
	strClass := inst.meta.simpleClassName
	strPath := inst.meta.shortResPath
	strSize := strconv.Itoa(size)

	p[keyPackage] = strPackage
	p[keyClass] = strClass
	p[keyPath] = strPath
	p[keySize] = strSize
	p[keyList1] = strList1
}

func (inst *myEmbedResJavaFileBuilder) doWriteToFile() error {
	file := inst.meta.fileDst
	code := inst.code
	opt := &afs.Options{
		Flag:       os.O_WRONLY | os.O_CREATE,
		Permission: fs.ModePerm,
	}
	return file.GetIO().WriteText(code, opt)
}

////////////////////////////////////////////////////////////////////////////////

type myMainResJavaFileBuilder struct {
	tree         *myResourceTree
	code         string // 生成的 java 代码
	list1token   string
	mainResFile  afs.Path // MainRes.java
	mainResClass string   // MainRes
}

func (inst *myMainResJavaFileBuilder) create() error {

	steps := make([]func() error, 0)

	steps = append(steps, inst.doPrepareNames)
	steps = append(steps, inst.doGenCode)
	steps = append(steps, inst.doWriteToFile)

	for _, step := range steps {
		err := step()
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myMainResJavaFileBuilder) doPrepareNames() error {

	ctx := inst.tree.context
	prefix := ctx.resJavaFileNamePrefix
	suffix := ctx.resJavaFileNameSuffix
	dir := inst.tree.folderDst

	simpleClassName := prefix + "Main"
	fileName := simpleClassName + suffix

	inst.mainResClass = simpleClassName
	inst.mainResFile = dir.GetChild(fileName)
	return nil
}

func (inst *myMainResJavaFileBuilder) doGenCode() error {

	out := &strings.Builder{}
	params := make(map[string]string)
	templateName := "templates/main-java-template.txt"
	templateText := ""
	ctx := inst.tree.context

	inst.loadParams(params)

	templateText, err := ctx.loadTemplate(templateName)
	if err != nil {
		return err
	}

	templateInst, err := template.New(templateName).Parse(templateText)
	if err != nil {
		return err
	}

	err = templateInst.Execute(out, params)
	if err != nil {
		return err
	}

	code := out.String()
	code = inst.injectDataListToCode(code)
	inst.code = code
	return nil
}

func (inst *myMainResJavaFileBuilder) loadParams(p map[string]string) {
	const (
		keyPackage = "package_name"
		keyClass   = "class_name" // class-simple-name
		keyList1   = "for_list_1"
	)

	strList1 := "token_585c9fd39698702b" // a magic number
	strPackage := inst.tree.targetPackage
	strClass := inst.mainResClass

	inst.list1token = strList1

	p[keyPackage] = strPackage
	p[keyClass] = strClass
	p[keyList1] = strList1
}

func (inst *myMainResJavaFileBuilder) injectDataListToCode(code string) string {
	const (
		space = "    "
	)
	items := inst.tree.items
	b := &strings.Builder{}
	b.WriteRune('\n')
	// make list
	for _, item := range items {
		simpleClassName := item.simpleClassName
		b.WriteString(space)
		b.WriteString(space)
		b.WriteString("list.add(new ")
		b.WriteString(simpleClassName)
		b.WriteString("());\n")
	}
	// replace
	str1 := inst.list1token
	str2 := b.String()
	return strings.Replace(code, str1, str2, 1)
}

func (inst *myMainResJavaFileBuilder) doWriteToFile() error {
	file := inst.mainResFile
	code := inst.code
	opt := &afs.Options{
		Flag:       os.O_WRONLY | os.O_CREATE,
		Permission: fs.ModePerm,
	}
	return file.GetIO().WriteText(code, opt)
}

////////////////////////////////////////////////////////////////////////////////

type myTreeScanner struct {
	handler func(file afs.Path, shortPath string) error
}

func (inst *myTreeScanner) scan(dir afs.Path) error {
	return inst.scanIntoDir(dir, "", 0)
}

func (inst *myTreeScanner) scanIntoDir(dir afs.Path, shortPath string, depth int) error {
	if !dir.IsDirectory() {
		return nil
	}
	namelist := dir.ListNames()
	sort.Strings(namelist)
	for _, name := range namelist {
		var err error
		child := dir.GetChild(name)
		path2 := shortPath + "/" + name
		if child.IsFile() {
			err = inst.onFile(child, path2)
		} else if child.IsDirectory() {
			err = inst.scanIntoDir(child, path2, depth+1)
		}
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myTreeScanner) onFile(file afs.Path, shortPath string) error {
	h := inst.handler
	if h == nil {
		return nil
	}
	return h(file, shortPath)
}

////////////////////////////////////////////////////////////////////////////////

type myRunner struct {
	fs afs.FS
	ac application.Context
}

func (inst *myRunner) run() error {
	ctx := &myContext{}
	steps := make([]func(ctx *myContext) error, 0)

	steps = append(steps, inst.doInit)
	steps = append(steps, inst.doLocateConfigFile)
	steps = append(steps, inst.doLoadConfigFile)
	steps = append(steps, inst.doForEachTree)
	steps = append(steps, inst.doFinish)

	for _, step := range steps {
		err := step(ctx)
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myRunner) handleTree(tree *myResourceTree) error {
	ctx := tree.context
	steps := make([]func(ctx *myContext, tree *myResourceTree) error, 0)

	steps = append(steps, inst.doTree)
	steps = append(steps, inst.doTreeClean)
	steps = append(steps, inst.doTreeScan)
	steps = append(steps, inst.doTreeMakeResJavaFiles)
	steps = append(steps, inst.doTreeMakeMainJavaFile)

	for _, step := range steps {
		err := step(ctx, tree)
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myRunner) doTree(ctx *myContext, tree *myResourceTree) error {
	return nil
}

func (inst *myRunner) doTreeClean(ctx *myContext, tree *myResourceTree) error {
	dir := tree.folderDst
	prefix := ctx.resJavaFileNamePrefix
	suffix := ctx.resJavaFileNameSuffix
	if !dir.IsDirectory() {
		return nil
	}
	if prefix == "" || suffix == "" {
		return fmt.Errorf("the embed-res.java file name.(prefix|suffix) is empty")
	}
	namelist := dir.ListNames()
	for _, name := range namelist {
		if strings.HasPrefix(name, prefix) && strings.HasSuffix(name, suffix) {
			file := dir.GetChild(name)
			if !file.IsFile() {
				continue
			}
			err := file.Delete()
			if err != nil {
				return err
			}
		}
	}
	return nil
}

func (inst *myRunner) doTreeScan(ctx *myContext, tree *myResourceTree) error {
	scanner := &myTreeScanner{}
	scanner.handler = func(file afs.Path, shortPath string) error {
		resfile := &myResourceFile{
			context:      ctx,
			tree:         tree,
			fileSrc:      file,
			fileDst:      nil,
			shortResPath: shortPath,
		}
		tree.items = append(tree.items, resfile)
		return nil
	}
	return scanner.scan(tree.folderRes)
}

func (inst *myRunner) doTreeMakeMainJavaFile(ctx *myContext, tree *myResourceTree) error {
	builder := &myMainResJavaFileBuilder{tree: tree}
	return builder.create()
}

func (inst *myRunner) doTreeMakeResJavaFiles(ctx *myContext, tree *myResourceTree) error {
	items := tree.items
	for _, item := range items {
		builder := &myEmbedResJavaFileBuilder{
			meta: item,
		}
		err := builder.create()
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myRunner) doInit(ctx *myContext) error {
	wd, err := os.Getwd()
	if err != nil {
		return err
	}
	ctx.resJavaFileNamePrefix = "EmbeddedRes"
	ctx.resJavaFileNameSuffix = ".java"
	ctx.wd = inst.fs.NewPath(wd)
	ctx.templates = make(map[string]string)
	ctx.ac = inst.ac
	return nil
}

func (inst *myRunner) doLocateConfigFile(ctx *myContext) error {
	const want = "s4jer.config"
	pos1 := ctx.wd
	pos2 := pos1
	for ttl := 32; ttl > 0; ttl-- {
		if pos2 == nil {
			break
		}
		file := pos2.GetChild(want)
		if file.IsFile() {
			ctx.configFile = file
			return nil
		}
		pos2 = pos2.GetParent()
	}
	const msg = "cannot find config file named '%s' at path '%s'"
	return fmt.Errorf(msg, want, pos1.String())
}

func (inst *myRunner) doLoadConfigFile(ctx *myContext) error {
	file := ctx.configFile
	text, err := file.GetIO().ReadText(nil)
	if err != nil {
		return err
	}
	ptab, err := properties.Parse(text, nil)
	if err != nil {
		return err
	}
	getter := ptab.Getter()
	treeNameList := getter.GetStringList("s4jer.trees")
	for _, name := range treeNameList {
		tree, err := inst.loadTree(ctx, name, getter)
		if err != nil {
			return err
		}
		ctx.trees = append(ctx.trees, tree)
	}
	return nil
}

func (inst *myRunner) loadTree(ctx *myContext, name string, ptab properties.Getter) (*myResourceTree, error) {
	prefix := "tree." + name + "."
	pkg := ptab.GetString(prefix + "package")
	codePath := ptab.GetString(prefix + "code")
	resPath := ptab.GetString(prefix + "res")
	path2pkg := strings.ReplaceAll(pkg, ".", "/")
	// dirs
	projectDir := ctx.configFile.GetParent()
	resDir := projectDir.GetChild(resPath)
	codeDir := projectDir.GetChild(codePath)
	// tree
	tree := &myResourceTree{
		context: ctx,
	}
	tree.folderDst = codeDir.GetChild(path2pkg)
	tree.folderSrc = resDir
	tree.folderRes = resDir
	tree.folderCode = codeDir
	tree.targetPackage = pkg
	return tree, nil
}

func (inst *myRunner) doForEachTree(ctx *myContext) error {
	for _, tree := range ctx.trees {
		err := inst.handleTree(tree)
		if err != nil {
			return err
		}
	}
	return nil
}

func (inst *myRunner) doFinish(ctx *myContext) error {
	vlog.Info("done")
	return nil
}

////////////////////////////////////////////////////////////////////////////////

type myRunnerHolder struct {
	fs afs.FS
	ac application.Context
}

func (inst *myRunnerHolder) _impl() application.Lifecycle {
	return inst
}

func (inst *myRunnerHolder) Life() *application.Life {
	l := &application.Life{}
	l.OnCreate = inst.create
	l.OnLoop = inst.run
	return l
}

func (inst *myRunnerHolder) create() error {
	// vlog.Warn("todo: impl myRunnerHolder.create")

	const id = "com-753b8c77262f4ce1-fs-AFSProxy"
	o, err := inst.ac.GetComponent(id)
	if err != nil {
		return err
	}

	fs := o.(afs.FS)
	inst.fs = fs

	return nil
}

func (inst *myRunnerHolder) run() error {
	// vlog.Warn("todo: impl myRunnerHolder.run")
	runner := &myRunner{
		ac: inst.ac,
		fs: inst.fs,
	}
	return runner.run()
}

////////////////////////////////////////////////////////////////////////////////
