package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/addToCategoryServlet")
public class AddToCategoryServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoryId = request.getParameter("categoryName");
        String noteId = request.getParameter("noteName");
        Model model = ModelFactory.getModel();
        model.addToCategory(categoryId, noteId);
        response.sendRedirect("categoryServlet?categoryId="+categoryId);
    }
}
