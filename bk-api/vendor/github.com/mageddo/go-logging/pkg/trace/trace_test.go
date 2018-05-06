package trace

import (
	"testing"
)

func TestGetCallerFunctionName(t *testing.T){
	result := GetCallerFunction(0)
	expected := "TestGetCallerFunctionName"
	if result != expected {
		t.Errorf("actual=%q, expected=%q", result, expected)
	}
}

func TestGetCallerFunctionNameInsideLambda(t *testing.T) {
	func(){
		result := GetCallerFunction(0)
		expected := "TestGetCallerFunctionNameInsideLambda"
		if result != expected {
			t.Errorf("actual=%q, expected=%q", result, expected)
		}
	}()
}

func BenchmarkGetCallerFunctionName(t *testing.B){
	for i:=0; i < t.N; i++ {
		result := GetCallerFunction(0)
		expected := "BenchmarkGetCallerFunctionName"
		if result != expected {
			t.Errorf("actual=%q, expected=%q", result, expected)
		}
	}
}