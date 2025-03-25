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

@WebServlet("/noteServlet")
public class ViewNoteServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        getNotes(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        getNotes(request, response);
    }

    public void getNotes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        String noteId = request.getParameter("noteId");
        String categoryId = request.getParameter("categoryId");

        Map<String, String> noteContent = model.getNote(noteId);

        System.out.println("noteContent in servlet" + noteContent);
        request.setAttribute("noteContent", noteContent);
        request.setAttribute("categoryId", categoryId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("note.jsp");
        dispatcher.forward(request, response);
        //response.sendRedirect("note.jsp");
    }
}
