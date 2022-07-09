package com.clopez.chat.usermgnt;

import static org.junit.Assert.assertEquals;

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

        db.CreateUser(u1);
        db.CreateUser(u2);
        db.CreateUser(u3);
    }

    @Test
    public void TestUsersFindById() {
        assertEquals("ById " + u1.getName(), (db.FindUserById(id1)).getName(), u1.getName());
        assertEquals("ById " + u2.getName(), (db.FindUserById(id2)).getName(), u2.getName());
        assertEquals("ById " + u3.getName(), (db.FindUserById(id3)).getName(), u3.getName());
    }

    @Test
    public void TestUsersFindByName() {
        assertEquals("ByName " + u1.getName(), (db.FindUserByName("clopez")).getId(), id1);
        assertEquals("ByName " + u2.getName(), (db.FindUserByName("pepito")).getId(), id2);
        assertEquals("ByName " + u3.getName(), (db.FindUserByName("juanito")).getId(), id3);
    }

    @Test
    public void DeleteUser() {
        db.DeleteUser(u2.getId());
        assertEquals("El usuario " + u2.getName() + "No debería existir", db.FindUserById(u2.getId()), null);
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
    }

    @Test (expected = IllegalArgumentException.class)
    public void DuplicateUser() {
        db.CreateUser(u1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void DeleteInvalidUser() {
        db.DeleteUser("invalido");
    }

    @AfterClass
    public static void CleanTests() {
        assertEquals("Borrando la BBDD", true, db.deleteDatabase());
    }

}