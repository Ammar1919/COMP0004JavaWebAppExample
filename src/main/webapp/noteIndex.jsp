<%--
  Created by IntelliJ IDEA.
  User: ammark
  Date: 3/6/25
  Time: 1:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fasterxml.jackson.databind.JsonNode"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.File" %>


<html>
<head>
    <title>Notes App</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Notes:</h2>
    <ul>
        <%
            List<String> notes = (List<String>) request.getAttribute("index");
            for (int i = 0; i < notes.size(); i++){
                String noteId = notes.get(i);
        %>
        <li>
            <a href="noteServlet?noteId=<%=noteId%>"><%=notes.get(i)%></a>
        </li>
        <% } %>
    </ul>
    <a href="createNote.jsp">New Note</a>
    <a href="index.html">Back to Home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
