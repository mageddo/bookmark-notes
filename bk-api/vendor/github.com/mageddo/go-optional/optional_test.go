package optional

import (
	"testing"
	"fmt"
	"strconv"
	"reflect"
)

func TestOfNullable_UsingDefaultValue(t *testing.T) {
	current := OfNullable(nil).OrElse(1)
	assertEqual(t, 1, current, "Value must be not null")
}

func TestOfNullable_UsingNilAsDefaultValue(t *testing.T) {
	var n *int = nil
	current := OfNullable(n)
	assertEqual(t, false, current.IsPresent(), "Must be not present")
	assertEqual(t, 1, current.OrElse(1), "Value must be not null")
}

func TestOfNullable_UsingPassedValue(t *testing.T) {
	current := OfNullable(3).OrElse(1)
	assertEqual(t, 3, current, "Value must be not null")
}


func TestOfNullable_DefaultValueAndMapping(t *testing.T) {

	current := OfNullable(nil).Map(func(o interface{}) interface{} {
		return strconv.Itoa(o.(int))
	}).OrElse("2")
	assertEqual(t, "2", current, "")
}

func TestOfNullable_PassingNotNullValueAndMapping(t *testing.T) {

	current := OfNullable(3).Map(func(o interface{}) interface{} {
		return strconv.Itoa(o.(int))
	}).OrElse("2")
	assertEqual(t, "3", current, "")
}


func assertEqual(t *testing.T, expected interface{}, current interface{}, message string) {
	if expected == current {
		return
	}
	t.Fatal(fmt.Sprintf("%v(%s) != %v(%s): %s", expected, reflect.TypeOf(expected), current, reflect.TypeOf(current), message))
}
