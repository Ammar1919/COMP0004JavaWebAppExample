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

@WebServlet("/editNoteServlet")
public class EditNoteServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        String noteId = request.getParameter("noteId");
        String editType = request.getParameter("editType");
        String newContent = request.getParameter("newContent");

        System.out.println("noteId" + noteId);
        System.out.println("editType" + editType);
        System.out.println("newContent" + newContent);

        Model model = ModelFactory.getModel();

        model.editNote(noteId, editType, newContent);

        //fetch new noteId

        //Pass data to Model - editNote(noteId, editType, newContent)
        //Model determines whether to edit title or text
        //Model updates data

        if (editType.equals("0")){
            noteId = newContent;
        }

        response.sendRedirect("noteServlet?noteId="+noteId);
    }
}
