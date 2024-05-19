
package com.belhard.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private AnnotationConfigApplicationContext appContext;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            String command = req.getParameter("command");
            AnnotationConfigApplicationContext appContext = AppListner.getContext();
            Command commandInstance = appContext.getBean(command, Command.class);
            String page = commandInstance.execute(req);
            req.getRequestDispatcher(page).forward(req,resp);
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            log.error(e.getMessage());
            PrintWriter out = resp.getWriter();
            out.println("<h1>Error</h1>");
            out.println("<p> Invalid request </p>");

        } catch (NotFoundException e) {
            resp.setStatus(404);
            log.error(e.getMessage());
            PrintWriter out = resp.getWriter();
            out.println("<h1>Error</h1>");
            out.println("<p>" + e.getMessage() + " </p>");

        } catch (Exception e) {
            resp.setStatus(500);
            log.error(e.getMessage());
            PrintWriter out = resp.getWriter();
            out.println("<h1>Error</h1>");
            out.println("<p> The disaster on the swing </p>");
        }
    }
}
