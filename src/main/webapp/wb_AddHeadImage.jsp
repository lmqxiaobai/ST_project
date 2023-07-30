<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/7/12
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>上传个人头像</title>
</head>
<body>
<h1>上传个人头像</h1>
<form action="/ST_project/st_addHeadImageServlet?user_id=${user.user_id}" method="post" enctype="multipart/form-data">
    <input type="file" name="image"  required>
    <br>
    <input type="submit" value="上传头像">
</form>
</body>
</html>
