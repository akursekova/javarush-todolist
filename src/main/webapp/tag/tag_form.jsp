<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: antonkupreychik
  Date: 3.12.22
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tag</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<c:if test="${tag != null}">
<form action="tag" method="post">
    </c:if>
    <c:if test="${tag == null}">
    <form action="new-tag" method="post">
        </c:if>
        <div class="container">
            <br>

            <h1>
                <c:if test="${tag != null}">
                    Edit Tag
                </c:if>
                <c:if test="${tag == null}">
                    Add New Tag
                </c:if>
            </h1>

            <br>

            <form action="tag" method="post">
                <input type="hidden" name="id" value='${tag.id}'/>

                <div class="form-group">
                    <label for="tagName" class="form-label">Tag name</label>
                    <input type="text" class="form-control" id="tagName" name="tagName" placeholder="Enter tag name"
                           value="<c:out value='${tag.name}' />">
                    <br>
                    <label class="form-label" for="favcolor">Select your color:</label>
                    <br>
                    <input type="color" id="favcolor" name="favcolor"

                    <c:if test="${tag != null}">
                           value=${tag.color}
                           </c:if>
                                   <c:if test="${tag == null}">value="#ff0000"
                    </c:if>>

                    <br>
                    <br>
                    <button type="submit" class="btn btn-success">Submit</button>
                </div>
            </form>
            <button type="button" class="btn btn-primary" onclick="location.href='table-tag'">All tags</button>

            <c:choose>
                <c:when test="${action == 'new'}">
                    <button type="button" class="btn btn-primary" onclick="location.href='new-task'">Go Back</button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-primary" onclick="location.href='task'">Go Back</button>
                </c:otherwise>
            </c:choose>

        </div>
</body>
</html>