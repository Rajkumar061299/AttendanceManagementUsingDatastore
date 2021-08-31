package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.apphosting.api.DatastorePb.QueryResult;

public class ParticularStudentDetails extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		String email3 = request.getParameter("email3");
		System.out.println(email1+"   "+email2+"    "+email3);
		
		List<String> list = new ArrayList<>();
		list.add(email1);
		list.add(email2);
		list.add(email3);
		list.remove(null);
		
		System.out.println(list);
		
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
		} catch (ParseException e) {
		}

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("email", FilterOperator.IN, list);
		Filter propertyFilter1 = new FilterPredicate("date", FilterOperator.EQUAL, date);

		Filter compositeFilter = CompositeFilterOperator.and(propertyFilter, propertyFilter1);
		Query query = new Query("AttendanceDetails").setFilter(compositeFilter);
		System.out.println(query);
		PreparedQuery prepardQuery = datastore.prepare(query);
		
		for (Entity e : prepardQuery.asIterable()) {
			
			out.print("<b>Name : </b>" + e.getProperty("name") + "     ");
			out.print("<b>Attendance Status : </b>" + e.getProperty("attendanceStatus"));
			out.print("<br>");
		}


	}
}
