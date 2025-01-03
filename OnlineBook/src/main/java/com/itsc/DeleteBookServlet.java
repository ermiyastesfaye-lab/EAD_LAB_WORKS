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

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    private DBConnectionManager dbManager = new DBConnectionManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = dbManager.getConnection()) {
            String sql = "DELETE FROM Books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            int rowsAffected = statement.executeUpdate();

            PrintWriter out = response.getWriter();
            if (rowsAffected > 0) {
                out.println("Book deleted successfully!");
            } else {
                out.println("Book not found!");
            }
        } catch (Exception e) {
            throw new ServletException("Error deleting book", e);
        }
    }
}

