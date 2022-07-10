package com.clopez.chat.usermgnt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class User {
    private String name;
    private String id;
    private String password;
    private String token;
    private Date token_valid_upTo;

    public User(String name, String password) throws IllegalArgumentException {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        String temp = encryptPassword(password);
        if (temp == "")
            throw new IllegalArgumentException("Invalid Password");
        else 
            this.password = temp;

        generateToken(30);
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return name;
    }

    public String getToken(){
        return token;
    }

    public Date getTokenValidUpTo(){
        return token_valid_upTo;
    }

    public boolean isTokenValid(){
        if (token_valid_upTo.compareTo(new Date()) > 0)
            return true;
        else
            return false;
    }

    public void generateToken(int days){
        Date d = new Date();
        long milliseconds = (long)days * 24 * 3600 * 1000;
        d.setTime(d.getTime()+ milliseconds);
        this.token_valid_upTo = d;
        this.token = UUID.randomUUID().toString();
    }

    public boolean passwordMatch(String password){
        if (encryptPassword(password).equals(this.password))
            return true;
        else
            return false;
    }

    private String encryptPassword(String clearPassword) {
        StringBuilder hexString = new StringBuilder();
        byte[] temp = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            temp = digest.digest(clearPassword.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < temp.length; i++) {
            hexString.append(Integer.toHexString(0xff & temp[i]));
        }
        return hexString.toString();
    }
}
