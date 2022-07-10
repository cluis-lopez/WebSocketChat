package com.clopez.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clopez.chat.usermgnt.JSONDatabase;
import com.clopez.chat.usermgnt.User;
import com.google.gson.Gson;

@WebServlet("/Login")
public class Login extends HttpServlet {

    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONDatabase users = new JSONDatabase("usersdb");
        Gson gs = new Gson();

        String user =req.getParameter("user");
        String password = req.getParameter("password");
        Map<String, String> response = new HashMap<String, String>();
        response.put("code", "OK");
        response.put("user", "");
        response.put("id", "");
        response.put("token", "");
        

        User u = users.findUserByName(user);

        if (u != null)
            if (u.passwordMatch(password)){
                response.put("user", u.getName());
                response.put("id", u.getId());
                response.put("token", u.getToken());
            }
            else {
                response.put("code", "Invalid Password");
            }
        else {
            response.put("code", "Invalid User");
        }
            
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        pw.println(gs.toJson(response));
        pw.close();
    }
}
