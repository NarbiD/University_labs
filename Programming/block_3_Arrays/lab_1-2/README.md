# Лабораторна робота. Зміна масиву

## Версія 1 
Написати програму, яка заповнює масив цілих чисел в той або інший спосіб, обробляє його й виводить результати обробки. 
Програма виводить покажчик пронумерованих способів заповнення масиву й запрошення, щоб користувач указав спосіб заповнення масиву. 
Після вказівки користувача програма у відповідний спосіб заповнює масив (точніше, його робочу частину). 
Потім робоча частина масиву виводиться.
Далі вона обробляється й знову виводиться.

## Варіант 38: 
Обміняти місцями дві ділянки масиву від позиції k до позиції m та від позиції p до позиції s. Наприклад, якщо є масив 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 і k=3, m=5, p=10, s=13, то результатом обміну буде масив 0 1 2 10 11 12 13 6 7 8 9 3 4 5 14. Границі задає користувач. За недопустимих даних (k > m, або p > s, або ділянки мають спільні елементи) масив не обробляється. Можна скористатися додатковим масивом, а можна обійтися й без нього.


Вимоги до виконання 

1.У програмі мають бути головна функція та допоміжні: 
- обробки вказівки користувача щодо способу заповнення масиву, 
- заповнення початку масиву цілих псевдовипадковими числами, 
- заповнення за допомогою клавіатури, 
- виведення значень елементів масиву,
- обробки масиву, 
- можливо (це залежить від варіанту), функція введення додаткових величин. 

2.Реалізувати програму за допомогою проекту, який включає щонайменше три файли. У h-файлі заголовків записати прототипи функцій роботи з масивом, у відповідному cpp-файлі – самі ці функції, в головному файлі – головну функцію. 

3.Головна функція викликає функцію обробки вказівки користувача, потім, залежно від вказівки, закінчує роботу або викликає ту чи іншу функцію заповнення масиву, функції виведення та обробки. 

4.Функція обробки вказівки користувача виводить підказку про подальші дії: 1 – заповнити масив псевдовипадковими числами, 2 – ввести числа за допомогою клавіатури, 9 – закінчити роботу. Користувач вводить вказівку – ціле число. Функція повертає це число. 

5.Довжина масивів цілих N = 20. 

6.Параметри функцій уведення та заповнення випадковими значеннями – вказівник на перший елемент масиву, довжина масиву й довжина його робочої частини. 

7.Введення за допомогою клавіатури закінчується натисканням клавіш Ctrl-Z і Enter (для сумісності з іншими середовищами розробки, цю роль також виконує введення числа -1). Кількість елементів, які мають отримати значення, наперед не задається. Якщо всі елементи масиву отримали значення, введення припиняє не користувач, а програма. Перед уведенням кожного значення має з’являтися запрошення на введення, в якому вказано індекс елемента. 

8.Під час заповнення масиву псевдовипадковими числами кількість елементів запитується в користувача й має бути від 1 до N. Псевдовипадкові числа отримуються від бібліотечної функції та перетворюються до діапазону від 0 до 99. 

9.Обов’язкові параметри функції обробки масиву – вказівник на перший елемент масиву, довжина робочої частини масиву. Інші параметри – залежно від варіанту. 

10.Параметри функції виведення – вказівник на перший елемент масиву, довжина робочої частини масиву.

11.Значення елементів масиву виводити на екран по 5 на рядок. Останній рядок може бути неповним. Стовпчики вирівняти за правим краєм. 

## Версія 2 

Програма може отримувати значення елементів масиву з файлу й виводити результати в інший файл. Якщо задано введення з файлу, то програма запитує користувача ім’я файлу з даними для заповнення масиву. Незалежно від способу заповнення масиву, результати його роботи можуть виводитися на екран або у файл.

Вимоги до виконання 

1.Внести зміни у функцію обробки вказівки користувача щодо способу заповнення масиву. До покажчика подальших дій користувача додати ще один пункт: 3 – введення з файлу. Якщо користувач обрав цей пункт, потрібно запросити користувача ввести ім’я файлу у файловій системі. Якщо користувач задає порожнє ім’я, то дані вводяться з файлу з фіксованим іменем input.txt, розташованого в папці з програмою. Якщо введення закінчується невдачею, функція повертає ознаку невдачі. 
2.Внести зміни в головну функцію: якщо утворення даних у масиві закінчилося невдачею, його слід повторити. 
3.Реалізувати введення з файлу або від клавіатури. Додати окрему функцію введення з файлу.
4.Додати функцію обробки вказівки користувача щодо виведення даних. Вона виводить підказку про подальші дії: 1 – виведення на екран, 2 – виведення у файл. Користувач вводить вказівку – ціле число. Якщо користувач задав виведення у файл, то функція запитує ім’я файлу у файловій системі. Якщо користувач задає порожнє ім’я, то дані мають виводитися у файл з фіксованим іменем output.txt, розташований у папці з програмою. 
5.Додати функцію виведення у файл. 