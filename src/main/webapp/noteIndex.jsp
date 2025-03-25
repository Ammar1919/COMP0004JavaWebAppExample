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

    <%
        String selectedCategory = (String) request.getAttribute("selectedCategory");
        List<String> notes = (List<String>) request.getAttribute("index");
        String searchQuery = (String) request.getAttribute("searchQuery");
        List<String> categories = (List<String>) request.getAttribute("categoryIndex");
        System.out.println("Categories in HTML: " + categories);
    %>

    <button onclick="document.getElementById('searchNotes').style.display='block'">Search for a Note</button>
    <form id="searchNotes" action="searchNoteServlet" method="post" style="display:none;">
        <input id="searchQuery" name="searchQuery" type="text" value="Enter your query here">
        <button>Search</button>
    </form>

    <% if (selectedCategory == null && searchQuery == null){%>
    <h2>Categories:</h2>
    <ul>
        <%
            for (int i = 0; i < categories.size(); i++){
                String categoryId = categories.get(i);
                System.out.println("Category" + i + ": " + categoryId);
        %>
        <li>
            <a href="categoryServlet?categoryId=<%=categoryId%>"><%=categoryId%></a>
        </li>
        <% } %>
    </ul>
    <%}%>
    <h2>Notes
        <% if (selectedCategory != null) { %> in <%= selectedCategory %> <% }
        else if (searchQuery != null) {%> with <%= searchQuery %> <%}%>:</h2>
    <ul>
        <%
            for (int i = 0; i < notes.size(); i++){
                String noteId = notes.get(i);
        %>
        <li>
            <a href="noteServlet?noteId=<%=noteId%><% if (selectedCategory != null) { %>&categoryId=<%=selectedCategory%><% } %>"><%=noteId%></a>
        </li>
        <% } %>
    </ul>

    <%if (selectedCategory == null){%>
    <p onclick="document.getElementById('newCategory').style.display='block'"><u><strong>Create a New Category</strong></u></p>
    <form id="newCategory" action="createCategoryServlet" method="post" style="display:none;">
        <label for="newCategoryName">Add a Category</label>
        <input id="newCategoryName" name="newCategoryName" type="text">
        <button type="submit">Enter</button>
    </form>

    <p onclick="document.getElementById('addToCategory').style.display='block'"><u><strong>Add a Note to a Category</strong></u></p>
    <form id="addToCategory" action="addToCategoryServlet" method="post" style="display:none;">
        <label for="categoryName">Enter a Category: </label>
        <input id="categoryName" name="categoryName" type="text">
        <label for="noteName">Enter note to add: </label>
        <input id="noteName" name="noteName" type="text">
        <button type="submit">Enter</button>
    </form>
    <a href="createNote.jsp">New Note</a>
    <br>
    <br>
    <%}%>
    <%if (selectedCategory!=null){%>
    <form id="deleteCategory" action="deleteServlet" method="post">
        <input type="hidden" id="categoryId" name="categoryId" value="<%=selectedCategory%>">
        <button type="submit">Delete Category</button>
    </form>

    <a href="noteIndex.html">Back to Notes</a>
    <br>
    <br>
    <%}%>
    <a href="index.html">Back to Home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
