/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/11.0.4
 * Generated at: 2025-03-11 15:56:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import java.util.HashMap;
import java.util.Map;

public final class note_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(4);
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.LinkedHashSet<>(3);
    _jspx_imports_classes.add("java.util.Map");
    _jspx_imports_classes.add("java.util.HashMap");
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
 Map<String, String> noteContent = (Map<String, String>) request.getAttribute("noteContent");
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

      out.write("\n");
      out.write("<head>\n");
      out.write("    <title>");
      out.print(name);
      out.write("</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/header.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("<div class=\"main\">\n");
      out.write("    <h2 id=\"titleName\" onclick=\"document.getElementById('newTitleForm').style.display='block'; document.getElementById('hideTitleForm').style.display='block'\">");
      out.print(name);
      out.write("</h2>\n");
      out.write("    <form id=\"newTitleForm\" action=\"editNoteServlet?noteId=");
      out.print(name);
      out.write("&editType=");
      out.print(0);
      out.write("\" method=\"post\" style=\"display:none;\">\n");
      out.write("        <label type=\"hidden\" for=\"newContent\">Rename</label>\n");
      out.write("        <input id=\"newContent\" name=\"newContent\" type=\"text\">\n");
      out.write("        <button type=\"submit\">\n");
      out.write("            Submit\n");
      out.write("        </button>\n");
      out.write("    </form>\n");
      out.write("    <button id=\"hideTitleForm\" type=\"submit\" onclick=\"document.getElementById('newTitleForm').style.display='none';document.getElementById('hideTitleForm').style.display='none'\" style=\"display:none;\">\n");
      out.write("        Hide\n");
      out.write("    </button>\n");
      out.write("\n");
      out.write("    <p id=\"textBox\" onclick=\"document.getElementById('editTextForm').style.display='block'\">");
      out.print(text);
      out.write(" <a href=\"");
      out.print(url);
      out.write('"');
      out.write('>');
      out.print(url);
      out.write("</a> </p>\n");
      out.write("    <form id=\"editTextForm\" action=\"editNoteServlet?noteId=");
      out.print(name);
      out.write("&editType=");
      out.print(1);
      out.write("\" method=\"post\" style=\"display:none;\">\n");
      out.write("        <label type=\"hidden\" for=\"newContent\">Edit:</label>\n");
      out.write("        <input id=\"editText\" name=\"newContent\" type=\"text\">\n");
      out.write("        <button type=\"submit\">\n");
      out.write("            Submit\n");
      out.write("        </button>\n");
      out.write("    </form>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<form action=\"deleteNodeServlet\" method=\"post\">\n");
      out.write("    <input type=\"hidden\" name=\"noteId\" value=\"");
      out.print(name);
      out.write("\">\n");
      out.write("    <button type=\"submit\">Delete Note</button>\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("<a href=\"noteIndex.html\">Back to Notes</a>\n");
      out.write("<br>\n");
      out.write("<br>\n");
      out.write("<a href=\"index.html\">Back to Home</a>\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/footer.jsp", out, false);
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
