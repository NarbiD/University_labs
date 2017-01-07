import random

random.seed(version=3)
file = open('..\\resources\\input.txt', 'w')

number_of_digits = int(input('Enter number of digits: '))

numbers_left = number_of_digits
while numbers_left > 0:
    numbers_left -= 1
    file.write('\t' + str(random.randint(0, 99)))
    if numbers_left % 5 == number_of_digits % 5:
        file.write('\n')

file.close()
print('Done!')
