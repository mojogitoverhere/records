package com.david.records;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ParserTest {

  @Test
  public void testParseAcceptsPipeDelimitedString() {
    Record record = Parser.parse("Minelli|Alister|aminelli2@webs.com|Violet|6/24/1990");
    assertThat(record.getLastName(), is("Minelli"));
    assertThat(record.getFirstName(), is("Alister"));
    assertThat(record.getEmail(), is("aminelli2@webs.com"));
    assertThat(record.getFavoriteColor(), is("Violet"));
    assertThat(record.getDateOfBirthAsString(), is("6/24/1990"));
  }

  @Test
  public void testParseTrimsWhitespaceAroundPipes() {
    Record record = Parser.parse("Minelli |Alister |   aminelli2@webs.com   | Violet| 6/24/1990");
    assertThat(record.getLastName(), is("Minelli"));
    assertThat(record.getFirstName(), is("Alister"));
    assertThat(record.getEmail(), is("aminelli2@webs.com"));
    assertThat(record.getFavoriteColor(), is("Violet"));
    assertThat(record.getDateOfBirthAsString(), is("6/24/1990"));
  }

  @Test
  public void testParseAcceptsCommaDelimitedString() {
    Record record = Parser.parse("Minelli,Alister,aminelli2@webs.com,Violet,6/24/1990");
    assertThat(record.getLastName(), is("Minelli"));
    assertThat(record.getFirstName(), is("Alister"));
    assertThat(record.getEmail(), is("aminelli2@webs.com"));
    assertThat(record.getFavoriteColor(), is("Violet"));
    assertThat(record.getDateOfBirthAsString(), is("6/24/1990"));
  }

  @Test
  public void testParseTrimsWhitespaceAroundCommas() {
    Record record = Parser.parse("Minelli ,Alister ,   aminelli2@webs.com   , Violet, 6/24/1990");
    assertThat(record.getLastName(), is("Minelli"));
    assertThat(record.getFirstName(), is("Alister"));
    assertThat(record.getEmail(), is("aminelli2@webs.com"));
    assertThat(record.getFavoriteColor(), is("Violet"));
    assertThat(record.getDateOfBirthAsString(), is("6/24/1990"));
  }

  @Test
  public void testParseAcceptsSpaceDelimitedString() {
    Record record = Parser.parse("Minelli Alister aminelli2@webs.com Violet 6/24/1990");
    assertThat(record.getLastName(), is("Minelli"));
    assertThat(record.getFirstName(), is("Alister"));
    assertThat(record.getEmail(), is("aminelli2@webs.com"));
    assertThat(record.getFavoriteColor(), is("Violet"));
    assertThat(record.getDateOfBirthAsString(), is("6/24/1990"));
  }

  @Test
  public void testParseTrimsAllWhitespace() {
    Record record = Parser.parse("Minelli Alister    aminelli2@webs.com     Violet  6/24/1990");
    assertThat(record.getLastName(), is("Minelli"));
    assertThat(record.getFirstName(), is("Alister"));
    assertThat(record.getEmail(), is("aminelli2@webs.com"));
    assertThat(record.getFavoriteColor(), is("Violet"));
    assertThat(record.getDateOfBirthAsString(), is("6/24/1990"));
  }
}