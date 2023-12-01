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
import java.util.Objects;

@WebServlet("/apanel")
public class ApanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("username") == null) {
            response.sendRedirect("/login?error=3");
        }
        else {
            String userRole = (String) session.getAttribute("role");
            if (Objects.equals(userRole, "1")) {
                try {
                    Connection connection = DBConnection.getConnection();
                    String sql = "SELECT * FROM bookings ORDER BY approved";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    ResultSet resultSet = statement.executeQuery();
                    ArrayList<Booking> allBookings = new ArrayList<>();
                    while (resultSet.next()) {
                        Booking tempBooking = new Booking();
                        tempBooking.id = Integer.parseInt(resultSet.getString("id"));
                        tempBooking.discount_percent = Integer.parseInt(resultSet.getString("discount_percent"));
                        tempBooking.booking_date = resultSet.getString("booking_date");
                        tempBooking.booking_from = resultSet.getString("booking_from");
                        tempBooking.booking_amount = Integer.parseInt(resultSet.getString("booking_amount"));
                        tempBooking.booking_time = resultSet.getString("booking_time");
                        tempBooking.approved = resultSet.getString("approved");
                        allBookings.add(tempBooking);
                    }
                    statement.close();
                    connection.close();
                    request.setAttribute("allBookings", allBookings);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/apanel.jsp");
                dispatcher.forward(request, response);
            }
            else {
                response.sendRedirect("/");
            }
        }
    }
}
