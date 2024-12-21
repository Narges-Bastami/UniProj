package com.UniProj.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/forgotpassword")
public class ForgotPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String securityAnswer = request.getParameter("securityAnswer");
        String newPwd = request.getParameter("newPwd");
        String confirmNewPwd = request.getParameter("confirmNewPwd");

        RequestDispatcher dispatcher = null;

        // Check for empty fields
        if (email == null || email.equals("") ||
                securityAnswer == null || securityAnswer.equals("") || newPwd == null || newPwd.equals("") ||
                confirmNewPwd == null || confirmNewPwd.equals("")) {

            request.setAttribute("status", "emptyFields");
            dispatcher = request.getRequestDispatcher("forgotpassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Check if new password matches confirm password
        if (!newPwd.equals(confirmNewPwd)) {
            request.setAttribute("status", "passwordMismatch");
            dispatcher = request.getRequestDispatcher("forgotpassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniproj?useSSL=false", "root", "N@rges210682");

            // Verify user email, remembered password, and security answer
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE uemail=? AND security_answer=?");
            ps.setString(1, email);
            ps.setString(2, securityAnswer);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // Update password
                PreparedStatement updatePs = con.prepareStatement(
                        "UPDATE users SET upwd=? WHERE uemail=?");
                updatePs.setString(1, newPwd);
                updatePs.setString(2, email);

                int rowsUpdated = updatePs.executeUpdate();
                if (rowsUpdated > 0) {
                    request.setAttribute("status", "successReset");
                    dispatcher = request.getRequestDispatcher("login.jsp");
                } else {
                    request.setAttribute("status", "updateFailed");
                    dispatcher = request.getRequestDispatcher("forgotpassword.jsp");

                }
            } else {
                 request.setAttribute("status", "userNotFound");
                dispatcher = request.getRequestDispatcher("forgotpassword.jsp");

            }

            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", "error");
            dispatcher = request.getRequestDispatcher("forgotpassword.jsp");
            dispatcher.forward(request, response);
        }
    }
}
