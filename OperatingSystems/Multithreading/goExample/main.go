package main

import "time"

const THREAD_NUM = 1000000

const TIME_TO_SLEEP = 40 * time.Second

func main() {
	for i :=0 ; i < THREAD_NUM; i++ {
		go run()
	}
	run()
}

func run () {
	for { time.Sleep(TIME_TO_SLEEP) }
}
