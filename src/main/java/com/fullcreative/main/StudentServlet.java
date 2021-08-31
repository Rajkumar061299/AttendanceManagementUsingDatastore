package com.fullcreative.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Servlet implementation class StudentServlet
 */
public class StudentServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		Key id = (Key)request.getSession().getAttribute("userId");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("role", FilterOperator.EQUAL, "student");
		Query query = new Query("User").setFilter(propertyFilter).setAncestor(id);
		PreparedQuery prepardQuery = datastore.prepare(query);
		
		Iterator<Entity> lisOfAttendance = prepardQuery.asIterator();
		
		out.println("Welcome");
		while(lisOfAttendance.hasNext()) { 
			Entity e = lisOfAttendance.next();
			out.print("<b>Date : </b>"+ e.getProperty("date") + "   ");
			out.print("<b>Date : </b>"+e.getProperty("attendanceStatus")+"<br>");
		}
	}
}
