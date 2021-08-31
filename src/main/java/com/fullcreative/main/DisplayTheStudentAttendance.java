package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Servlet implementation class DisplayTheStudentAttendance
 */
public class DisplayTheStudentAttendance extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
		} catch (ParseException e) {
		}

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter propertyFilter = new FilterPredicate("date", FilterOperator.EQUAL, date);
		Query query = new Query("AttendanceDetails").setFilter(propertyFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Iterator<Entity> listOfStudent = prepardQuery.asIterator();
		while (listOfStudent.hasNext()) {
			Entity e = listOfStudent.next();
			out.print("<b>Name : </b>" +e.getProperty("name")+"     ");
			out.print("<b>Attendance Status : </b>" + e.getProperty("attendanceStatus"));
			out.print("<br>");


		}
	}

}
