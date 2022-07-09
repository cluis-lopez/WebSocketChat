package com.clopez.chat.usermgnt;

public interface UDatabase {
    public void CreateUser(User user);
    public void DeleteUser(String id);
    public User FindUserByName(String name);
    public User FindUserById(String id);
}
