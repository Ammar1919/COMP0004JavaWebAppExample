package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/removeNoteCategoryServlet")
public class RemoveNoteCategoryServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoryId = request.getParameter("categoryId");
        String noteId = request.getParameter("noteId");
        Model model = ModelFactory.getModel();
        model.removeNoteInCategory(categoryId, noteId);
        response.sendRedirect("categoryServlet?categoryId="+categoryId);
    }
}
