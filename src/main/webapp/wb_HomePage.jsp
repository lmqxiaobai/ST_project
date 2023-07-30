<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/7/6
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%--首页发布动态--%>
<!DOCTYPE html>
<html>
<head>
    <title>微博-随时随地发现新鲜事</title>
    <style>
        /* 样式代码 */
        /* 全局样式 */
        body {
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* 导航栏样式 */
        nav {
            background-color: #ffffff;
            padding: 10px;
            width: 100%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            left: 0;
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
            left: 180px; /* 调整侧边栏与左侧的间距 */
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
            margin-left: -230px; /* 调整微博内容区域与左侧的间距 */
        }

        .weibo-list {

            list-style-type: none;
            margin: 0;
            padding: 0;
            width: 150%; /*调整微博内容框的宽度*/
        }

        .weibo-item {
            background-color: #ffffff;
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 20px;
        }

        .weibo-author {
            display: flex;
            align-items: center;
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
            width: 100%;
            margin-top: auto;

        }

        /*  */
        .input-form {
            margin-bottom: 20px;
        }
        .input-form textarea {
            width: 100%;
            height: 80px;
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
        <ul class="weibo-list">
            <div class="input-form">
                <form action="/ST_project/addUserIdPostServlet" method="post" enctype="multipart/form-data">
                    <label for="message-input"></label>
                    <%--required 表示该输入框必填，如果不填无法提交--%>
                    <textarea id="message-input" name="content" placeholder="有什么新鲜事想分享给大家？" required></textarea>
                    <button  onclick="submitPost()">发送</button>
                    <input type="file" id="image-input" name="imageFile" accept="image/*" multiple>
                </form>
            </div>
            <c:forEach items="${posts}" var="post">
                <li class="weibo-item">
                    <div class="weibo-author">
                        <img src="${post.getImage_url()}" alt="作者头像">
                        <div class="author-info">
                        <span class="author-name">
                            <a href="/ST_project/selectByOtherPostServlet?post_id=${post.post_id}">
                                <strong>${post.username}</strong>
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
    <p>没有更多内容了~去其他页面看看吧</p>
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

<script>
    // 渲染消息
    function renderPosts(posts) {
        const postsContainer = document.querySelector(".weibo-list");

        // 遍历消息数组，生成HTML代码
        const html = posts.map(
            (post) => `
      <li class="weibo-item">
          <div class="weibo-author">
              <img src="${post.getImage_url()}" alt="作者头像">
              <div class="author-info">
                  <span class="author-name">
                      <a href="/ST_project/selectByOtherPostServlet?post_id=${post.post_id}">
                          <strong>${post.username}</strong>
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
          <div class="weibo-actions">
              <button class="like-button">点赞</button>
              <button class="comment-button">评论</button>
              <button class="share-button">分享</button>
          </div>
      </li>
    `
        ).join('');

        // 将HTML代码插入页面
        postsContainer.innerHTML = html;
    }

    // 使用AJAX获取所有动态数据
    function getAllPosts() {
        fetch("/ST_project/selectAllPostServlet")
            .then((response) => response.json())
            .then((data) => {
                const posts = data.posts;
                renderPosts(posts);
            })
            .catch((error) => {
                console.error("获取动态失败", error);
            });
    }

    // 页面加载完成后获取并显示所有动态数据
    document.addEventListener("DOMContentLoaded", getAllPosts);
</script>

<script>

    // 提交消息
    function submitPost() {
        const postsContainer = document.getElementById('message-input');
        const message = postsContainer.value;

        // 获取选择的图片文件
        const imageInput = document.getElementById('image-input');
        const imageFile = imageInput.files[0];

        // 创建一个新的表单对象
        const form = document.createElement('form');
        form.action = '/ST_project/addUserIdPostServlet';
        form.method = 'post';
        // 创建一个隐藏的输入字段，用于传递消息内容
        const contentInput = document.createElement('message-input');
        contentInput.type = 'hidden';
        contentInput.name = 'content';
        contentInput.value = message;
        // 将输入字段添加到表单中
        form.appendChild(contentInput);
        // 将选择的图片文件添加到表单中
        form.appendChild(imageFile);

        // 将表单添加到页面，并提交表单
        document.body.appendChild(form);
        form.submit();
    }

</script>
</body>
</html>