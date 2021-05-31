package com.david.records;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ServerFunctionalTest {

  private Server server;

  @BeforeClass
  public static void classSetup() {
    // Use this base url for all the tests
    Unirest.config().defaultBaseUrl("http://localhost:1234");
  }

  @Before
  public void setup() {
    server = new Server(new RecordsService());
    server.start(1234);
  }

  @After
  public void teardown() {
    server.stop();
  }

  @Test
  public void testPOSTRecordWithPipeDelimiter() {
    HttpResponse<JsonNode> response = Unirest.post("/records").body("Minelli|Alister|aminelli2@webs.com|Violet|6/24/1990").asJson();
    JSONObject body = response.getBody().getObject();

    assertThat(response.getStatus(), is(200));
    assertThat(body.getString("firstName"), is("Alister"));
    assertThat(body.getString("lastName"), is("Minelli"));
    assertThat(body.getString("email"), is("aminelli2@webs.com"));
    assertThat(body.getString("favoriteColor"), is("Violet"));
    assertThat(body.getString("dateOfBirth"), is("6/24/1990"));
  }

  @Test
  public void testPOSTRecordWithCommaDelimiter() {
    HttpResponse<JsonNode> response = Unirest.post("/records").body("Minelli,Alister,aminelli2@webs.com,Violet,6/24/1990").asJson();
    JSONObject body = response.getBody().getObject();

    assertThat(response.getStatus(), is(200));
    assertThat(body.getString("firstName"), is("Alister"));
    assertThat(body.getString("lastName"), is("Minelli"));
    assertThat(body.getString("email"), is("aminelli2@webs.com"));
    assertThat(body.getString("favoriteColor"), is("Violet"));
    assertThat(body.getString("dateOfBirth"), is("6/24/1990"));
  }

  @Test
  public void testPOSTRecordWithSpaceDelimiter() {
    HttpResponse<JsonNode> response = Unirest.post("/records").body("Minelli Alister aminelli2@webs.com Violet 6/24/1990").asJson();
    JSONObject body = response.getBody().getObject();

    assertThat(response.getStatus(), is(200));
    assertThat(body.getString("firstName"), is("Alister"));
    assertThat(body.getString("lastName"), is("Minelli"));
    assertThat(body.getString("email"), is("aminelli2@webs.com"));
    assertThat(body.getString("favoriteColor"), is("Violet"));
    assertThat(body.getString("dateOfBirth"), is("6/24/1990"));
  }

  @Test
  public void testGETRecordsByColor() {
    postNewRecord("Hastings|Lynnet|lhastings5@fotki.com|Indigo|11/29/2015");
    postNewRecord("Chisholm|Deck|dchisholm6@google.it|Teal|9/20/1980");
    postNewRecord("Torrans|Ralf|rtorrans7@newsvine.com|Purple|7/16/2001");

    HttpResponse<JsonNode> response = Unirest.get("/records/color").asJson();
    JSONObject body = response.getBody().getObject();
    assertThat(body.getInt("total"), is(3));
    JSONArray records = body.getJSONArray("records");
    assertThat("The first record should have a favorite color of Indigo", records.getJSONObject(0).getString("favoriteColor"), is("Indigo"));
    assertThat("The second record should have a favorite color of Purple", records.getJSONObject(1).getString("favoriteColor"), is("Purple"));
    assertThat("The third record should have a favorite color of Teal", records.getJSONObject(2).getString("favoriteColor"), is("Teal"));
  }

  @Test
  public void testGETRecordsByBirthdate() {
    postNewRecord("Hastings|Lynnet|lhastings5@fotki.com|Indigo|11/29/2015");
    postNewRecord("Chisholm|Deck|dchisholm6@google.it|Teal|9/20/1980");
    postNewRecord("Torrans|Ralf|rtorrans7@newsvine.com|Purple|7/16/2001");

    HttpResponse<JsonNode> response = Unirest.get("/records/birthdate").asJson();
    JSONObject body = response.getBody().getObject();
    assertThat(body.getInt("total"), is(3));
    JSONArray records = body.getJSONArray("records");
    assertThat("The first record should have a birthdate of 9/20/1980", records.getJSONObject(0).getString("dateOfBirth"), is("9/20/1980"));
    assertThat("The second record should have a birthdate of 7/16/2001", records.getJSONObject(1).getString("dateOfBirth"), is("7/16/2001"));
    assertThat("The third record should have a birthdate of 11/29/2015", records.getJSONObject(2).getString("dateOfBirth"), is("11/29/2015"));
  }

  @Test
  public void testGETRecordsByLastName() {
    postNewRecord("Hastings|Lynnet|lhastings5@fotki.com|Indigo|11/29/2015");
    postNewRecord("Chisholm|Deck|dchisholm6@google.it|Teal|9/20/1980");
    postNewRecord("Torrans|Ralf|rtorrans7@newsvine.com|Purple|7/16/2001");

    HttpResponse<JsonNode> response = Unirest.get("/records/name").asJson();
    JSONObject body = response.getBody().getObject();
    assertThat(body.getInt("total"), is(3));
    JSONArray records = body.getJSONArray("records");
    assertThat("The first record should have a last name of Chisholm", records.getJSONObject(0).getString("lastName"), is("Chisholm"));
    assertThat("The second record should have a last name of Hastings", records.getJSONObject(1).getString("lastName"), is("Hastings"));
    assertThat("The third record should have a last name of Torrans", records.getJSONObject(2).getString("lastName"), is("Torrans"));
  }

  private void postNewRecord(String body) {
    HttpResponse<String> response = Unirest.post("/records").body(body).asString();
    if (response.getStatus() != 200) {
      fail(String.format("Aborting tests. A new record failed to POST with error code %d: %s.", response.getStatus(), response.getBody()));
    }
  }
}