package com.david.records;

import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
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

  @Test(expected = UnsupportedOperationException.class)
  public void testGetAllRecordsIsNotMutable() {
    RecordsService service = new RecordsService();
    List<Record> records = service.getAllRecords();
    Record newRecord = getDummyRecord();

    // Throws UnsupportedOperationException
    records.add(newRecord);
  }

  private Record getDummyRecord() {
    return new Record("fname", "lname", "test@test.com", "color", "5/29/2021");
  }
}