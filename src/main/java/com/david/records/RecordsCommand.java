package com.david.records;

public class RecordsCommand {

  public static void main(String args[]) {

    RecordsService recordsService = new RecordsService();

    Record r1 = new Record("david", "morales", "david@me.com", "blue", "7/3/1992");
    Record r2 = new Record("sherlock", "holmes", "sh@me.com", "red", "12/4/1992");
    Record r3 = new Record("steve", "lombardi", "steve@me.com", "blue", "4/7/1991");

    recordsService.addRecord(r1);
    recordsService.addRecord(r2);
    recordsService.addRecord(r3);

    System.out.println("OUTPUT 1: Sorted by ascending favorite color then last name");
    System.out.println("First Name\tLast Name\tEmail\tFavorite Color\tDate of Birth");
    recordsService.getAllRecordsByFavoriteColorThenByLastNameAscending().forEach(r -> System.out.println(print(r)));
    System.out.println();

    System.out.println("OUTPUT 2: Sorted by ascending date of birth");
    System.out.println("First Name\tLast Name\tEmail\tFavorite Color\tDate of Birth");
    recordsService.getAllRecordsByDateOfBirthAscending().forEach(r -> System.out.println(print(r)));
    System.out.println();

    System.out.println("OUTPUT 3: Sorted by descending last name");
    System.out.println("First Name\tLast Name\tEmail\tFavorite Color\tDate of Birth");
    recordsService.getAllRecordsByLastNameDescending().forEach(r -> System.out.println(print(r)));
    System.out.println();
  }

  private static String print(Record record) {
    return String.format("%s\t%s\t%s\t%s\t%s", record.getFirstName(), record.getLastName(), record.getEmail(), record.getFavoriteColor(), record.getDateOfBirthAsString());
  }

}
