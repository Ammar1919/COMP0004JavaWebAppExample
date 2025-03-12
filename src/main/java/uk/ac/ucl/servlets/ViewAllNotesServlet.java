package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/allNotesServlet")
public class ViewAllNotesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        List<Map<String, String>> notes = model.getAllNotes();

        request.setAttribute("allNotes", notes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewNotes.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String sortId = request.getParameter("sortId");

        Model model = ModelFactory.getModel();
        List<Map<String, String>> notes = model.getAllNotes();
        List<Map<String, String>> sortedNotes = new ArrayList<>();

        if (sortId.equals("0")){
            for (Map<String, String> note : notes){
                LocalDateTime timeAdded = (LocalDateTime) note.get("timeAdded");
                if (sortedNotes.isEmpty()){
                    
                }
            }
        }
    }
}