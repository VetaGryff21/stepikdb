package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.DBException;
import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
//    private final AccountService accountService;
//
//    public SignUpServlet(AccountService accountService)
//    {
//        this.accountService = accountService;
//    }

    DBService dbService = new DBService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //поиск юзера в базе по логину.
        //если нет - создаем юзера
        //если есть - пишем, что есть

        long id = 0;
        try {
            id = dbService.searchByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (id == 0) {
            try {
                dbService.addUser(login, password);
            } catch (DBException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Added");
            return;
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("User with login " + login + "is exists");
            return;
        }
    }
}
