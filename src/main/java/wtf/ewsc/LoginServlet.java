package wtf.ewsc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("username") != null) {
            response.sendRedirect("/");;
        }
        else {
            String error = null;
            if (Objects.equals(request.getParameter("error"), "1")) {
                error = "Wrong username or password.";
            }
            else if (Objects.equals(request.getParameter("error"), "2")) {
                error = "Log in to make bookings.";
            }
            else if (Objects.equals(request.getParameter("error"), "3")) {
                error = "Log in to make access admin panel.";
            }
            request.setAttribute("error", error);
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}

