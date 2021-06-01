package com.david.records;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecordsService {

  public enum SortBy {
    LAST_NAME(Comparator.comparing(r -> r.getLastName().toLowerCase())),
    FIRST_NAME(Comparator.comparing(r -> r.getFirstName().toLowerCase())),
    EMAIL(Comparator.comparing(r -> r.getEmail().toLowerCase())),
    FAVORITE_COLOR(Comparator.comparing(r -> r.getFavoriteColor().toLowerCase())),
    DATE_OF_BIRTH(Comparator.comparing(Record::getDateOfBirth));

    private final Comparator<Record> comparator;

    SortBy(Comparator<Record> comparator) {
      this.comparator = comparator;
    }

    public Comparator<Record> getComparator() {
      return comparator;
    }
  }

  public enum SortDirection {
    ASCENDING,
    DESCENDING
  }

  private List<Record> records = new ArrayList<>();

  public void addRecord(Record record) {
    records.add(record);
  }

  public List<Record> getAllRecords() {
    return getAllRecords(SortBy.LAST_NAME, SortDirection.ASCENDING);
  }

  public List<Record> getAllRecords(Comparator<Record> comparator) {
    return records.stream().sorted(comparator).collect(Collectors.toList());
  }

  public List<Record> getAllRecords(SortBy sortBy, SortDirection sortDirection) {
    Comparator<Record> comparator = sortBy.getComparator();
    if (sortDirection == SortDirection.DESCENDING) {
      comparator = comparator.reversed();
    }

    return getAllRecords(comparator);
  }
}
