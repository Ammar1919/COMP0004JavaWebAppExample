<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.io.File"%>
<html>
<% Map<String, String> noteContent = (Map<String, String>) request.getAttribute("noteContent");
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
    String categoryId = request.getParameter("categoryId");
%>
<head>
    <title><%=name%></title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="main">

    <button onclick="document.getElementById('searchNotes').style.display='block'">Search in this note</button>
    <form id="searchNotes" action="searchInNoteServlet?noteId=<%=name%>" method="post" style="display:none;">
        <input id="searchQuery" name="searchQuery" type="text" value="Enter your query here">
        <button>Search</button>
    </form>

    <h2 id="titleName" onclick="document.getElementById('newTitleForm').style.display='block'; document.getElementById('hideTitleForm').style.display='block'"><%=name%></h2>
    <form id="newTitleForm" action="editNoteServlet?noteId=<%=name%>&editType=<%=0%>" method="post" enctype="multipart/form-data" style="display:none;">
        <label type="hidden" for="newContent">Rename</label>
        <input id="newContent" name="newContent" type="text" value="<%=name%>">
        <button type="submit">
            Submit
        </button>
    </form>
    <button id="hideTitleForm" type="submit" onclick="document.getElementById('newTitleForm').style.display='none';document.getElementById('hideTitleForm').style.display='none'" style="display:none;">
        Hide
    </button>

    <p id="textBox" onclick="document.getElementById('editTextForm').style.display='block'"><%=text%></p>
    <form id="editTextForm" action="editNoteServlet?noteId=<%=name%>&editType=<%=1%>" method="post" enctype="multipart/form-data" style="display:none;">
        <label type="hidden" for="newContent">Edit:</label>
        <input id="editText" name="newContent" type="text" value="<%=text%>">
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

    <button onclick="document.getElementById('editImage').style.display='block'">Add/Replace Image</button>
    <form id="editImage" action="editNoteServlet?noteId=<%=name%>&editType=<%=2%>" method="post" enctype="multipart/form-data" style="display: none;">
        <br>
        <input type="file" id="replaceImage" name="editImage" accept="image/*"><br><br>
        <button type="submit">Save Changes</button>
    </form>

</div>
<div id="actions">
    <form action="deleteServlet" method="post">
        <input type="hidden" name="noteId" value="<%=name%>">
        <button type="submit">Delete Note</button>
    </form>

    <% if (categoryId != null){%>

    <form action="removeNoteCategoryServlet" method="post">
        <input type="hidden" name="categoryId" value="<%=categoryId%>">
        <input type="hidden" name="noteId" value="<%=name%>">
        <button type="submit">Remove Note from <%=categoryId%></button>
    </form>

    <a href="categoryServlet?categoryId=<%=categoryId%>">Back To <%=categoryId%></a>
    <%}%>
    <br>
    <br>
    <a href="noteIndex.html">Back to Notes</a>
    <br>
    <br>
    <a href="index.html">Back to Home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
