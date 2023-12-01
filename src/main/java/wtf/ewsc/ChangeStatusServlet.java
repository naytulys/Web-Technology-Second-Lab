package wtf.ewsc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ChangeStatusServlet")
public class ChangeStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("book_id"));
        String newApprovedStatus = request.getParameter("approved");
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "UPDATE bookings SET approved = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newApprovedStatus);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Update successful
                response.getWriter().println("Update successful.");
            } else {
                // Update failed
                response.getWriter().println("Update failed.");
            }
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/apanel");
    }
}
