package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String noteId = request.getParameter("noteId");
        String categoryId = request.getParameter("categoryId");
        Model model = ModelFactory.getModel();
        if (noteId != null && categoryId == null){
            model.deleteNote(noteId);
        }
        else {
            model.deleteCategory(categoryId);
        }
        response.sendRedirect("/noteIndex.html");
    }
}
