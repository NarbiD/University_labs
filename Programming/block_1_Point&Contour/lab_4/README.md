# Лабораторна робота 1-1. Точка та контур

# Версія 4 («багато контурів – багато точок») 
Умова: 
Як і у версії 3, задається контур і декілька точок, проте після повторення точки робота не закінчується. Вводиться значення параметра, яке визначає наступний контур, і все повторюється. На відміну від версії 3, введення точки може не бути успішним – тоді припиняється робота з поточним контуром. Умови закінчення всієї роботи такі самі, як у версії 3. 

1. Перероблена функція введення точки getPoint – вона повертає ознаку того, що точку успішно введено. 
2. Перероблена функція обробки контуру – в разі неуспішного введення точки вона виводить повідомлення <<<POINT IS ABSENT>>> і закінчує обробку поточного контуру.
3. Після відмови користувача від уведення контуру (наприклад, вводу нечислового значення) на екрані з’являються тільки повідомлення <<<CONTOUR IS ABSENT>>> і  <<<END OF WORK>>>, і ніяких інших. 