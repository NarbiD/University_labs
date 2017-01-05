# Лабораторна робота 2. Наближені обчислення

# Версія 1 (початкова умова) 
Функцію f(x), визначену на інтервалі (a; b), задано аналітичною формулою та нескінченною сумою. Програма має отримати декілька дійсних значень аргументу x із інтервалу й для кожного з них вивести:
- значення f(x), обчислене за формулою,
- значення f(x), обчислене за розкладом у суму степенів x, 
- різницю між цими значеннями, 
- кількість доданків у обчисленій скінченній сумі. 
Значення аргументу x задає користувач за допомогою клавіатури. Програма має ігнорувати недопустимі значення аргументу за межами інтервалу, повідомляючи про недопустимість і запрошуючи до повторного введення, поки користувач не введе допустиме значення. Запрошення до кожного введення повинно містити межі інтервалу (a; b), якому має належати аргумент. 
У відповідь на допустиме значення аргументу треба вивести два рядки. Перший містить заголовок, тобто позначення x, f_lib(x), f_sum(x), difference, N_adds. Другий – відповідні числа, розташовані під цими позначеннями та вирівняні з ними за правим краєм.
Робота закінчується, коли користувач замість того, щоб набрати наступне значення аргументу, введе будь-яке нечислове значення і потім Enter. 

Вимоги до виконання 

1.У супроводі до роботи записати розклад у суму степенів і систему рекурентних співвідношень, які виражають послідовні значення сум і доданків (можливо, їх чисельників, знаменників тощо). Система співвідношень має бути такою, щоб у програмі не було зайвих повторних обчислень тих самих величин. Якщо в співвідношеннях є дроби, чисельники й знаменники яких мають спільні множники, то необхідно скоротити їх. 

2.На початку програми записати прототипи всіх функцій, окрім головної, далі головну функцію, далі – решту функцій. Програма повинна мати, окрім головної, п’ять функцій: 
- введення аргументу x, 
- обчислення f(x) за аналітичним виразом, 
- обчислення f(x) за рекурентними співвідношеннями, 
- виведення рядка з заголовком, 
- виведення рядка з числами. 

3.Функція введення аргументу повертає ознаку того, що значення x від користувача отримано. 

4.Обчислити f(x) за аналітичним виразом, скориставшися бібліотечними математичними функціями. 

5.Обчислити f(x) за рекурентними співвідношенями як скінченну суму, утворену початковими доданками, модулі яких не менше 10–8. Функція повертає суму, а кількість доданків присвоюється її додатковому параметру-посиланню. У цих обчисленнях виклики бібліотечних математичних функцій, окрім fabs(), заборонені.

6.Функція виведення рядка з числами отримує їх як аргументи у виклику. 

# Версія 2 
Користувач задає x і крок h, де a < x < b, h > 0. Як і у версії 1, програма має ігнорувати недопустимі значення x за межами інтервалу (a; b), повідомляючи про недопустимість і запрошуючи до повторного введення, поки користувач не введе допустиме значення. Запрошення до кожного введення x і h повинно містити межі інтервалу (a; b), якому має належати аргумент. 
Програма починає з аргументу x і збільшує його з кроком h, поки не досягне кінця інтервалу b (значення f(b) не обчислювати!). Для кожного аргументу програма виводить рядок із п’ятьма числами, вказаними у версії 1. 
Результати виводяться «сторінками». Перший рядок сторінки є заголовком і містить позначення x, f_lib(x), f_sum(x), difference, N_adds. Наступні 10 рядків — по 5 відповідних чисел, вирівняних за правим краєм. 
Після виведення кожної «сторінки», окрім останньої, програма виводить запит, чи продовжувати, та підказку, що клавіша 'Q' означає закінчення обробки заданих значень x і h, а будь-яка інша – продовження. Остання «сторінка» може мати менше 10 рядків, але не менше одного. 
Робота закінчується, коли користувач замість того, щоб набрати наступне значення x, введе будь-яке нечислове значення і потім Enter. 

Вимоги до виконання 

1.Додати функцію введення кроку h. Якщо користувач задав значення h≤0, то ця функція повертає «стандартне» значення 10–6.

2.Додати функцію обробки заданих x і h: вона виводить «сторінки», зазначені вище, й викликає функцію, яка запитує щодо продовження та обробляє відповідь (див. наступний пункт).

3.Додати функцію, яка викликається після виведення «сторінки» результатів. Вона виводить запит до користувача, чи продовжувати виведення результатів, отримує відповідь (натискання на Esc або іншу клавішу), і повертає булеву ознаку продовження.

# Варіант 16: sqrt(1 + x)