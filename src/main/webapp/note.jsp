<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<html>
<% Map<String, String> noteContent = (Map<String, String>) request.getAttribute("noteContent");
    String name = noteContent.get("name");
    System.out.println("NoteId from HTML" + name);
    String text = noteContent.get("text");
    String url = noteContent.get("url");
    if (text!=null){
        text = text.replaceAll("(https?://[^\s]+)", "<a href=\"$1\" target=\"_blank\">$1</a>)");
    }
    else {
        text = "";
    }
%>
<head>
    <title><%=name%></title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="main">
    <h2 id="titleName" onclick="document.getElementById('newTitleForm').style.display='block'; document.getElementById('hideTitleForm').style.display='block'"><%=name%></h2>
    <form id="newTitleForm" action="editNoteServlet?noteId=<%=name%>&editType=<%=0%>" method="post" style="display:none;">
        <label type="hidden" for="newContent">Rename</label>
        <input id="newContent" name="newContent" type="text">
        <button type="submit">
            Submit
        </button>
    </form>
    <button id="hideTitleForm" type="submit" onclick="document.getElementById('newTitleForm').style.display='none';document.getElementById('hideTitleForm').style.display='none'" style="display:none;">
        Hide
    </button>

    <p id="textBox" onclick="document.getElementById('editTextForm').style.display='block'"><%=text%> <a href="<%=url%>"><%=url%></a> </p>
    <form id="editTextForm" action="editNoteServlet?noteId=<%=name%>&editType=<%=1%>" method="post" style="display:none;">
        <label type="hidden" for="newContent">Edit:</label>
        <input id="editText" name="newContent" type="text">
        <button type="submit">
            Submit
        </button>
    </form>
</div>

<form action="deleteNodeServlet" method="post">
    <input type="hidden" name="noteId" value="<%=name%>">
    <button type="submit">Delete Note</button>
</form>

<a href="noteIndex.html">Back to Notes</a>
<br>
<br>
<a href="index.html">Back to Home</a>
<jsp:include page="/footer.jsp"/>
</body>
</html>
