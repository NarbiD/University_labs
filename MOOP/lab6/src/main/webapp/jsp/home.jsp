<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 14.03.2018
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--Цвет текста берем из значения куки color --%>

<form method="post" action="/home">
    <label for="query">
        <select name="query" id="query">
            <option value="findTeacherByDayAndRoom">Найти преподавателя по дню и аудитории</option>
            <option value="findTeacherByDayWithoutLesson">Найти преподавателя, без пар в указанный день</option>
            <option value="findRoomByDay">Найти занятые аудитории</option>
        </select>
    </label>
    <input type="submit" value="send">
</form>
<hr>

<form method="post" action="/add">
    <label for="add">
        <select name="add" id="add">
            <option value="teacher">Добавить преподавателя</option>
            <option value="subject">Добавить предмет</option>
            <option value="room">Добавить аудиторию</option>
        </select>
        <input name="name" type="text">
        <input type="submit" value="send">
    </label>
</form>

<form method="post" action="/addLesson">
    <label>
        <input name="subject" placeholder="Предмет" type="text">
        <input name="teacher" placeholder="Предодаватель" type="text">
        <input name="room" placeholder="Аудитория" type="text">
        <input name="day" placeholder="День" type="text">
    </label>
    <input type="submit" value="send">
</form>
</body>
</html>
