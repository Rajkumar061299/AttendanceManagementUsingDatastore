package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * Servlet implementation class CreateNewUserServlet
 */
public class CreateNewUserServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String action = request.getParameter("submit");

		if (action.equalsIgnoreCase("Add student")) {
			String name = request.getParameter("sdtname");
			String password = request.getParameter("sdtpass");
			String email = request.getParameter("sdtemail");

			addStudent(name, password, email);
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Student added Sucessfully');");
			out.println("location='admin.html';");
			out.println("</script>");
		}

		if (action.equalsIgnoreCase("Add Faculty"))

		{
			String name = request.getParameter("facname");
			String password = request.getParameter("facpass");
			String email = request.getParameter("facemail");

			addFaculty(name, password, email);
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Faculty added Sucessfully');");
			out.println("location='admin.html';");
			out.println("</script>");
		}

	}

	private void addFaculty(String name, String password, String email) {
		
		String user_id = UUID.randomUUID().toString();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("User", user_id);
		e.setProperty("name", name);
		e.setProperty("email", email);
		e.setProperty("password", password);
		e.setProperty("role", "faculty");
		datastore.put(e);

	}

	private void addStudent(String name,String password, String email) {

		String user_id = UUID.randomUUID().toString();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("User", user_id);
		e.setProperty("name", name);
		e.setProperty("email", email);
		e.setProperty("password", password);
		e.setProperty("role", "student");
		datastore.put(e);
	}

}
