package com.epam.donetc.restaurant.servlets;

import com.epam.donetc.restaurant.exeption.AppException;
import com.epam.donetc.restaurant.exeption.DBException;
import com.epam.donetc.restaurant.database.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.epam.donetc.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet  {

    private final UserService userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login.isEmpty() || password.isEmpty()){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
         }
        try{
            User user = userService.logIn(login, password);
            if(user != null){
                request.getSession().setAttribute("user", user);
                if(user.getRoleId() == 1){
                    response.sendRedirect(request.getContextPath() + "/controller?command=client_menu");
                } else {
                    response.sendRedirect(request.getContextPath() + "/controller?command=manageOrders");
                }
            } else {
                request.setAttribute("error", "true");
                response.sendRedirect(request.getContextPath() + "/login?err=");
            }
        } catch (DBException ex){
            log.error("In Login servlet doPost() ", ex);
            throw new AppException("Login error", ex);
        }
    }
}
