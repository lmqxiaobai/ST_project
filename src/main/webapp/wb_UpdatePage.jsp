<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/7/7
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    编辑动态
    接收来自SelectByPostUpdateServlet的post类，修改后提交到upadtePostServlet里面进行数据库操作
    --%>
<html>
<head>
    <title>编辑动态</title>
</head>
<body>
<h1>编辑微博</h1>
<form action="/ST_project/updatePostServlet" method="post">
    <input type="hidden" name="post_id" value="${post.getPost_id()}">
    动态:<input name="content" value="${post.getContent()}"><br>

    <input type="submit" value="提交">
</form>
</body>
</html>
