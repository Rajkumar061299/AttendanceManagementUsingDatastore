package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Servlet implementation class FacultyServlet
 */
public class FacultyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
		} catch (ParseException e) {
		}

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		String attendanceStatus = request.getParameter("attendance");

		String studentID = request.getParameter("id");

		String studentAttendance_id = UUID.randomUUID().toString();

		Key key = KeyFactory.createKey("User", studentID);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("AttendanceDetails", studentAttendance_id, key);

		e.setProperty("name", name);
		e.setProperty("date", date);
		e.setProperty("email", email);		
		e.setProperty("attendanceStatus", attendanceStatus);

		Filter propertyFilter = new FilterPredicate("date", FilterOperator.EQUAL, date);
		Filter propertyFilter1 = new FilterPredicate("name", FilterOperator.EQUAL, name);

		Filter compositeFilter = CompositeFilterOperator.and(propertyFilter, propertyFilter1);

		Query query = new Query("AttendanceDetails").setFilter(compositeFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);

		Entity result = prepardQuery.asSingleEntity();
		if (result == null) {

			datastore.put(e);

			out.println("<script type=\"text/javascript\">");
			out.println("alert('attendance updated');");
			out.println("location='faculty.jsp';");
			out.println("</script>");

		}
		else {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('attendance already updated');");
			out.println("location='faculty.jsp';");
			out.println("</script>");
		}

	}

}
