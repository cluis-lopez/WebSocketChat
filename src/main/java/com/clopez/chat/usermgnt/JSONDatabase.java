package com.clopez.chat.usermgnt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


public class JSONDatabase implements UDatabase{
    private HashMap<String, User> users;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String datafile;

    public JSONDatabase(String filename) {
        this.datafile = filename;
        users = new HashMap<String, User>();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(filename));
            Type type = new TypeToken<HashMap<String, User>>(){}.getType();
            users = gson.fromJson(reader, type);
            reader.close();
            System.out.println("La base de datos contiene " + users.size() + " usuarios");
        } catch (FileNotFoundException e) {
            System.out.println("Warning: no existe el fichero de datos");
        } catch (IOException e) {
            System.out.println("Warning: fichero con problemas");
            e.printStackTrace();
        }
    }

    public void saveDatabase(){
        try {
            FileWriter fw = new FileWriter(datafile);
            gson.toJson(users, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteDatabase(){
        File file = new File(datafile);
        System.out.println("El fichero: " +file.getAbsolutePath() + " se va a borrar");
        return file.delete();
    }

    public void CreateUser(User u) throws IllegalArgumentException {
        if (FindUserByName(u.getName()) != null) // El usuario ya existe
            throw new IllegalArgumentException("Usuario ya existente");
        users.put(u.getId(), u);
        saveDatabase();
    }

    public void DeleteUser(String id) throws IllegalArgumentException {
        if (!users.containsKey(id)) //Invalid user
            throw new IllegalArgumentException("El usuario no existe");
        else {
            users.remove(id);
            saveDatabase();
        }
    }

    public User FindUserById(String id){
        if (users.containsKey(id))
            return users.get(id);
        else  
            return null;
    }

    public User FindUserByName(String name){
        User u;
        for (String id : users.keySet()){
            u = users.get(id);
            if (u.getName().equals(name))
                return u;
        }
        return null;
    }
}
