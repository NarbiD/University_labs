import threading
import time

SLEEP_TIME = 40000
THREAD_NUM = 100000


class Worker(threading.Thread):
    def run(self):
        time.sleep(20)


def main():
    for _ in range(THREAD_NUM):
        t = Worker()
        t.start()


main()
