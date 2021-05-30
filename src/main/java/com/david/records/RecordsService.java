package com.david.records;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecordsService {

  private List<Record> records = new ArrayList<>();

  public void addRecord(Record record) {
    records.add(record);
  }

  public List<Record> getAllRecords() {
    return Collections.unmodifiableList(records);
  }

  public List<Record> getAllRecordsByLastNameDescending() {
    return records.stream().sorted(Comparator.comparing(Record::getLastName).reversed()).collect(Collectors.toList());
  }

  public List<Record> getAllRecordsByDateOfBirthAscending() {
    return records.stream().sorted(Comparator.comparing(Record::getDateOfBirth)).collect(Collectors.toList());
  }

  public List<Record> getAllRecordsByFavoriteColorThenByLastNameAscending() {
    Comparator<Record> favoriteColorAscending = Comparator.comparing(Record::getFavoriteColor);
    Comparator<Record> lastNameAscending = Comparator.comparing(Record::getLastName);
    return records.stream().sorted(favoriteColorAscending.thenComparing(lastNameAscending)).collect(Collectors.toList());
  }
}
