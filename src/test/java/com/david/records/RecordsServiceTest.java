package com.david.records;

import java.util.Comparator;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class RecordsServiceTest {

  @Test
  public void testAddRecord() {
    RecordsService service = new RecordsService();
    Record record = getDummyRecord();
    service.addRecord(record);
    assertThat("The record service should contain 1 record", service.getAllRecords().size(), is(1));
    assertThat("The record should be the same", service.getAllRecords(), hasItems(record));
  }

  @Test
  public void testGetAllRecordsByLastNameDescending() {
    RecordsService service = new RecordsService();
    Record morales = new Record("fname", "Morales", "test@test.com", "blue", "5/29/2021");
    Record chavez = new Record("fname", "Chavez", "test@test.com", "blue", "5/29/2021");
    Record anderson = new Record("fname", "Anderson", "test@test.com", "blue", "5/29/2021");
    Record lombardi = new Record("fname", "Lombardi", "test@test.com", "blue", "5/29/2021");
    service.addRecord(morales);
    service.addRecord(chavez);
    service.addRecord(anderson);
    service.addRecord(lombardi);

    List<Record> sortedRecords = service.getAllRecords(RecordsService.SortBy.LAST_NAME, RecordsService.SortDirection.DESCENDING);
    assertThat("The records should be sorted in this order by last name: Morales, Lombardi, Chavez, Anderson", sortedRecords, contains(morales, lombardi, chavez, anderson));
  }

  @Test
  public void testGetAllRecordsByDateOfBirthAscending() {
    RecordsService service = new RecordsService();
    Record dob1985 = new Record("fname", "lname", "test@test.com", "blue", "5/29/1985");
    Record dob2021 = new Record("fname", "lname", "test@test.com", "blue", "5/29/2021");
    Record dob1990 = new Record("fname", "lname", "test@test.com", "blue", "5/29/1990");
    Record dob1970 = new Record("fname", "lname", "test@test.com", "blue", "5/29/1970");
    service.addRecord(dob1985);
    service.addRecord(dob2021);
    service.addRecord(dob1990);
    service.addRecord(dob1970);

    List<Record> sortedRecords = service.getAllRecords(RecordsService.SortBy.DATE_OF_BIRTH, RecordsService.SortDirection.ASCENDING);
    assertThat("The records should be sorted in this order by date of birth: 5/29/1970, 5/29/1985, 5/29/1990, 5/29/2021", sortedRecords, contains(dob1970, dob1985, dob1990, dob2021));
  }

  @Test
  public void testGetAllRecordsByFavoriteColorThenByLastNameAscending() {
      RecordsService service = new RecordsService();
      Record redMorales = new Record("fname", "Morales", "test@test.com", "red", "5/29/2021");
      Record blueChavez = new Record("fname", "Chavez", "test@test.com", "blue", "5/29/2021");
      Record blueAnderson = new Record("fname", "Anderson", "test@test.com", "blue", "5/29/2021");
      Record redLombardi = new Record("fname", "Lombardi", "test@test.com", "red", "5/29/2021");
      service.addRecord(redMorales);
      service.addRecord(blueChavez);
      service.addRecord(blueAnderson);
      service.addRecord(redLombardi);

    Comparator<Record> favoriteColorAscending = RecordsService.SortBy.FAVORITE_COLOR.getComparator();
    Comparator<Record> lastNameAscending = RecordsService.SortBy.LAST_NAME.getComparator();
    List<Record> sortedRecords = service.getAllRecords(favoriteColorAscending.thenComparing(lastNameAscending));
      assertThat("The records should be sorted in this order by ascending favorite color then by ascending last name: blue/Anderson, blue/Chavez, red/Lombardi, red/Morales", sortedRecords, contains(blueAnderson, blueChavez, redLombardi, redMorales));
  }

  private Record getDummyRecord() {
    return new Record("fname", "lname", "test@test.com", "color", "5/29/2021");
  }
}