package com.david.records;

import com.david.records.RecordsService.SortBy;
import com.david.records.RecordsService.SortDirection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class Server {

  private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(String.format("%d/%d/%d", src.getMonthValue(), src.getDayOfMonth(), src.getYear()));
    }
  }).create();

  private final Javalin app = Javalin.create();

  private final RecordsService service;

  public Server(RecordsService service) {
    this.service = service;
    app.post("/records", this::addRecord);
    app.get("/records/color", ctx -> getRecords(ctx, SortBy.FAVORITE_COLOR, SortDirection.ASCENDING));
    app.get("/records/birthdate", ctx -> getRecords(ctx, SortBy.DATE_OF_BIRTH, SortDirection.ASCENDING));
    app.get("/records/name", ctx -> getRecords(ctx, SortBy.LAST_NAME, SortDirection.ASCENDING));
  }

  private void addRecord(Context ctx) {
    String rawRecord = ctx.body();
    Record record = Parser.parse(rawRecord);
    if (record == null) {
      ctx.status(400).result("Failed to parse malformed record. The record must ");
      return;
    }

    service.addRecord(record);
    ctx.status(200).result(gson.toJson(record));
  }

  private void getRecords(Context ctx, SortBy sortBy, SortDirection sortDirection) {
    List<Record> records = service.getAllRecords(sortBy, sortDirection);
    ctx.status(200).result(gson.toJson(new QueryResponse(records)));
  }

  public void start(int port) {
    app.start(port);
  }

  public void stop() {
    app.stop();
  }

  // This class is only used by GSON to create the JSON response for queries
  private class QueryResponse {
    private List<Record> records;
    private int total;

    public QueryResponse(List<Record> records) {
      this.records = records;
      this.total = records.size();
    }
  }

}
