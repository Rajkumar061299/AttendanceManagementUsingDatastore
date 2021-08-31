package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Servlet implementation class ValidateServlet
 */
public class ValidateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query query = new Query("User").setFilter(new FilterPredicate("email", FilterOperator.EQUAL, email));
		System.out.println(query);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Entity user = prepardQuery.asSingleEntity();

		if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {

			response.sendRedirect("admin.html");
		}

		if (user != null) {

			if (user.getProperty("password").equals(password)) {
				
				request.getSession().setAttribute("userId", user.getKey().getName().toString());
				if (user.getProperty("role").equals("faculty"))
					response.sendRedirect("faculty.jsp");
				else {
					response.sendRedirect("StudentServlet");
				}
			}
			else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('password was incorrect');");
				out.println("location='login.html';");
				out.println("</script>");
			}
		}
		else {
			out.println("<h1>User was empty</h1>");
		}
		
		
	}

}
