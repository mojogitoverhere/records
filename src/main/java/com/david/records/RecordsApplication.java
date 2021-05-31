package com.david.records;

import io.javalin.Javalin;

public class RecordsApplication {

  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);
    app.get("/", ctx -> ctx.result("Hello World"));
  }
}
