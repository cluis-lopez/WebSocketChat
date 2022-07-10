package com.clopez.chat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.clopez.chat.usermgnt.JSONDatabase;
import com.clopez.chat.usermgnt.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

@ServerEndpoint(value = "/Server/{payload}")
public class Server {
    private Map<String, User> sessions = new HashMap<String, User>();
    private Map<String, String> payload;
    private Type type = new TypeToken<HashMap<String, String>>() {
    }.getType();
    private final TypeAdapter<JsonElement> strictAdapter = new Gson().getAdapter(JsonElement.class);
    private Gson gson = new Gson();
    private JSONDatabase db = new JSONDatabase("usersdb");

    @OnOpen
    public void onOpen(Session session, @PathParam("payload") String pl) {
        System.out.println("Open Connection ..." + session.getId() + " Payload: " + pl);
        if (pl != null && isValidJson(pl)) {
            payload = gson.fromJson(pl, type);
            User u = db.findUserById(payload.get("id"));
            if (u != null && payload.get("token").equals(u.getToken())) {
                // Usuario Válido
                sessions.put(session.getId(), u);
                System.out.println("Usuario " + u.getName() + "Conectado");
            } else {
                System.out.println("Identificación Incorrecta " + pl);
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        } else {
            System.out.println("Parámetros inválidos " + pl);
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("Close Connection ...");
    }

    @OnMessage
    public String onMessage(String message) {
        System.out.println("Message from the client: " + message);
        String echoMsg = "Echo from the server : " + message;
        return echoMsg;
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    private boolean isValidJson(String json) {
        try {
            strictAdapter.fromJson(json);
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Invalid JSON");
            return false;
        }
        System.out.println("JSON Válido");
        return true;
    }
}
