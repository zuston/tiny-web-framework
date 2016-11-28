<%--
  Created by IntelliJ IDEA.
  User: zuston
  Date: 16/11/13
  Time: 下午11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4>hello index.jsp</h4>
<h1><%=request.getAttribute("name")%></h1>
<h1><%=request.getMethod()%></h1>

</body>
</html>
