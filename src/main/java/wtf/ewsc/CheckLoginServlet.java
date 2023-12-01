package wtf.ewsc;

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

@WebServlet("/CheckAuth")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = Hasher.returnHashed(password);
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", resultSet.getString("role"));
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/login?error=1");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
