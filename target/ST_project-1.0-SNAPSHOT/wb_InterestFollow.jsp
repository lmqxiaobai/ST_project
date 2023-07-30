<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/7/8
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%--关注列表页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>${user.username}的关注列表</title>
    <style>
        /* 全局样式 */
        body {
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        /* 导航栏样式 */
        nav {
            background-color: #ffffff;
            padding: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1;
        }

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }

        nav ul li {
            margin-right: 10px;
        }

        nav ul li a {
            color: #333;
            text-decoration: none;
            padding: 5px 10px;
            font-size: 16px;
            transition: color 0.3s ease;
        }

        nav ul li a:hover {
            color: #666;
        }

        /* 侧边栏样式 */
        .sidebar {
            width: 150px;
            background-color: #ffffff;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 50px;
            bottom: 20px;
            left: 225px;      /* 调整侧边栏与左侧的间距 */
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
        }

        .sidebar-heading {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .sidebar-list {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        .sidebar-item {
            margin-bottom: 10px;
            font-size: 16px;
        }

        /* 微博内容区域样式 */
        main {
            display: flex;
            justify-content: center;
            margin-top: 50px; /* 调整内容区域与顶部的间距 */
            margin-bottom: 20px;
            max-width: 920px;    /*调整微博内容框在屏幕所处位置*/
            padding: 0 20px;
            margin-left: 50px; /* 调整微博内容区域与左侧的间距 */
        }

        .weibo-list {
            list-style-type: none;
            margin: 0;
            padding: 0;
            max-width: 1200px;
            width: 300%;      /*调整微博内容框的宽度*/
        }

        .weibo-item {
            background-color: #ffffff;
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 0px;
        }

        .weibo-author {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .weibo-author img {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .author-info {
            display: flex;
            flex-direction: column;
        }

        .author-name {
            font-weight: bold;
            color: black;
        }


        .weibo-actions button {
            margin-right: 10px;
            padding: 5px 10px;
            font-size: 14px;
        }


        /* 底部信息样式 */
        footer {
            background-color: #f0f0f0;
            padding: 10px;
            text-align: center;
            width: 95%;
            margin-top: auto;

        }
    </style>
</head>
<body>
<header>
    <!-- 导航栏 -->
    <nav>
        <ul>
            <li><a href="/ST_project/selectAllHomeServlet">首页</a></li>
            <li><a href="/ST_project/selectAllPostServlet">热门</a></li>
            <li><a href="#">消息</a></li>
            <li><a href="/ST_project/selectByUserIdServlet?user_id=${user.user_id}">我的微博</a></li>
        </ul>
    </nav>
</header>

<main>
    <!-- 侧边栏 -->
    <aside class="sidebar">
        <h2 class="sidebar-heading">个人中心</h2>
        <ul class="sidebar-list">
            <li class="sidebar-item">
                <a href="/ST_project/selectByUserIdServlet?user_id=${user.user_id}">我的主页</a>
            </li>
            <li class="sidebar-item">
                <a href="/ST_project/selectInterestServlet?follower_id=${user.user_id}">我的关注</a>
            </li>
            <li class="sidebar-item">
                <a href="/ST_project/selectFansServlet?following_id=${user.user_id}">我的粉丝</a>
            </li>
            <li class="sidebar-item">我的收藏</li>
            <li class="sidebar-item">我的赞</li>
            <li class="sidebar-item">
                <a href="/ST_project/wb_AddHeadImage.jsp?user_id=${user.user_id}">上传头像</a>
            </li>
            <li class="sidebar-item">
                <a href="/ST_project/st_login.jsp">退出登录</a>
            </li>
        </ul>
    </aside>
    <!-- 微博内容区域 -->
    <section id="weibo-content">
        <!-- 微博列表 -->
        <ul class="weibo-list">
            <c:forEach items="${users}" var="user">
                <li class="weibo-item">
                    <div class="weibo-author">
                        <img src="${user.getImage_url()}" alt="用户头像">
                        <div class="author-info">
                            <span class="author-name">
                                <a href="/ST_project/selectInterestPageServlet?user_id=${user.getUser_id()}">
                                    <strong>${user.getUsername()}</strong>
                                </a>
                            </span>
                        </div>
                    </div>
                    <div class="weibo-actions">
                        <button class="like-button">取消关注</button>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </section>
</main>
<footer>
    <!-- 底部信息 -->
    <p>版权信息 © 2023 微博</p>
</footer>

<script>
    // JavaScript代码，用于添加滚动时的动态效果
    var nav = document.querySelector("nav");
    var sidebar = document.querySelector(".sidebar");

    window.addEventListener("scroll", function() {
        if (window.pageYOffset > 0) {
            nav.classList.add("scrolled");
            sidebar.classList.add("sticky");
        } else {
            nav.classList.remove("scrolled");
            sidebar.classList.remove("sticky");
        }
    });
</script>

</body>
</html>
