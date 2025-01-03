package com.itsc;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/searchBooks")
public class SearchBooksServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnectionManager dbManager = new DBConnectionManager();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titleQuery = request.getParameter("title");

        try (Connection connection = dbManager.getConnection()) {
            String sql = "SELECT * FROM Books WHERE title LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + titleQuery + "%");
            ResultSet resultSet = statement.executeQuery();

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<table border='1'><tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

            while (resultSet.next()) {
                out.println("<tr><td>" + resultSet.getInt("id") + "</td><td>" + resultSet.getString("title") + "</td><td>" +
                        resultSet.getString("author") + "</td><td>" + resultSet.getDouble("price") + "</td></tr>");
            }

            out.println("</table></body></html>");
        } catch (Exception e) {
            throw new ServletException("Error searching books", e);
        }
    }
}
