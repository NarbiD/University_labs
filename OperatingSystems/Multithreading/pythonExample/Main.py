import threading
import time


class Worker(threading.Thread):
    def __init__(self, group=None, target=None, name=None, args=(), kwargs=None, *, daemon=None):
        super().__init__(group, target, name, args, kwargs, daemon=daemon)

    def run(self):
        print("b")
        time.sleep(20)
        print("I am sleeper")


def main():
    for _ in range(100000):
        t = Worker()
        t.start()


main()
