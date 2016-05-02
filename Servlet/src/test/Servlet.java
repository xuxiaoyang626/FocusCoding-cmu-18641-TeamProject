package test;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Servlet", urlPatterns = {"/"})
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = -1915463532411657451L;
    private static final String PROGRESS_URL = "https://leetcode.com/progress/";
    /**
     * overwrite the doGet method.
     * @param request HttpServletrequest object
     * @param response HttpServletResponse object
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        //get USERNAME and PSW from Get request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        ConnLeetCode connection = new ConnLeetCode();
        
        if (connection.loginLeetcode(username, password)) {
            connection.fetchPage(PROGRESS_URL);
            int ac_count = connection.getACCount();
            out.print(ac_count);
        } else {
            out.print(-1);
        }
    }
}
