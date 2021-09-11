package servlets;

import entity.RaceList;
import lombok.SneakyThrows;
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
            List<RaceList> race = RaceUtils.getRaceInfo(id);
            if (race == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setContentType("text/html");
                responseBody.println("<h1>Id:" + id + "</h1><h2>Date:" + RaceUtils.getRace(id).getDate() + "</h2>");
                responseBody.println("<table border=\"1\"><tr><th>Position</th>" +
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
        } else if (path.matches("/start/\\d+")) {
            doPost(req, resp);
        } else if (path.matches("/start+")) {
            startPage(req, resp);
        } else {
            resp.setStatus(404);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        int id = Integer.parseInt(path.substring(7));
        if (RaceUtils.startNewRace(id)) resp.setStatus(200);
        else resp.setStatus(400);
    }

    @SneakyThrows
    protected void startPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        int[] horsesId = RaceUtils.getAllHorsesId();
        resp.setContentType("text/html");
        responseBody.println("<h3>Chose your horse to start: </h3>\n");
        for (int i : horsesId) {
            responseBody.print("<button ><a href=\"http://localhost:8008/race/start/" + i + "\">Horse " + i + "</a></button>\n");
        }
        resp.setStatus(200);
    }
}