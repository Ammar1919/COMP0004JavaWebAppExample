package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/noteIndex.html")
public class IndexServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Model model = ModelFactory.getModel();
        List<String> categoryIndex = model.categoryIndex();

        String categoryId = request.getParameter("categoryId");
        String searchQuery = request.getParameter("searchQuery");
        System.out.println("search query " + searchQuery);
        List<String> index;

        if (categoryId != null) {
            System.out.println("Getting category notes");
            index = model.getCategoryNotes(categoryId);
            request.setAttribute("selectedCategory", categoryId);
        }
        else if (searchQuery != null) {
            index = model.searchNotes(searchQuery);
        }
        else {
            index = model.getIndex();
        }

        request.setAttribute("index", index);
        request.setAttribute("categoryIndex", categoryIndex);
        request.setAttribute("searchQuery", searchQuery);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteIndex.jsp");
        dispatch.forward(request, response);
    }
}
