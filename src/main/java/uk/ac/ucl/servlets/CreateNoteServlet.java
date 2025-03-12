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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@WebServlet("/createNoteServlet")
public class CreateNoteServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Model model = ModelFactory.getModel();

        Map<String, String> fields = new HashMap<String, String>();

        String name = request.getParameter("name");
        String text = request.getParameter("text");
        String url = request.getParameter("url");

        fields.put("name", name);
        fields.put("text", text);
        fields.put("URL", url);
        fields.put("imageURL", null);
        fields.put("timeAdded", LocalDateTime.now().toString());
        fields.put("timeEdited", LocalDateTime.now().toString());

        model.addNote("data"+File.separator+"notes.json", fields);

        System.out.println("NoteId from CNS" + name);

        response.sendRedirect("noteServlet?noteId="+name);
    }
}
