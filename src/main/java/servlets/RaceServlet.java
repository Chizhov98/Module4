package servlets;

import com.google.gson.Gson;
import entity.Race;
import entity.RaceList;
import utils.RaceUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RaceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        String path = req.getPathInfo();
        String teg;
        if (path.matches("/\\d+")) {
            int id = Integer.parseInt(path.substring(1));
            List<RaceList> race = RaceUtils.getRace(id);
            if (race == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setContentType("text/html");
                responseBody.println("<table><tr><th>Position</th>" +
                        "<th>Horse_id</th></tr>");
                for (RaceList list : race) {
                    if (list.isChosen()) {
                        teg = "th>";
                    } else {
                        teg = "td>";
                    }
                    responseBody.println("<tr><" + teg + list.getPosition() + "</" + teg + "<" + teg + list.getHorse().getId() + "</" + teg + "</tr");
                }
                responseBody.println("<table>");
                resp.setStatus(200);
            }
        }
    }
}