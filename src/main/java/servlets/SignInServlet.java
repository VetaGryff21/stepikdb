package servlets;

import dbService.DBService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;

public class SignInServlet extends HttpServlet
{

    private DBService dbService;

    public SignInServlet(DBService dbService){
        this.dbService = dbService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            dbService.addUser("qq", "qq");
        } catch (DBException e) {
            e.printStackTrace();
        }


        long id1 = -1;
        try {
            id1 = dbService.searchByLogin("qq");
        } catch (DBException e) {
            e.printStackTrace();
        }

        long id = -1;
        try {
            id = dbService.searchByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (id == -1) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
            return;
        }

        if (id != 0) {
            try {
                UsersDataSet usersDataSet = dbService.getUser(id);
                if (usersDataSet.getPassword().equals(password)) {
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Authorized: " + login);
                    return;
                }
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }
}
