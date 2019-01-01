
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Найти преподавателя по дню и аудитории</title>
</head>
<body>
<form method="post" action="/findTeacherByDayAndRoom">
    <input name="day" type="text" placeholder="day"/>
    <input name="room" type="text" placeholder="room"/>
    <input type="submit" value="send"/>
</form>
</body>
</html>
