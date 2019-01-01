package servlet;

import service.DBException;
import service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/addLesson")
public class AddLessonServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String teacher = req.getParameter("teacher");
        String subject = req.getParameter("subject");
        String room = req.getParameter("room");
        String day = req.getParameter("day");
        try {
            DBService.addLesson(subject,teacher,room,day);
        } catch (DBException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("home");
    }
}
