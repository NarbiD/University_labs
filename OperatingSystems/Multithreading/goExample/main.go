package main

import "time"

func main() {
	for i :=0 ; i < 1500000; i++ {
		go run()
	}
	run()
}

func run () {
	for { time.Sleep(20 * time.Second) }
}
