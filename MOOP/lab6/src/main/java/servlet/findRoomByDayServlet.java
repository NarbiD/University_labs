package servlet;

import dataset.Day;
import dataset.Room;
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

@WebServlet("/findRoomByDayServlet")
public class findRoomByDayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/findRoomByDayServlet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String day = req.getParameter("day");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        for (Room r: DBService.getRoomByDay(day)) {
            out.println(r.getId());
            out.println("<br>");
        }
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
