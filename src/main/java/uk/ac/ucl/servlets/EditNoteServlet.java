package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/editNoteServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class EditNoteServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        String noteId = request.getParameter("noteId");
        String editType = request.getParameter("editType");
        String newContent = request.getParameter("newContent");
        Part filePart = request.getPart("editImage");

        Model model = ModelFactory.getModel();

        model.editNote(noteId, editType, newContent, filePart);

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
