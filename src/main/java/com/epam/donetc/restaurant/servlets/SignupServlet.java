package com.epam.donetc.restaurant.servlets;


import com.epam.donetc.restaurant.database.entity.User;
import com.epam.donetc.restaurant.exeption.AppException;
import com.epam.donetc.restaurant.exeption.DBException;
import com.epam.donetc.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignupServlet", value = "/signup")
public class SignupServlet extends HttpServlet  {
    private final UserService userService;

    public SignupServlet() {
        this.userService = new UserService();
    }

    private static final Logger log = LogManager.getLogger(SignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if(login.isEmpty()|| password.isEmpty()){
            response.sendRedirect(request.getContextPath()+ "/signup");
            return;
        }
        try{
            if(userService.getUserByLogin(login) != null){
                request.setAttribute("err", "true");
                response.sendRedirect(request.getContextPath() + "/signup");
                return;
               }
            User user = userService.signUp(login, password,email);
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/menu");
        } catch (DBException ex) {
            log.error("In signup servlet doPost() ", ex);
            throw new AppException(ex);
        }
    }
}