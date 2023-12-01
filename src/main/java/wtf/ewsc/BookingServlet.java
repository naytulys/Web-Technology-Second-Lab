package wtf.ewsc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("username") == null) {
            response.sendRedirect("/login?error=2");;
        }
        else {
            try {
                Connection connection = DBConnection.getConnection();
                String sql = "SELECT * FROM bookings WHERE booking_from = ? ORDER BY id DESC";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, (String) session.getAttribute("username"));
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Booking> resultList = new ArrayList<>();

                while (resultSet.next()) {
                    Booking tempBooking = new Booking();
                    tempBooking.booking_date = resultSet.getString("booking_date");
                    tempBooking.id = Integer.parseInt(resultSet.getString("id"));
                    tempBooking.discount_percent = Integer.parseInt(resultSet.getString("discount_percent"));
                    tempBooking.booking_from = resultSet.getString("booking_from");
                    tempBooking.booking_amount = Integer.parseInt(resultSet.getString("booking_amount"));
                    tempBooking.booking_time = resultSet.getString("booking_time");
                    tempBooking.approved = resultSet.getString("approved");
                    resultList.add(tempBooking);
                }

                statement.close();
                connection.close();

                request.setAttribute("resultList", resultList);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/booking.jsp");
            dispatcher.forward(request, response);
        }
    }
}
