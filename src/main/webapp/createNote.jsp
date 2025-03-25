<%--
  Created by IntelliJ IDEA.
  User: ammark
  Date: 3/6/25
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Note</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>New Note</h2>
    <form action="createNoteServlet" method="post" enctype="multipart/form-data">
        <label for="title">Note Title</label>
        <input type="text" id="title" name="name" required><br><br>
        <label for="text">Content   </label>
        <input type="text" id="text" name="text" rows="20" cols="40 "required><br><br>
        <label for="image">Image (optional)</label>
        <input type="file" id="image" name="image" accept="image/*"><br><br>
        <button type="submit">Create Note</button>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
