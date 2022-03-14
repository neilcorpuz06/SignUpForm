package com.neil.registrationform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author CorpuzNe
 *
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("username");
		String upwd = request.getParameter("password");
		String SELECT_TBLUSER = "SELECT * FROM user WHERE uname = ? AND upwd = ?";
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsignup?useSSL=false", "root", "Root");
			PreparedStatement prep = conn.prepareStatement(SELECT_TBLUSER);
			
			prep.setString(1, uname);
			prep.setString(2, upwd);
			
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}else{
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
