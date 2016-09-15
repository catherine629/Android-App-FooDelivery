/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cathe
 */
public class ServerServlet extends HttpServlet {

  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_RUL = "jdbc:mysql://localhost:3306/FooDelivery";

  // Database credentials
  static final String USER = "root";
  static final String PASSWORD = "root";

  private static Connection conn = null;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ServerServlet() {
  	try {
    	Class.forName(JDBC_DRIVER);
    	conn = DriverManager.getConnection(DB_RUL, USER, PASSWORD);
	  } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter writer = new PrintWriter(response.getOutputStream());
  	// parse parameters
  	String sql = request.getParameter("sql");
  	String method = request.getParameter("method");
  	// prepare sql
  	Statement stmt;
    try {
      stmt = conn.createStatement();
      // decide methods
      if (method.equals("read")){
        // execute read operation
        StringBuilder sb = new StringBuilder();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rsmd.getColumnCount();
        while (rs.next()){
          for (int i = 1; i <= columnNumber; i++){
            sb.append(rs.getString(i)).append("\t");
          }
          sb.append("\n");
        }
        System.out.println(sb.toString());
        writer.write(sb.toString());
      } else {
        // execute update operation
        int result = stmt.executeUpdate(sql);
        System.out.println(result);
        if (result == 0){
            writer.write("False");
        } else {
            writer.write("True");
        }
      }
    } catch (SQLException e) {
      writer.write(e.getMessage());
      e.printStackTrace();
    } finally {
      if (writer != null) {
          writer.close();
      }
    }
  }
}
