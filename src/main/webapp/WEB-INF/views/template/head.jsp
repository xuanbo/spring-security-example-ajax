<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>
            欢迎 : ${pageContext.request.userPrincipal.name}
            <a href="<c:url value="/logout" />">退出</a>
        </h2>
    </c:if>
    <c:if test="${pageContext.request.userPrincipal.name == null}">
        <h2>
            欢迎 : 游客
            <a href="<c:url value="/login" />">登录</a>
        </h2>
    </c:if>
</div>

