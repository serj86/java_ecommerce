package com.github.serj86.java_ecommerce.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.serj86.java_ecommerce.entities.User;
import com.github.serj86.java_ecommerce.services.UserService;

@WebServlet(urlPatterns = { "/login" })
public class UserAuthentication extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Display login page for Get requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	RequestDispatcher requestDispatcher;
	request.setAttribute("notice", "Please login using eMail and Password.");
	requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
	requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	RequestDispatcher requestDispatcher;
	HttpSession session = request.getSession();

	String email = request.getParameter("email");
	String password = request.getParameter("password");

	User userService = new UserService().getValidatedUserByEmail(email, password);
	
	if (userService != null) {
	    session.setAttribute("user", userService);
	    request.setAttribute("notice", "Welcome " + userService.getFirstName() + " " + userService.getLastName() + "!");
	    requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
	    requestDispatcher.forward(request, response);
	} else {
	    request.setAttribute("notice", "Login for '"+email+"' failed, please try again.");
	    requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
	    requestDispatcher.forward(request, response);
	}
    }

}
