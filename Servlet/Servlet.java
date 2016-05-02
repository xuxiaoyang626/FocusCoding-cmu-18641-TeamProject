package test;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Servlet", urlPatterns = {"/"})
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = -1915463532411657451L;
    /**
     * overwrite the doGet method.
     * @param request HttpServletrequest object
     * @param response HttpServletResponse object
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("hello world");
    }
}
