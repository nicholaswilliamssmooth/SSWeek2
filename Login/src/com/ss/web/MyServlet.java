package com.ss.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		// Declare and initialize variables
		PrintWriter pw = response.getWriter();
		String uname = request.getParameter("uname");
		String pass = request.getParameter("pass");
		
		// To keep things simple we only use one valid password
		// A full version of this would pull from a database of usernames and private passwords
		if (pass.equals("abc123")) {
			pw.print("<html>\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"ISO-8859-1\">\r\n"
					+ "<title>Welcome</title>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "<h1>Welcome home " + uname + "! </h1>\r\n"
					+ "</body>\r\n"
					+ "</html>");
		}
		else {
			pw.print("<html>\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"ISO-8859-1\">\r\n"
					+ "<title>Sorry!</title>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "<h1>Sorry! But that was the wrong password</h1>\r\n"
					+ "</body>\r\n"
					+ "</html>");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");



	}

}
