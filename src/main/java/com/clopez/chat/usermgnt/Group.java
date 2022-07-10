package com.clopez.chat.usermgnt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {

    private String id;
    private String name;
    private User owner;
    private List<User> users;

    public  Group (String name, User owner) {
        this.owner = owner;
        this.name = name;
        this.id = UUID.randomUUID().toString();
        users = new ArrayList<User>();
        users.add(owner);
    }

    public void addMember(User user) throws IllegalArgumentException {
        if (user != null && !users.contains(user))
            users.add(user);
        else
            throw new IllegalArgumentException("Invalid user");
    }

    public void removeMember(User user) throws IllegalArgumentException {
        if (user != null && users.contains(user))
        users.remove(user);
    else
        throw new IllegalArgumentException("Invalid user");
    }

    public boolean isMemeber(User user){
        return users.contains(user);
    }
}
