from Function import Function
from Integration import Integration
from numpy import sin, pi
from matplotlib import pyplot as plt


func_f = Function(lambda x: x*sin(x))
func_f.build_plot()

print(Integration.rectangles(func_f.calculate, 0, pi))
print(Integration.trapezoids(func_f.calculate, 0, pi))
print(Integration.simpson(func_f.calculate, 0, pi))
plt.plot(Integration.area_domain, Integration.area_codomain, color='red', label="Area")
plt.legend(loc='upper left')
plt.show()
