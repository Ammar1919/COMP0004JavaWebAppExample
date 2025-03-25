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
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@WebServlet("/createNoteServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class CreateNoteServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Model model = ModelFactory.getModel();

        Map<String, String> fields = new HashMap<>();

        String name = request.getParameter("name");
        String text = request.getParameter("text");

        //Image change here
        Part filePart = request.getPart("image");

        String imageURL = null;
        if (filePart != null && filePart.getSize() > 0) {
            imageURL = model.saveImage(filePart);
        }

        fields.put("name", name);
        fields.put("text", text);
        fields.put("imageURL", imageURL);
        fields.put("timeAdded", LocalDateTime.now().toString());
        fields.put("timeEdited", LocalDateTime.now().toString());

        model.addNote(fields);

        response.sendRedirect("noteServlet?noteId="+name);
    }
}
