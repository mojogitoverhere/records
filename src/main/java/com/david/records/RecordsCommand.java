package com.david.records;

import com.david.records.RecordsService.SortBy;
import com.david.records.RecordsService.SortDirection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class RecordsCommand {

  public static void main(String args[]) {

    // Verify at least one filename is passed in as an argument
    if (args.length < 1) {
      System.out.println("ERROR: One or more files must be specified to ingest.");
      return;
    }

    // Make sure the files exist and are readable
    for (String filename : args) {
      Path filepath = Paths.get(filename);
      if (!Files.isReadable(filepath)) {
        System.out.println(String.format("ERROR: The file [%s] either doesn't exist or is not readable.", filename));
        return;
      }
    }

    RecordsService recordsService = new RecordsService();

    for (String filename : args) {
      try {
        Path filepath = Paths.get(filename);
        ingest(recordsService, filepath);
      } catch (IOException e) {
        System.out.println(String.format("ERROR: An error occurred while reading file [%s]: %s", filename, e.getMessage()));
        return;
      }
    }

    System.out.println("OUTPUT 1: Sorted by ascending favorite color then last name");
    Comparator<Record> favoriteColorAscending = SortBy.FAVORITE_COLOR.getComparator();
    Comparator<Record> lastNameAscending = SortBy.LAST_NAME.getComparator();
    System.out.println(printTable(recordsService.getAllRecords(favoriteColorAscending.thenComparing(lastNameAscending))));

    System.out.println("OUTPUT 2: Sorted by ascending date of birth");
    System.out.println(printTable(recordsService.getAllRecords(SortBy.DATE_OF_BIRTH, SortDirection.ASCENDING)));

    System.out.println("OUTPUT 3: Sorted by descending last name");
    System.out.println(printTable(recordsService.getAllRecords(SortBy.LAST_NAME, SortDirection.DESCENDING)));
  }

  private static void ingest(RecordsService recordsService, Path filepath) throws IOException{
    try (Stream<String> lines = Files.lines(filepath, StandardCharsets.UTF_8)) {
      lines.forEach(line -> {
        Record newRecord = Parser.parse(line);
        if (newRecord == null) {
          System.out.println(String.format("WARNING: The following record did not have the 5 required fields and was ignored: %s", line));
          return;
        }
        recordsService.addRecord(newRecord);
      });
    }
  }

  private static String printTable(List<Record> records) {
    String lastNameLabel = "LAST NAME";
    String firstNameLabel = "FIRST NAME";
    String emailLabel = "EMAIL";
    String favoriteColorLabel = "FAVORITE COLOR";
    String dateOfBirthLabel = "DATE OF BIRTH";
    int minLastNameSize = lastNameLabel.length();
    int minFirstNameSize = firstNameLabel.length();
    int minEmailSize = emailLabel.length();
    int minFavoriteColorSize = favoriteColorLabel.length();
    int minDateOfBirthSize = dateOfBirthLabel.length();

    for (Record record : records) {
      if (record.getLastName().length() > minLastNameSize) {
        minLastNameSize = record.getLastName().length();
      }
      if (record.getFirstName().length() > minFirstNameSize) {
        minFirstNameSize = record.getFirstName().length();
      }
      if (record.getEmail().length() > minEmailSize) {
        minEmailSize = record.getEmail().length();
      }
      if (record.getFavoriteColor().length() > minFavoriteColorSize) {
        minFavoriteColorSize = record.getFavoriteColor().length();
      }
      if(record.getDateOfBirthAsString().length() > minDateOfBirthSize) {
        minDateOfBirthSize = record.getDateOfBirthAsString().length();
      }
    }

    StringBuilder builder = new StringBuilder();

    // Add the table header
    addTableCell(builder, lastNameLabel, minLastNameSize);
    addTableCell(builder, firstNameLabel, minFirstNameSize);
    addTableCell(builder, emailLabel, minEmailSize);
    addTableCell(builder, favoriteColorLabel, minFavoriteColorSize);
    addTableCell(builder, dateOfBirthLabel, minDateOfBirthSize);
    builder.append(System.lineSeparator());

    // Add 5 to account for the dividing characters between each column in the table
    int tableWidth = minLastNameSize + minFirstNameSize + minEmailSize + minFavoriteColorSize + minDateOfBirthSize + 5;
    for (int i = 0; i < tableWidth; i++) {
      builder.append("-");
    }
    builder.append(System.lineSeparator());

    for (Record record : records) {
      addTableCell(builder, record.getLastName(), minLastNameSize);
      addTableCell(builder, record.getFirstName(), minFirstNameSize);
      addTableCell(builder, record.getEmail(), minEmailSize);
      addTableCell(builder, record.getFavoriteColor(), minFavoriteColorSize);
      addTableCell(builder, record.getDateOfBirthAsString(), dateOfBirthLabel.length());
      builder.append(System.lineSeparator());
    }

    return builder.toString();
  }

  private static void addTableCell(StringBuilder builder, String str, int minLength) {
    builder.append(str);
    padWithSpace(builder, str, minLength);
    builder.append("|");
  }

  private static void padWithSpace(StringBuilder builder, String str, int requestedLength) {
    int difference = requestedLength - str.length();
    if (difference <= 0) {
      return;
    }

    for (int i = 0; i < difference; i++) {
      builder.append(" ");
    }
  }

}
