from math import fabs


def function_f(x):
    return x*x*x + 3.0*x - 2.0


class Integration:
    EPS = 1e-6

    @staticmethod
    def rectangles(a, b, iterations=100):
        delta_x = (b - a) / iterations
        x = a + 0.5 * delta_x
        current_area = 0.0
        for iteration in range(iterations):
            prev_area = current_area
            current_area += function_f(x) * delta_x
            if fabs(current_area - prev_area) < Integration.EPS:
                break
            x += delta_x
        return current_area

    @staticmethod
    def trapezoids(a, b, iterations=200):
        delta_x = (b - a) / iterations
        current_area = 0.0
        for iteration in range(1, iterations):
            x = a + iteration * delta_x
            current_area += function_f(x)
        return delta_x * ((function_f(a) + function_f(b)) / 2 + current_area)

    @staticmethod
    def simpson(a, b, iterations=200):
        delta_x = (b - a) / iterations
        x = a
        current_area = function_f(a) + function_f(b)
        for iteration in range(1, iterations):
            x += delta_x
            if iteration % 2:
                current_area += 2.0 * function_f(x)
            else:
                current_area += 4.0 * function_f(x)
        return (delta_x/3.0)*current_area


print(Integration.rectangles(0, 1))
print(Integration.trapezoids(0, 1))
print(Integration.simpson(0, 1))
