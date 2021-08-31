package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Servlet implementation class AdminServlet
 */

public class AdminServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
		} catch (ParseException e) {
		}
		String attendanceStatus = request.getParameter("attendance");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
		Query query = new Query("User").setFilter(propertyFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Entity user = prepardQuery.asSingleEntity();
		if (user != null) {
			String studentId = user.getKey().getName().toString();

			Filter propertyFilter1 = new FilterPredicate("date", FilterOperator.EQUAL, date);

			Query query1 = new Query("AttendanceDetails").setFilter(propertyFilter1).setAncestor(user.getKey());
			PreparedQuery prepardQuery1 = datastore.prepare(query1);

			Entity result = prepardQuery1.asSingleEntity();

			Key k = new KeyFactory.Builder("User", studentId).addChild("AttendanceDetails", result.getKey().getName())
					.getKey();

			Entity e;
			try {
				e = datastore.get(k);
				e.setProperty("name", user.getProperty("name"));
				e.setProperty("date", date);
				e.setProperty("attendanceStatus", attendanceStatus);
				datastore.put(e);
				out.println("<script type=\"text/javascript\">");
				out.println("alert('attendance updated');");
				out.println("location='admin.html';");
				out.println("</script>");

			} catch (EntityNotFoundException e1) {
				e1.printStackTrace();
			}

		}
	}
}
