package com.david.records;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import static org.hamcrest.CoreMatchers.is;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class ServerFunctionalTest {

  /*
    TODO: figure out how to clean up server setup.
    Calling server.start(1234) in an @Before method
    and server.stop() in an @After method causes the
    http requests to fail if there are more than one
    test methods.
    An ErrorCollector is used here so that we can do
    assertions with collector.checkThat(...) and still
    be able to shutdown each server at the end of each
    test.
  */
  @Rule
  public ErrorCollector collector = new ErrorCollector();

  @Test
  public void testPOSTRecordWithPipeDelimiter() {
    Server server = new Server(new RecordsService());
    server.start(1234);
    HttpResponse<JsonNode> response = Unirest.post("http://localhost:1234/records").body("Minelli|Alister|aminelli2@webs.com|Violet|6/24/1990").asJson();
    JSONObject body = response.getBody().getObject();

    collector.checkThat(response.getStatus(), is(200));
    collector.checkThat(body.getString("firstName"), is("Alister"));
    collector.checkThat(body.getString("lastName"), is("Minelli"));
    collector.checkThat(body.getString("email"), is("aminelli2@webs.com"));
    collector.checkThat(body.getString("favoriteColor"), is("Violet"));
    collector.checkThat(body.getString("dateOfBirth"), is("6/24/1990"));
    server.stop();
  }

  @Test
  public void testPOSTRecordWithCommaDelimiter() {
    Server server = new Server(new RecordsService());
    server.start(1235);
    HttpResponse<JsonNode> response = Unirest.post("http://localhost:1235/records").body("Minelli,Alister,aminelli2@webs.com,Violet,6/24/1990").asJson();
    JSONObject body = response.getBody().getObject();

    collector.checkThat(response.getStatus(), is(200));
    collector.checkThat(body.getString("firstName"), is("Alister"));
    collector.checkThat(body.getString("lastName"), is("Minelli"));
    collector.checkThat(body.getString("email"), is("aminelli2@webs.com"));
    collector.checkThat(body.getString("favoriteColor"), is("Violet"));
    collector.checkThat(body.getString("dateOfBirth"), is("6/24/1990"));
    server.stop();
  }

  @Test
  public void testPOSTRecordWithSpaceDelimiter() {
    Server server = new Server(new RecordsService());
    server.start(1236);
    HttpResponse<JsonNode> response = Unirest.post("http://localhost:1236/records").body("Minelli Alister aminelli2@webs.com Violet 6/24/1990").asJson();
    JSONObject body = response.getBody().getObject();

    collector.checkThat(response.getStatus(), is(200));
    collector.checkThat(body.getString("firstName"), is("Alister"));
    collector.checkThat(body.getString("lastName"), is("Minelli"));
    collector.checkThat(body.getString("email"), is("aminelli2@webs.com"));
    collector.checkThat(body.getString("favoriteColor"), is("Violet"));
    collector.checkThat(body.getString("dateOfBirth"), is("6/24/1990"));
    server.stop();
  }

  @Test
  public void testGETRecordsByColor() {
    Server server = new Server(new RecordsService());
    server.start(1237);

    postNewRecord("Hastings|Lynnet|lhastings5@fotki.com|Indigo|11/29/2015", 1237);
    postNewRecord("Chisholm|Deck|dchisholm6@google.it|Teal|9/20/1980", 1237);
    postNewRecord("Torrans|Ralf|rtorrans7@newsvine.com|Purple|7/16/2001", 1237);

    HttpResponse<JsonNode> response = Unirest.get("http://localhost:1237/records/color").asJson();
    JSONObject body = response.getBody().getObject();
    collector.checkThat(body.getInt("total"), is(3));
    JSONArray records = body.getJSONArray("records");
    collector.checkThat("The first record should have a favorite color of Indigo", records.getJSONObject(0).getString("favoriteColor"), is("Indigo"));
    collector.checkThat("The second record should have a favorite color of Purple", records.getJSONObject(1).getString("favoriteColor"), is("Purple"));
    collector.checkThat("The third record should have a favorite color of Teal", records.getJSONObject(2).getString("favoriteColor"), is("Teal"));
    server.stop();
  }

  @Test
  public void testGETRecordsByBirthdate() {
    Server server = new Server(new RecordsService());
    server.start(1238);

    postNewRecord("Hastings|Lynnet|lhastings5@fotki.com|Indigo|11/29/2015", 1238);
    postNewRecord("Chisholm|Deck|dchisholm6@google.it|Teal|9/20/1980", 1238);
    postNewRecord("Torrans|Ralf|rtorrans7@newsvine.com|Purple|7/16/2001", 1238);

    HttpResponse<JsonNode> response = Unirest.get(String.format("http://localhost:%d/records/birthdate", 1238)).asJson();
    JSONObject body = response.getBody().getObject();
    collector.checkThat(body.getInt("total"), is(3));
    JSONArray records = body.getJSONArray("records");
    collector.checkThat("The first record should have a birthdate of 9/20/1980", records.getJSONObject(0).getString("dateOfBirth"), is("9/20/1980"));
    collector.checkThat("The second record should have a birthdate of 7/16/2001", records.getJSONObject(1).getString("dateOfBirth"), is("7/16/2001"));
    collector.checkThat("The third record should have a birthdate of 11/29/2015", records.getJSONObject(2).getString("dateOfBirth"), is("11/29/2015"));
    server.stop();
  }

  @Test
  public void testGETRecordsByLastName() {
    Server server = new Server(new RecordsService());
    server.start(1239);

    postNewRecord("Hastings|Lynnet|lhastings5@fotki.com|Indigo|11/29/2015", 1239);
    postNewRecord("Chisholm|Deck|dchisholm6@google.it|Teal|9/20/1980", 1239);
    postNewRecord("Torrans|Ralf|rtorrans7@newsvine.com|Purple|7/16/2001", 1239);

    HttpResponse<JsonNode> response = Unirest.get(String.format("http://localhost:%d/records/name", 1239)).asJson();
    JSONObject body = response.getBody().getObject();
    collector.checkThat(body.getInt("total"), is(3));
    JSONArray records = body.getJSONArray("records");
    collector.checkThat("The first record should have a last name of Chisholm", records.getJSONObject(0).getString("lastName"), is("Chisholm"));
    collector.checkThat("The second record should have a last name of Hastings", records.getJSONObject(1).getString("lastName"), is("Hastings"));
    collector.checkThat("The third record should have a last name of Torrans", records.getJSONObject(2).getString("lastName"), is("Torrans"));
    server.stop();
  }

  private void postNewRecord(String body, int port) {
   HttpResponse<String> response = Unirest.post(String.format("http://localhost:%d/records", port)).body(body).asString();
    if (response.getStatus() != 200) {
      fail(String.format("Aborting tests. A new record failed to POST with error code %d: %s.", response.getStatus(), response.getBody()));
    }
  }
}