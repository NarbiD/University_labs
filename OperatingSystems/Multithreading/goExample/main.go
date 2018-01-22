package main

import "math"

func main() {
	for i :=0 ; i < 2000000; i++ {
		go test()
	}
	for { math.Sqrt(1) }
}

func test () {
	for { math.Sqrt(1) }
}