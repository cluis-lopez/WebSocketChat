package com.clopez.chat.usermgnt;

public class Tester2 {
    public static void main(String[] args){
        JSONDatabase db = new JSONDatabase("maindb");

        User u = db.FindUserByName("clopez");

        if (u != null)
            System.out.println("El usuario " + u.getName() + " tiene Id " + u.getId());
        else
            System.out.println("Usuario no encontrado");

        System.out.println("Borrando la BBDD ");
        if (db.deleteDatabase())
            System.out.println("Fichero borrado");
        else
            System.out.println("No existe el fichero");
    }
}
