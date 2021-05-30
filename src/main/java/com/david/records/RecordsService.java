package com.david.records;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordsService {

  private List<Record> records = new ArrayList<>();

  public void addRecord(Record record) {
    records.add(record);
  }

  public List<Record> getAllRecords() {
    return Collections.unmodifiableList(records);
  }
}
