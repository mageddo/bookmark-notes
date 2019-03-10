package com.mageddo.bookmarks;

import com.mageddo.commons.Maps;
import com.mageddo.thymeleaf.TemplatingUtils;
import spark.ModelAndView;

import java.util.Arrays;
import java.util.Map;

import static com.mageddo.db.DBUtils.template;
import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
//		Runtime.getRuntime().
		System.out.println("marombada" + Arrays.toString(args));

		get("/hello", (request, response) -> "Hello World!");

		post("/hello", (request, response) -> {
			Map<String, Object> model = Maps.of("name", "Elvis");
			return TemplatingUtils.template().render(new ModelAndView(model,  "index"));
		});

		get("/private", (request, response) -> {
			response.status(401);
			return "Go Away!!!";
		});

		get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

		get("/news/:section", (request, response) -> {
			response.type("text/xml");
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
		});

		get("/protected", (request, response) -> {
			halt(403, "I don't think so!!!");
			return null;
		});

		get("/redirect", (request, response) -> {
			response.redirect("/news/world");
			return null;
		});

		get("/", (request, response) -> "root");

		get("/dual", (request, response) -> {
			System.out.println("processing");
			String r = String.valueOf(template().queryForList("SELECT * FROM bsk_item").get(0));
			System.out.println("resulted");
			return r;
		});

		get("/props", (request, response) -> {
			return String.valueOf(System.getProperties());
		});
	}
}
