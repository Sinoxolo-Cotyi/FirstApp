import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFiles.location("/public");
        /*get("/greet/:username", ((request, response) -> {
            return "Molo: " + request.params(":username");
        }));
        post("/greet/:username", ((request, response) -> {
            return "Hello, " + request.queryParams("username");
        }));
*/
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
