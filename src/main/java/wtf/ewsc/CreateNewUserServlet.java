package wtf.ewsc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/CreateNew")
public class CreateNewUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        password = Hasher.returnHashed(password);

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, "0");
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ignored) {

        }

        response.sendRedirect("/");
    }
}