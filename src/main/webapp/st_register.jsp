<%--
  Created by IntelliJ IDEA.
  User: LMQ
  Date: 2023/5/23
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>

<%--注册页面--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>微博注册</title>
    <link href="css/register.css" rel="stylesheet">
</head>
<body>

<div class="form-div">
    <div class="reg-content">
        <h1>微博注册</h1>
        <span>已有帐号？</span> <a href="st_login.jsp">直接登录</a>
    </div>
    <form id="reg-form" action="/ST_project/st_registerServlet" method="post">

        <table>

            <tr>
                <td>用户名</td>
                <td class="inputs">
                    <input name="username" type="text" id="username">
                    <br>
                    <span id="username_err1" class="err_msg" style="display: none">用户名已存在</span>
                    <br>
                    <%-- ${}里面可以直接调用request域中的键，返回对应的值}--%>
                    <span id="username_err" class="err_msg" >${register_msg}</span>
                </td>

            </tr>

            <tr>
                <td>密码</td>
                <td class="inputs">
                    <input name="password" type="password" id="password">
                    <br>
                    <span id="password_err" class="err_msg" style="display: none">密码格式有误</span>
                </td>
            </tr>

            <tr>
                <td>验证码</td>
                <td class="inputs">
                    <input name="checkCode" type="text" id="checkCode">
                    <%--事件绑定，根据定义的函数方法绑定--%>
                    <img id="checkCodeImg" src="/ST_project/st_CheckCodeServlet" onclick="changeCode()">
                    <a href="#" id="changImg" onclick="changeCode()">看不清？</a>
                    <br>
                    <span id="username_err2" class="err_msg" >${register_msg2}</span>

                </td>
            </tr>

        </table>

        <div class="buttons">
            <input value="注 册" type="submit" id="reg_btn">
        </div>
        <br class="clear">
    </form>

</div>

<script type="text/javascript">
    <%--  事件绑定，当点击看不清按钮时，会转到checkCodeServlet页面生成验证码图片,新建一个函数changeCode用户变更验证码  --%>
    function changeCode(){
        //为了避免出现重复的数据，导致缓存之后验证码不会变，那个参数可以用时间
        document.getElementById("checkCodeImg").src = "/ST_project/st_CheckCodeServlet?"+new Date().getMilliseconds();
    }
</script>

<script>
    // 1.给用户名输入框绑定失去焦点事件（当鼠标离开输入框时开始执行）
    document.getElementById("username").onblur = function (){
        // 2.发生ajax请求
        // 获取用户名的值
        var username = this.value;
        // 创建核心对象
        var xhttp;
        if(window.XMLHttpRequest){
            xhttp = new XMLHttpRequest();
        }else {
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        //发生请求,将用户框输入的用户名参数传递到RegisterServlet
        xhttp.open("POST","http://localhost:8080/ST_project/st_selectUserServlet?username="+username);
        xhttp.send();

        // 获取响应
        xhttp.onreadystatechange = function (){
            if(this.readyState == 4 && this.status == 200){
                if(this.responseText == "true"){
                    // 用户名存在，显示提示信息
                    document.getElementById("username_err1").style.display = '';
                }else {
                    // 用户名不存在，清除提示信息
                    document.getElementById("username_err1").style.display = 'none';
                }
            }
        }
    }
</script>
</body>
</html>


