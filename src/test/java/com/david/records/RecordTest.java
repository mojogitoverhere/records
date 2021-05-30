package com.david.records;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class RecordTest {

  @Test
  public void testRecordGetters() {
    String fname = "fname";
    String lname = "lname";
    String email = "test@test.com";
    String color = "color";
    LocalDate dateOfBirth = LocalDate.of(1992, 7, 3);
    Record record = new Record(fname, lname, email, color, dateOfBirth);

    assertThat(record.getFirstName(), is(fname));
    assertThat(record.getLastName(), is(lname));
    assertThat(record.getEmail(), is(email));
    assertThat(record.getFavoriteColor(), is(color));
    assertThat(record.getDateOfBirth(), is(dateOfBirth));
  }

  @Test(expected = DateTimeParseException.class)
  public void testInvalidDateOfBirthFormatThrowsException() {
    // Only the M/dd/yyyy format is accepted, so this date is invalid
    String invalidDOB = "5/29/21";
    Record record = new Record("fname", "lname", "test@test.com", "color", invalidDOB);
  }

  @Test
  public void testGetDateOfBirthAsString() {
    String dateOfBirth = "7/3/1992";
    Record record = new Record("fname", "lname", "test@test.com", "color", dateOfBirth);
    assertThat("The date of birth should be output in the same format that is accepted by the constructor", record.getDateOfBirthAsString(), is(dateOfBirth));
  }

  @Test
  public void testParseDateAcceptsCorrectFormat() {
    String dateString = "1/17/2021";
    LocalDate date = Record.parseDate(dateString);
    assertThat("The month should be 1", date.getMonthValue(), is(1));
    assertThat("The day should be 17", date.getDayOfMonth(), is(17));
    assertThat("The year should be 2021", date.getYear(), is(2021));
  }
}
