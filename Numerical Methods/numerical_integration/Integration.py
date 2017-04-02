from numpy import fabs, arange


class Integration:
    EPS = 1e-6

    area_domain = list()
    area_codomain = list()

    @staticmethod
    def rectangles(func, a, b, iterations=180):
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
        # plt.plot(x, y, 'bs', color='red', label='Rectangles')
        return current_area

    @staticmethod
    def trapezoids(func, a, b, iterations=150):
        delta_x = (b - a) / iterations
        x = [a + 0.5 * delta_x]
        y = [func(x[0])]
        current_area = 0.0
        for iteration in range(iterations):
            current_area += func(x[iteration])
            x.append(x[iteration] + delta_x)
            y.append(func(x[iteration + 1]))
        # plt.plot(x, y, 'g^', color='yellow', label='Rectangles')
        return delta_x * ((func(a) + func(b)) / 2 + current_area)

    @staticmethod
    def simpson(func, a, b, iterations=50):
        delta_x = (b - a) / iterations
        Integration.area_domain.extend(arange(a, b, delta_x))
        x = a
        current_area = func(a) + func(b)
        for iteration in range(iterations):
            x += delta_x
            if iteration % 2:
                current_area += 2.0 * func(x)
            else:
                current_area += 4.0 * func(x)
            Integration.area_codomain.append((delta_x / 3) * current_area)
        return (delta_x/3.0)*current_area
