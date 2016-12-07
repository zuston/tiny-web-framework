<%@ page import="java.util.List" %>
<%@ page import="app.orm.user" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: zuston
  Date: 16/11/30
  Time: 上午11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>todo-list</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="http://www.bootcss.com/p/buttons/css/buttons.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body>
    <div class="container">
        <hr>
        <span>
            <a style="font-size: 40px;font-family: NanumMyeongjo;color: lightpink;text-decoration: none;">todo-list</a>
            <div style="font-size: 16px;float: right;margin-top: 25px;">
                <a href="/logout" class="button button-primary button-rounded button-small"><%=request.getAttribute("name")%>-logout</a>
            </div>
        </span>
        <table class="table">
            <%--<caption>TODO LIST</caption>--%>
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
                pageContext.setAttribute("relation",request.getAttribute("relation"));
                HashMap<Integer,String> id2String = (HashMap<Integer, String>) request.getAttribute("relation");
            %>
            <c:forEach items="${personList}" var="person">
                <tr class="active">
                    <td><a style="font-size: 18px;text-decoration: none;color:black;letter-spacing: 1px;"><c:out value="${person.getName()}"></c:out></a> </td>
                    <td>
                        <c:set value="${person.getId()}" var="index" />
                        <input type="text" class="form-control tinfo" rows="2" id="${person.getId()}" value="<%=id2String.get(pageContext.getAttribute("index"))%>">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <a href="" class="button button-3d button-action button-pill" id="saveList">SAVE</a>
    </div>
</body>

<script type="text/javascript">
    $(document).ready(function(){
        $("#saveList").click(function(){
            var res = []
            $(".form-control.tinfo").each(function (index,element) {
                var temp = {};
                temp['id'] = element.id;
                temp['value'] = element.value;
                res.push(temp)
            });
            data = JSON.stringify(res)
            $.post("/savetodo",{"data":data}, function () { location.href = "/login"; });
        });
    });
</script>
</html>
