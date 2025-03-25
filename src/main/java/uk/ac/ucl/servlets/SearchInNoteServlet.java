package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.Map;


@WebServlet("/searchInNoteServlet")

public class SearchInNoteServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getQuery(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getQuery(request, response);
    }
    public void getQuery(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

        String noteId = request.getParameter("noteId");
        String searchQuery = request.getParameter("searchQuery");

        String searchResult = model.searchInNote(noteId, searchQuery);

        Map<String, String> noteContent = model.getNote(noteId);
        noteContent.put("imageURL", null);
        noteContent.put("text", searchResult);


        System.out.println(searchResult);

        request.setAttribute("noteContent", noteContent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("note.jsp");
        dispatcher.forward(request, response);
    }
}
