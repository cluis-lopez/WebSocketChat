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

@WebServlet("/Register")
public class Register extends HttpServlet {
    JSONDatabase db = new JSONDatabase("usersdb");
    Gson gs = new Gson();

    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> response = new HashMap<String, String>();
        response.put("code", "OK");
        response.put("user", "");
        response.put("id", "");
        response.put("token", "");

        String user = req.getParameter("user");
        String password = req.getParameter("password");

        User u = db.findUserByName(user);
        if (u == null && isValidPassword(password)){
            //El usuario no existe y la password es válida
            u = new User(user, password);
            try {
            	db.createUser(u);
            	response.put("user", u.getName());
                response.put("id", u.getId());
            } catch (IllegalArgumentException e) {
            	response.put("code", e.getMessage());
            }

        } else {
            response.put("code", "ERROR: Invalid user or Password");
        }

        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        pw.println(gs.toJson(response));
        pw.close();
    }

    private boolean isValidPassword(String password){
        if (password != null && password.length() >= 4 )
            return true;
        else
            return false;
    }
}
