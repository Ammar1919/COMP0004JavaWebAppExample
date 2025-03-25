package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/searchNoteServlet")
public class SearchNoteServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getQuery(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getQuery(request, response);
    }

    public void getQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchQuery = request.getParameter("searchQuery");
        response.sendRedirect("/noteIndex.html?searchQuery="+searchQuery);
    }
}
