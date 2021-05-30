package com.david.records;

public class RecordsCommand {

  public static void main(String args[]) {

    RecordsService recordsService = new RecordsService();

    Record r1 = new Record("david", "morales", "david@me.com", "blue", "7/3/1992");
    Record r2 = new Record("sherlock", "holmes", "sh@me.com", "red", "12/4/1992");
    Record r3 = new Record("steve", "lombardi", "steve@me.com", "green", "4/7/1991");

    recordsService.addRecord(r1);
    recordsService.addRecord(r2);
    recordsService.addRecord(r3);

    // Sorted by last name
    System.out.println("First Name\tLast Name\tEmail\tFavorite Color\tDate of Birth");
    recordsService.getAllRecords().stream().sorted((a, b) -> a.getLastName().compareToIgnoreCase(b.getLastName())).forEach(record -> System.out.println(print(record)));
  }

  private static String print(Record record) {
    return String.format("%s\t%s\t%s\t%s\t%s", record.getFirstName(), record.getLastName(), record.getEmail(), record.getFavoriteColor(), record.getDateOfBirthAsString());
  }

}
