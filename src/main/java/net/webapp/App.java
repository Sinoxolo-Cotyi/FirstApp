package net.webapp;


import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.*;
import java.util.List;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        String dbDiskURL = "jdbc:h2:file:./greetdb";
        //     String dbMemoryURL = "jdbc:h2:mem:greetdb";

        Jdbi jdbi = Jdbi.create(dbDiskURL, "sa", "");

        // get a handle to the database
        Handle handle = jdbi.open();
        // create the table if needed
        handle.execute("create table if not exists greet ( id integer identity, name varchar(50), counter int )");


        port(getHerokuAssignedPort());
        staticFiles.location("/public");
        List<String> namesList = new ArrayList<>();

        //Copy all items from list 1 to list 2
        ArrayList<String> usernames = new ArrayList<String>(namesList);
        get("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());
        post("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();
            String username = req.queryParams("username");
            // create the greeting message
            String greeting = null;
            String language = req.queryParams("Language");

            switch (language) {
                case "IsiXhosa" :
                    greeting = "Molo, " + username;
                    break;
                case "English" :
                    greeting = "Hello, " + username;
                    break;
                case "French" :
                    greeting = "Bonjour, " + username;
                    break;
                default: greeting = "Please select a language!!!";
            }
            if (!usernames.contains(username)) {
                usernames.add(username);
            }
            // put it in the map which is passed to the template - the value will be merged into the template
            map.put("buttons",language);
            map.put("greeting", greeting);
            map.put("number", usernames.size());
            map.put("users", usernames);

            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());



    }
}
