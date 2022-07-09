package com.clopez.chat.usermgnt;

public class Tester1 {

    public static void main(String[] args) {
        JSONDatabase db = new JSONDatabase("maindb");
        User u1 = new User("clopez", "123");

        System.out.println("Borrando DB");
        if (db.deleteDatabase())
            System.out.println("Borrando el fichero");
        else
            System.out.println("El fichero no existe");

        System.out.println("Creando usuario de pueba");
        db.CreateUser(u1);

        User u2 = db.FindUserByName("clopez");

        System.out.println("EL usuario " + u2.getName() + " tiene id " + u2.getId());

        User u3 = new User("pepito", "123");

        db.CreateUser(u3);


    }


}
