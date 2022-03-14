package com.neil.registrationform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author CorpuzNe
 *
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		Connection conn = null;
		String INSERT_INTO = "INSERT INTO  user (uname,upwd,uemail,umobile)" + "VALUES(?,?,?,?)";

		RequestDispatcher dispatcher = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsignup?useSSL=false", "root", "Root");
			PreparedStatement prep = conn.prepareStatement(INSERT_INTO);

			/* Setting the Data */
			prep.setString(1, uname);
			prep.setString(2, upwd);
			prep.setString(3, uemail);
			prep.setString(4, umobile);

			int rowCount = prep.executeUpdate();

			dispatcher = request.getRequestDispatcher("registration.jsp");

			if (rowCount > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "failed");
			}

			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();// Close the JDBC Connection.
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}

/*
 * to know if the Servlet is functional. 1st Test PrintWriter out =
 * response.getWriter(); out.print("it's Working!");
 */

/*
 * 2nd test if the output will display PrintWriter out = response.getWriter();
 * 
 * out.print(uname); out.print(uemail); out.print(upwd); out.print(umobile);
 */
