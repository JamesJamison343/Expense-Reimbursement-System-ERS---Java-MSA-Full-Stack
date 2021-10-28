package com.project1.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project1.log.LogHelper;

@SuppressWarnings("serial")
public class ViewServlet extends HttpServlet {
	
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
			req.getRequestDispatcher(ViewDispatcher.process(req)).forward(req, res);
			
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
			
			req.getRequestDispatcher(ViewDispatcher.process(req)).forward(req, res);
			
		}
		
	
}
