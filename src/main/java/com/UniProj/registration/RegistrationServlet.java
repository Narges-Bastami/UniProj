package com.UniProj.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register")

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();

    }

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    private boolean validatePassword(String passwordStr) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(passwordStr);
        return matcher.matches();
    }

    public static final Pattern VALID_MOBILE_NUMBER_REGEX =
            Pattern.compile("^(\\+98|0)?9\\d{9}$");

    private boolean validateMobileNumber(String mobileStr) {
        Matcher matcher = VALID_MOBILE_NUMBER_REGEX.matcher(mobileStr);
        return matcher.matches();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String Reupwd = request.getParameter("re_pass");
        String umobile = request.getParameter("contact");
        String security_answer = request.getParameter("question");

        RequestDispatcher dispatcher = null;
        Connection con = null;

        if (uname == null || uname.equals("")) {

            request.setAttribute("status", "invalidName");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);

        } else if (uemail == null || uemail.equals("") || !validate(uemail)) {

            request.setAttribute("status", "invalidEmail");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);

        } else if (upwd == null || upwd.equals("") || !validatePassword(upwd)) {

            request.setAttribute("status", "invalidUpwd");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);

        } else if (!upwd.equals(Reupwd)) {
            request.setAttribute("status", "invalidConfirmPassword");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);

        } else if (umobile == null || umobile.equals("") || !validateMobileNumber(umobile)) {

            request.setAttribute("status", "invalidMobile");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);

        }

        else if (security_answer == null || security_answer.equals("")) {

            request.setAttribute("status", "invalidAnswer");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        else {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uniproj?useSSL=false", "root", "N@rges210682");

                PreparedStatement existpst = con.prepareStatement("select * from users where uemail = ?");
                existpst.setString(1, uemail);
                ResultSet rs = existpst.executeQuery();
                if(rs.next()) {
                    request.setAttribute("status", "uExist");
                    dispatcher = request.getRequestDispatcher("registration.jsp");
                    dispatcher.forward(request, response);
                } else {

                    PreparedStatement pst = con.prepareStatement("insert into users(uname,upwd,uemail,security_answer,umobile) values (?,?,?,?,?)");
                    pst.setString(1, uname);
                    pst.setString(2, Reupwd);
                    pst.setString(3, uemail);
                    pst.setString(4, security_answer);
                    pst.setString(5, umobile);


                    int rowCount = pst.executeUpdate();
                    if (rowCount > 0) {
                        request.setAttribute("status", "success");
                        dispatcher = request.getRequestDispatcher("login.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        request.setAttribute("status", "error");
                        dispatcher = request.getRequestDispatcher("registration.jsp");
                        dispatcher.forward(request, response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
