package servlet;


import service.DBException;
import service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String add = req.getParameter("add");

        switch (add) {
            case "teacher":
                try {
                    DBService.addTeacher(name);
                } catch (DBException e) {
                    e.printStackTrace();
                }
                break;
            case "subject":
                try {
                    DBService.addSubject(name);
                } catch (DBException e) {
                    e.printStackTrace();
                }
                break;
            case "room":
                try {
                    DBService.addRoom(name);
                } catch (DBException e) {
                    e.printStackTrace();
                }
                break;
        }
        resp.sendRedirect("home");
    }
}
