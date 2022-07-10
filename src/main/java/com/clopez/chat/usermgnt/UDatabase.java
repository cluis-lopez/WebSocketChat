package com.clopez.chat.usermgnt;

public interface UDatabase {
    public void createUser(User user);
    public void deleteUser(String id);
    public User findUserByName(String name);
    public User findUserById(String id);
}
