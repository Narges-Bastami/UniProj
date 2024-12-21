package com.UniProj.registration;

import com.mysql.cj.protocol.Resultset;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        if(uemail == null || uemail.equals("")) {

            request.setAttribute("status", "invalidEmail");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }

        if(upwd == null || upwd.equals("")) {

            request.setAttribute("status", "invalidUpwd");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uniproj?useSSL=false","root","N@rges210682");
            PreparedStatement ps = con.prepareStatement("select * from users where uemail=? and upwd=?");
            ps.setString(1, uemail);
            ps.setString(2, upwd);

            Resultset rs = (Resultset) ps.executeQuery();
            if(((java.sql.ResultSet) rs).next()) {
                session.setAttribute("name", ((java.sql.ResultSet) rs).getString("uname"));
                dispatcher = request.getRequestDispatcher("index.jsp");
            } else{
                request.setAttribute("status","failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
