package servlets;

import com.google.gson.Gson;
import entity.ProfileInfo;
import utils.RaceUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        ProfileInfo profile = RaceUtils.getStats();
        resp.setContentType("application/json");
        var body = new Gson().toJson(profile);
        responseBody.println(body);
        resp.setStatus(200);
    }
}
