//"Ermiyas Tesfaye Ayalew (UGR/6782/14)"

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

@WebServlet("/registerBook")
public class BookRegistrationServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnectionManager dbManager = new DBConnectionManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("price"));

        try (Connection connection = dbManager.getConnection()) {
            String sql = "INSERT INTO Books (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setDouble(3, price);
            statement.executeUpdate();

            PrintWriter out = response.getWriter();
            out.println("Book registered successfully!");
        } catch (Exception e) {
            throw new ServletException("Error registering book", e);
        }
    }
}
