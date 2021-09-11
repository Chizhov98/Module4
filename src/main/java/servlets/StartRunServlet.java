package servlets;


import lombok.SneakyThrows;
import utils.RaceUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class StartRunServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path.matches("/\\d+")) {
            int id = Integer.parseInt(path.substring(8));
            if (RaceUtils.startNewRace(id)) resp.setStatus(200);
            else resp.setStatus(400);
        }
    }
}
