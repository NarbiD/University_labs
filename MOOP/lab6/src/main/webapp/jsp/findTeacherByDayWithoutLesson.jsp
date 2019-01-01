<%--
  Created by IntelliJ IDEA.
  User: narbid
  Date: 20.12.18
  Time: 6:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Найти преподавателя без пар в указанный день</title>
</head>
<body>
<form method="post" action="/findTeacherByDayWithoutLesson">
    <input name="day" type="text" placeholder="day"/>
    <input type="submit" value="send"/>
</form>
</body>
</html>
