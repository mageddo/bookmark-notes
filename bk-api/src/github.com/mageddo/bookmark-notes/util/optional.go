package util

type Optional interface {
	Map(func(o interface{}) interface{}) Optional
	OrElse(interface{}) Optional
	Get() interface{}
}

type OptionalImpl struct {
	value interface{}
}

func (op *OptionalImpl) Map(fn func(o interface{}) interface{}) Optional {
	op.value = fn(op.value)
	return op
}

func (op *OptionalImpl) OrElse(o interface{}) Optional {
	if op.value == nil {
		op.value = o
	}
	return op
}

func (op *OptionalImpl)Get() interface{} {
	return op.value
}

func OfNullable(o interface{}) Optional {
	return &OptionalImpl{o}
}
