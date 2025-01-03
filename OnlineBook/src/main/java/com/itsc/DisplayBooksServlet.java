package com.itsc;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/displayBooks")
public class DisplayBooksServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnectionManager dbManager = new DBConnectionManager();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = dbManager.getConnection()) {
            String sql = "SELECT * FROM Books";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<table border='1'><tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

            while (resultSet.next()) {
                out.println("<tr><td>" + resultSet.getInt("id") + "</td><td>" + resultSet.getString("title") + "</td><td>" +
                        resultSet.getString("author") + "</td><td>" + resultSet.getDouble("price") + "</td></tr>");
            }

            out.println("</table></body></html>");
        } catch (Exception e) {
            throw new ServletException("Error displaying books", e);
        }
    }
}
