package servlet;

import dataset.Teacher;
import service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/findTeacherByDayWithoutLesson")
public class findTeacherByDayWithoutLesson extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/findTeacherByDayWithoutLesson.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String day = req.getParameter("day");


        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");

        try {
            for (Teacher r: DBService.getTeacherByDayWithoutLesson(day)) {
                out.println(r.getName());
                out.println("<br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</body>");
        out.println("</html>");
        out.close();


    }
}
