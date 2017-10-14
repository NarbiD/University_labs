from numpy import linspace, tan, array
from matplotlib import pyplot as plt
from Solver import Euler, Runge_Kutta

y0 = 0
dy = lambda x, y: x*x + y*y
domain = linspace(0, 0.5, 100)
codomain = Euler(dy, domain, y0)
y_ans = tan(domain) - domain

plt.plot(domain, codomain, domain, y_ans)
plt.legend(['Euler', 'answer'], loc='best')
plt.show()

system = [lambda t, x, y: 2/3*x - 4/3*x*y, lambda t, x, y: x*y - y]
t = linspace(0, 20, 500)
y0 = array([1, 2])
z = Runge_Kutta(system, t, y0)

plt.plot(t, z[:, 0], t, z[:, 1])
plt.legend(['prey', 'predator'], loc='best')
plt.show()
