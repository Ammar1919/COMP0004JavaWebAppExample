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
import java.util.List;
import java.util.Map;

@WebServlet("/allNotesServlet")
public class ViewAllNotesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String sortId = request.getParameter("sortId");
        System.out.println("Sort Id: " + sortId);

        Model model = ModelFactory.getModel();
        List<Map<String, String>> notes = model.getAllNotes(sortId);

        request.setAttribute("allNotes", notes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewNotes.jsp");
        dispatcher.forward(request, response);
    }
}