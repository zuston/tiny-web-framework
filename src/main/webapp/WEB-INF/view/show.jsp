<%@ page import="java.util.List" %>
<%@ page import="app.orm.user" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: zuston
  Date: 16/11/30
  Time: 上午11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>todo-list</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body>
    <div class="container">
        <h4>本周例会值班：<%=request.getAttribute("name")%><button style="float: right;"><a href="/logout">退出登录</a></button></h4>
        <table class="table">
            <caption>TODO LIST</caption>
            <thead>
            <tr>
                <th class="col-sm-2">name</th>
                <th class="col-sm-10">todo</th>
            </thead>
            <tbody>
            <%
                List<Object> personList = (List<Object>) request.getAttribute("personList");
                ArrayList<user> list = new ArrayList<>();
                for (Object person:personList){
                    list.add((user)person);
                }
                pageContext.setAttribute("personList",list);
            %>
            <c:out value="{param}"></c:out>
            <c:forEach items="${param.personList}" var="person" step="1">
                <tr class="active">
                    <td><c:out value="${person.name}" /></td>
                    <td><textarea class="form-control" rows="2"></textarea></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
