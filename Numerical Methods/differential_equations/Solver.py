from numpy import empty_like, empty, array


def Euler(f, x, y0):
    h = x[1] - x[0]
    y = empty_like(x)
    y[0] = y0
    for i in range(len(x)-1):
        y[i+1] = y[i] + h * f(x[i], y[i])
    return y


def f(system, x):
    return array([func(*x) for func in system])


def Runge_Kutta(system, x, y0):
        h = x[1] - x[0]
        y = empty((len(x), len(y0)))
        y[0] = y0
        for i in range(len(x)-1):
            k1 = h * f(system, [x[i], *y[i]])
            k2 = h * f(system, [x[i] + h / 2, *(y[i] + k1 / 2)])
            k3 = h * f(system, [x[i] + h / 2, *(y[i] + k2 / 2)])
            k4 = h * f(system, [x[i] + h, *(y[i] + k3)])
            y[i+1] = y[i] + (k1 + 2 * k2 + 2 * k3 + k4) / 6
        return y
