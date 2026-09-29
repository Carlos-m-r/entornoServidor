package org.iesalixar.daw2.carlosmartel.servlets.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private String userName = "Carlos Martel";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Entering the doGet method of UserServlet.");

        request.setAttribute("userName", userName);

        request.getRequestDispatcher("views/user.jsp").forward(request, response);

        logger.info("Exiting the doGet method of UserServlet.");
    }



}
