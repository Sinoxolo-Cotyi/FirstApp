import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFiles.location("/public");
        List<String> namesList = Arrays.asList("username");

        //Copy all items from list 1 to list 2
        ArrayList<String> usernames = new ArrayList<String>(namesList);

        System.out.println(usernames);

       if (usernames.contains("username")) {
           System.out.println(usernames);
       } else {
           usernames.add("username");
           System.out.println(usernames);
       }

        get("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());
        post("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();

            // create the greeting message
            String greeting = "Hello, " + req.queryParams("username");

            // put it in the map which is passed to the template - the value will be merged into the template
            map.put("greeting", greeting);

            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());



    }
}
