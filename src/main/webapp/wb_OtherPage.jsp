<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/7/6
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>

<%--其他人主页页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>${user.getUsername()}的个人主页</title>
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
            left: 190px;      /* 调整侧边栏与左侧的间距 */
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
            max-width: 900px;    /*调整微博内容框在屏幕所处位置*/
            padding: 0 20px;
            margin-left: 110px; /* 调整微博内容区域与左侧的间距 */
        }

        .weibo-list {
            list-style-type: none;
            margin: 0;
            padding: 0;
            width: 180%;  /*调整微博内容框的宽度*/
        }

        .weibo-item {
            background-color: #ffffff;
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 20px;
        }
        .weibo-authorI {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .weibo-authorI img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            margin-right: 10px;
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

        .post-time {
            font-size: 14px;
            color: #666;
        }

        .weibo-text {
            margin-top: 10px;
            font-size: 16px;
        }

        .weibo-images {
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start; /* 居左对齐 */
            margin-bottom: 10px; /* 可选：用于在行之间添加一些间距 */
        }

        .weibo-image-item {
            margin-right: 10px; /* 可选：用于在图片之间添加一些间距 */
        }

        .weibo-images img {
            width: 100px;
            height: 100px;
            margin: 5px;
            object-fit: cover; /* 控制图片裁剪以适应指定尺寸 */
        }

        .weibo-actions {
            display: flex;
            align-items: center;
        }

        .weibo-actions button {
            margin-right: 10px;
            padding: 5px 10px;
            font-size: 14px;
        }

        /* 底部信息样式 */
        footer {

            padding: 10px;
            text-align: center;
            width: 100%;


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
            <li><a href="/ST_project/selectByUserIdServlet?user_id=${user_id1}">我的微博</a></li>
        </ul>
    </nav>
</header>
<main>
    <!-- 侧边栏 -->
    <aside class="sidebar">
        <h2 class="sidebar-heading">首页</h2>
        <ul class="sidebar-list">
            <li class="sidebar-item">全部关注</li>
            <li class="sidebar-item">最新微博</li>
            <li class="sidebar-item">特别关注</li>
        </ul>
    </aside>

    <!-- 微博内容区域 -->
    <section id="weibo-content">
        <!-- 微博列表 -->
        <div class="weibo-authorI">
            <img src="${user.getImage_url()}" alt="用户头像">
            <div class="author-info">
                <span class="author-name">
                    <h2>${user.username}</h2>
                </span>
                <span class="author-name">
                    <form action="/ST_project/checkIfFollowedServlet" method="post">
                         <input type="hidden" name="following_id" value="${user.user_id}">
                         <button type="submit">${followButtonText}</button>
                    </form>
                </span>
            </div>
        </div>


        <ul class="weibo-list">
            <c:forEach items="${posts}" var="post">
                <li class="weibo-item">
                    <div class="weibo-author">
                        <img src="${user.getImage_url()}" alt="用户头像">
                        <div class="author-info">
                            <span class="author-name">
                                <a href="/ST_project/selectByOtherPostServlet?post_id=${post.post_id}">
                                    <strong>${post.getUsername()}</strong>
                                </a>
                            </span>
                            <span class="post-time">
                                <fmt:formatDate value="${post.getCreated_at()}" pattern="yyyy年MM月dd日 HH:mm:ss" />
                            </span>
                        </div>
                    </div>
                    <div class="weibo-text">
                            ${post.getContent()}<br>
                    </div>
                    <div class="weibo-images">
                        <c:forEach items="${post.getImage_urls()}" var="imageUrl" varStatus="loop">
                            <%--在每三张图片后插入一个清除浮动的元素--%>
                            <c:if test="${loop.index % 3 == 0}">
                                <div class="weibo-image-row">
                            </c:if>
                            <div class="weibo-image-item">
                                <img src="${imageUrl}" alt="动态图片">
                            </div>
                            <c:if test="${(loop.index + 1) % 3 == 0 || loop.last}">
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="weibo-actions">
                        <button class="like-button">点赞</button>
                        <button class="comment-button">评论</button>
                        <button class="share-button">分享</button>
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

</body>
</html>
