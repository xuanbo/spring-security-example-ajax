<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="/static/jquery/jquery-3.1.1.min.js"></script>
    <title>登录</title>
</head>
<body>
<c:import url="WEB-INF/views/template/head.jsp"/>

<c:url value="/login" var="loginUrl"/>
<c:url value="/captcha" var="captchaUrl"/>

<form id="loginForm" action="${loginUrl}" method="post">
    <p id="error" style="display: none; color: red">${error}</p>
    <c:if test="${error != null}">
        <p>${error}</p>
    </c:if>
    <c:if test="${logout != null}">
        <p>${logout}</p>
    </c:if>
    <p>
        <label for="username">用户名：</label>
        <input type="text" id="username" name="username"/>
    </p>

    <p>
        <label for="password">密码：</label>
        <input type="password" id="password" name="password"/>
    </p>

    <p>
        <label for="captcha">验证码：</label>
        <input type="text" id="captcha" name="captcha"/>
        <img src="${captchaUrl}">
    </p>

    <p>
        <label for="rememberMe">一周内记住我：</label>
        <input type="checkbox" id="rememberMe" name="remember-me" value="true"/>
    </p>
    <button id="loginBtn" type="submit">登录</button>
</form>

<script type="text/javascript">
    (function ($) {
        $(function () {
            $('#loginBtn').on('click', function (e) {
                e.preventDefault();
                var username = $('#username').val();
                var password = $('#password').val();
                var captcha = $('#captcha').val();
                var rememberMe = $('#rememberMe').val();
                var data = {
                    username: username,
                    password: password,
                    captcha: captcha,
                    'remember-me': rememberMe
                };
                $.post("/login", data, function (result) {
                    if (!result.flag) {
                        $('#error').html(result.msg).show();
                    } else {
                        alert(result.msg);
                    }
                });
            });
        });
    })(jQuery);
</script>
</body>
</html>
