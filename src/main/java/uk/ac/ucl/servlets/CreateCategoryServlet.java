package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/createCategoryServlet")
public class CreateCategoryServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoryName = request.getParameter("newCategoryName");
        System.out.println("newCategoryName in Servlet " + categoryName);
        Model model = ModelFactory.getModel();

        model.newCategory(categoryName);

        response.sendRedirect("noteIndex.html");
    }
}
