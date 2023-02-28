<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alinakursekova
  Date: 29/11/22
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Oops</title>
</head>
<body>
<div class="container">
    <div class="top-left">
        <%
            String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
            Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
            String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
            Integer statusCode = 404;
            if (servletName == null) {
                servletName = "Unknown";
            }
            if (requestUri == null) {
                requestUri = "Unknown";
            }
        %>
        <c:choose>
            <c:when test="${statusCode != 500}">
                <h3>Error Details</h3>
                <ul>
                    <li>Status Code: <%=statusCode%>
                    </li>
                    <li>Requested URI: <%=requestUri%>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>
                <h3>Exception Details</h3>
                <ul>
                    <li>Servlet Name: <%=servletName%>
                    </li>
                    <li>Exception Name: <%=throwable.getClass().getName()%>
                    </li>
                    <li>Requested URI: <%=requestUri%>
                    </li>
                    <li>Exception Message: <%=throwable.getMessage()%>
                    </li>
                </ul>
            </c:otherwise>
        </c:choose>
        <form action="IndexServlet" method="get">
            <input type="submit" name="homeButton" value="Go back"/>
        </form>
    </div>
</div>
</body>
</html>
