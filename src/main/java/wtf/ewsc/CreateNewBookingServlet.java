package wtf.ewsc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/CreateBookingServlet")
public class CreateNewBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        String amount = request.getParameter("amount");
        String date = request.getParameter("date");
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "INSERT INTO bookings (booking_from, booking_date, approved, booking_amount, discount_percent) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, date);
            statement.setString(3, "0");
            statement.setString(4, amount);
            statement.setString(5, "0");
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/booking");
    }
}
