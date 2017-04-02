from numpy import arange, pi
import matplotlib.pyplot as plt


class Function:
    domain = arange(0, pi, 0.1)
    codomain = list()

    def __init__(self, func):
        self.calculate = func

    def build_plot(self):
        self.codomain = [self.calculate(x) for x in self.domain]
        plt.plot(self.domain, self.codomain, linewidth=2.0, label="Original function")
