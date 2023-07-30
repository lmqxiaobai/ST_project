<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/7/1
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>

<%--登录页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page isELIgnored="false" %>
<html lang="en">

<head>
    <%--设置编码--%>
    <meta charset="UTF-8">
    <title>微博-随时随地发现新鲜事</title>
    <%--css静态路径--%>
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
<div id="loginDiv" style="...">
    <form action="/ST_project/st_loginServlet" method="post" id="from">
        <h1 id="loginMsg">账号登录</h1>
        <center>
            <%--报错显示--%>
            <font color="red">
                <div id="errorMsg">${login_msg} ${register_msg}</div>
            </font>
        </center>

        <%--记住用户名和密码，从cookie里面获取原先存储的用户名和密码，避免中文乱码，所以要编码--%>
        <%
            String usernames = "";
            // 从cookie里面获取对应的用户名和密码
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("username")){
                        usernames = URLDecoder.decode(cookie.getValue(),"utf-8");
                        break;
                    }
                }
            }
        %>
        <p>
            用户名:<input id="username" name="username" value="<%=usernames%>" type="text">
        </p>
        <p>
            密码:<input id="password" name="password" value="${cookie.password.value}" type="password">
        </p>
        <p>
            记住用户:
            <c:if test="${not empty cookie.username and not empty cookie.password}">
                <input id="remember" name="remember" value="1" type="checkbox" checked>
            </c:if>
            <c:if test="${empty cookie.username or empty cookie.password}">
                <input id="remember" name="remember" value="1" type="checkbox">
            </c:if>
        </p>
        <div id="subDiv">
            <input type="submit" class="button" value="登录">
            <input type="reset" class="button" value="重置">
            <a href="st_register.jsp">注册微博!</a>
        </div>
    </form>
</div >
</body>
</html>

