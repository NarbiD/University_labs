from numpy import fabs, arange, sin, pi
import matplotlib.pyplot as plt


class Function:
    domain = arange(0, pi, 0.1)
    codomain = list()
    calculate = None

    def __init__(self, func):
        self.calculate = func

    def build_plot(self):
        self.codomain = [self.calculate(x) for x in self.domain]
        plt.plot(self.domain, self.codomain, linewidth=2.0, label="Original function")


class Integration:
    EPS = 1e-6

    @staticmethod
    def rectangles(func, a, b, iterations=100):
        delta_x = (b - a) / iterations
        x = [a + 0.5 * delta_x]
        y = [func(x[0])]
        current_area = 0.0
        for iteration in range(iterations):
            prev_area = current_area
            current_area += func(x[iteration]) * delta_x
            if fabs(current_area - prev_area) < Integration.EPS:
                break
            x.append(x[iteration] + delta_x)
            y.append(func(x[iteration+1]))
        plt.plot(x, y, 'bs', color='red', label='Rectangles')
        return current_area

    @staticmethod
    def trapezoids(func, a, b, iterations=200):
        delta_x = (b - a) / iterations
        x = [a + 0.5 * delta_x]
        y = [func(x[0])]
        current_area = 0.0
        for iteration in range(0, iterations):
            current_area += func(x[iteration])
            x.append(x[iteration] + delta_x)
            y.append(func(x[iteration + 1]))
        plt.plot(x, y, 'g^', color='yellow', label='Rectangles')
        return delta_x * ((func(a) + func(b)) / 2 + current_area)

    @staticmethod
    def simpson(func, a, b, iterations=200):
        delta_x = (b - a) / iterations
        x = a
        current_area = func(a) + func(b)
        for iteration in range(1, iterations):
            x += delta_x
            if iteration % 2:
                current_area += 2.0 * func(x)
            else:
                current_area += 4.0 * func(x)
        return (delta_x/3.0)*current_area


func_f = Function(lambda x: sin(x))
func_f.build_plot()

print(Integration.rectangles(func_f.calculate, 0, pi))
print(Integration.trapezoids(func_f.calculate, 0, pi))
print(Integration.simpson(func_f.calculate, 0, pi))
plt.legend()
plt.show()
