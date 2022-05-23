package ru.netology.javacore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;


public class TodoServer {
    private final int port;
    private final Todos todos;

    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() {

        System.out.println ("Starting server at " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket (port)) {
            while (true) { // в цикле(!) принимаем подключения
                try (Socket clientSocket = serverSocket.accept (); // // стартуем сервер
                     PrintWriter out = new PrintWriter (clientSocket.getOutputStream (), true);
                     BufferedReader in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()))) {

                    String jsonTask = in.readLine ();
                    Map<String, String> map = jsonToMap (jsonTask);

                    String action = "", task = "";
                    for (Map.Entry<String, String> entry : map.entrySet ()) {
                        String key = entry.getKey ();
                        String value = entry.getValue ();
                        if ( key.equals ("type") ) {
                            action = value;
                        } else {
                            task = value;
                        }
                    }

                    if ( action.equals ("ADD") ) {
                        todos.addTask (task);
                    } else if ( action.equals ("REMOVE") ) {
                        todos.removeTask (task);
                    }

                    out.println (todos.getAllTasks ());

                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        } catch (IOException e) {
            System.out.println ("Не могу стартовать сервер");
            e.printStackTrace ();
        }
    }

    private static Map<String, String> jsonToMap(String jsonText) {

        Type mapType = new TypeToken<Map<String, String>> () {
        }.getType ();
        GsonBuilder builder = new GsonBuilder ();
        Gson gson = builder.create ();
        return gson.fromJson (jsonText, mapType);
    }
}