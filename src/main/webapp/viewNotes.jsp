<%--
  Created by IntelliJ IDEA.
  User: ammark
  Date: 3/10/25
  Time: 10:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<html>
<head>
    <title>Notes</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div id="sortMethods">
    <form action="allNotesServlet?sortId" method="get">
        <input type="hidden" name="sortId" value="0">
        <button type="submit">Sort by Earliest</button>
    </form>
    <form action="allNotesServlet?sortId" method="get">
        <input type="hidden" name="sortId" value="1">
        <button type="submit">Sort by Latest</button>
    </form>
    <form action="allNotesServlet?sortId" method="get">
        <input type="hidden" name="sortId" value="2">
        <button type="submit">Sort A-Z</button>
    </form>
    <form action="allNotesServlet?sortId" method="get">
        <input type="hidden" name="sortId" value="3">
        <button type="submit">Sort Z-A</button>
    </form>
</div>

<h2>All Notes</h2>
<ul>
    <%
        List<Map<String, String>> allNotes = (List<Map<String, String>>) request.getAttribute("allNotes");
        for (Map<String, String> noteContent : allNotes){
            String name = noteContent.get("name");
            System.out.println("NoteId from HTML" + name);
            String text = noteContent.get("text");
            String imageURL = noteContent.get("imageURL");
            if (text!=null){
                text = text.replaceAll("(https?://[^\s]+)", "<a href=\"$1\" target=\"_blank\">$1</a>)");
            }
            else {
                text = "";
            }
    %>
    <div style="border: 1px solid blueviolet; padding: 10px; margin-bottom: 20px;">
        <h2 id="titleName_<%=name%>" onclick="document.getElementById('newTitleForm_<%=name%>').style.display='block'; document.getElementById('hideTitleForm_<%=name%>').style.display='block'"><%=name%></h2>
        <form id="newTitleForm_<%=name%>" action="editNoteServlet?noteId=<%=name%>&editType=<%=0%>" method="post" enctype="multipart/form-data" style="display:none;">
            <label type="hidden" for="newContent_<%=name%>">Rename</label>
            <input id="newContent_<%=name%>" name="newContent" type="text" value="<%=name%>">
            <button type="submit">
                Submit
            </button>
        </form>
        <button id="hideTitleForm_<%=name%>" type="submit" onclick="document.getElementById('newTitleForm_<%=name%>').style.display='none';document.getElementById('hideTitleForm_<%=name%>').style.display='none'" style="display:none;">
            Hide
        </button>

        <p id="textBox_<%=name%>" onclick="document.getElementById('editTextForm_<%=name%>').style.display='block'"><%=text%> </p>
        <form id="editTextForm_<%=name%>" action="editNoteServlet?noteId=<%=name%>&editType=<%=1%>" method="post" enctype="multipart/form-data" style="display:none;">
            <label type="hidden" for="newContent_<%=name%>">Edit:</label>
            <input id="editText_<%=name%>" name="newContent" type="text" value="<%=text%>">
            <button type="submit">
                Submit
            </button>
        </form>

        <%
            if(imageURL != null && !imageURL.equals("null") && !imageURL.isEmpty()) {
                String fixedImageURL = imageURL.replace("src/main/webapp/", "");
        %>
        <img src="<%= request.getContextPath() %>/<%= fixedImageURL %>" alt="Note Image" style="max-width: 700px; max-height: 600px;">
        <%
            }
        %>
    </div>
    <%
        }
    %>
</ul>
<ul>
    <a href="createNote.jsp">New Note</a>
    <br>
    <br>
    <a href="index.html">Back to Home</a>
</ul>
<jsp:include page="/footer.jsp"/>
</body>
</html>
