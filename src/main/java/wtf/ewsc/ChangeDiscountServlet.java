package wtf.ewsc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ChangeDiscountServlet")
public class ChangeDiscountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("book_id"));
        int  newDiscount = Integer.parseInt(request.getParameter("discount"));
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "UPDATE bookings SET discount_percent = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newDiscount);
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
