package util

import (
	"testing"
	"fmt"
	"strconv"
	"reflect"
)

func TestOfNullable_UsingDefaultValue(t *testing.T) {
	current := OfNullable(nil).OrElse(1).Get()
	assertEqual(t, 1, current, "Value must be not null")
}

func TestOfNullable_UsingPassedValue(t *testing.T) {
	current := OfNullable(3).OrElse(1).Get()
	assertEqual(t, 3, current, "Value must be not null")
}


func TestOfNullable_DefaultValueAndMapping(t *testing.T) {

	current := OfNullable(nil).OrElse(1).Map(func(o interface{}) interface{} {
		return strconv.Itoa(o.(int))
	}).Get()
	assertEqual(t, "1", current, "")
}


func assertEqual(t *testing.T, expected interface{}, current interface{}, message string) {
	if expected == current {
		return
	}
	t.Fatal(fmt.Sprintf("%v(%s) != %v(%s): %s", expected, reflect.TypeOf(expected), current, reflect.TypeOf(current), message))
}
