package com.clopez.chat.usermgnt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UDatabaseTest {

    private static JSONDatabase db;
    private static String id1, id2, id3;
    private static User u1, u2, u3;

    @BeforeClass
    public static void setUp() throws Exception {
        db = new JSONDatabase("testdb");
        u1 = new User("clopez", "123");
        u2 = new User("pepito", "456");
        u3 = new User("juanito", "789");
        id1 = u1.getId();
        id2 = u2.getId();
        id3 = u3.getId();

        db.createUser(u1);
        db.createUser(u2);
        db.createUser(u3);
    }

    @Test
    public void TestUsersFindById() {
        assertEquals("ById " + u1.getName(), (db.findUserById(id1)).getName(), u1.getName());
        assertEquals("ById " + u2.getName(), (db.findUserById(id2)).getName(), u2.getName());
        assertEquals("ById " + u3.getName(), (db.findUserById(id3)).getName(), u3.getName());
    }

    @Test
    public void TestUsersFindByName() {
        assertEquals("ByName " + u1.getName(), (db.findUserByName("clopez")).getId(), id1);
        assertEquals("ByName " + u2.getName(), (db.findUserByName("pepito")).getId(), id2);
        assertEquals("ByName " + u3.getName(), (db.findUserByName("juanito")).getId(), id3);
    }

    @Test
    public void DeleteUser() {
        db.deleteUser(u2.getId());
        assertEquals("El usuario " + u2.getName() + "No debería existir", db.findUserById(u2.getId()), null);
    }

    @Test
    public void Passwords(){
        assertEquals("Password clopez debe de ser 123", true, u1.passwordMatch("123"));
        assertEquals("clopez no tiene password hola", false, u1.passwordMatch("hola"));
        assertEquals("Password juanito debe de ser 789", true, u3.passwordMatch("789"));
        }

    @Test
    public void Tokens(){
        assertEquals("El token de clopez debe de durar 30 días, valido hasta " + u1.getTokenValidUpTo(), true, u1.isTokenValid());
        /*long diff = u1.getTokenValidUpTo().getTime() - new Date().getTime();
        long teorico = 30*24*3600*1000;
        System.out.println("Diff : " + diff + " Teorico (30 dias)  : " + teorico);
        assertTrue("El token deben de servir para +/- 30 * 24 * 3600 * 1000 milisegundos", diff < (30*24*3600*1000)+5000 && diff > (30*24*3600*1000)-5000);
        */
    }


    @Test (expected = IllegalArgumentException.class)
    public void DuplicateUser() {
        db.createUser(u1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void DeleteInvalidUser() {
        db.deleteUser("invalido");
    }

    @AfterClass
    public static void CleanTests() {
        assertEquals("Borrando la BBDD", true, db.deleteDatabase());
    }

}