package com.david.records;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public class RecordsCommand {

  /* This pattern matches a string with the following form:
   Minelli|Alister|aminelli2@webs.com|Violet|6/24/1990

   The first [^|]+ matches any string up to the first pipe character,
   then this is repeated 4 more times for the remaining strings separated
   by each comma character.
  */
  private static final Pattern PIPE_DELIMITED_PATTERN = Pattern.compile("^[^|]+(\\|[^|]+){4}$");

  /* This pattern matches a string with the following form:
   Minelli,Alister,aminelli2@webs.com,Violet,6/24/1990

   The first [^,]+ matches any string up to the first comma character,
   then this is repeated 4 more times for the remaining strings separated
   by each comma character.
  */
  private static final Pattern COMMA_DELIMITED_PATTERN = Pattern.compile("^[^,]+(,[^,]+){4}$");

  public static void main(String args[]) {

    // Verify a filename is passed in as an argument
    if (args.length != 1) {
      System.out.println("ERROR: A file to ingest must be specified.");
      return;
    }

    // Make sure the file exists and is readable
    String filename = args[0];
    Path filepath = Paths.get(filename);
    if (!Files.isReadable(filepath)) {
      System.out.println(String.format("ERROR: The file [%s] is not readable.", filename));
      return;
    }

    RecordsService recordsService = new RecordsService();

    try (Stream<String> lines = Files.lines(filepath, StandardCharsets.US_ASCII)) {
      lines.forEach(line -> {
        String delimiterRegex = " ";
        if (PIPE_DELIMITED_PATTERN.matcher(line).find()) {
          delimiterRegex = "\\|";
        } else if (COMMA_DELIMITED_PATTERN.matcher(line).find()) {
          delimiterRegex = ",";
        }
        Record newRecord = parseRecord(line, delimiterRegex);
        if (newRecord == null) {
          System.out.println(String.format("The following record did not have the 5 required fields and was ignored: %s", line));
          return;
        }
        recordsService.addRecord(newRecord);
      });

    } catch (IOException e) {
      System.out.println(String.format("ERROR: An error occurred while reading the file [%s]: %s", filename, e.getMessage()));
      return;
    }

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

  @Nullable
  private static Record parseRecord(String line, String delimeterRegex) {
    String[] parts = line.split(delimeterRegex);
    if (parts.length != 5) {
      System.out.println(String.format("parts: %d", parts.length));
      return null;
    }
    return new Record(parts[0], parts[1], parts[2], parts[3], parts[4]);
  }

  private static String print(Record record) {
    return String.format("%s\t%s\t%s\t%s\t%s", record.getFirstName(), record.getLastName(), record.getEmail(), record.getFavoriteColor(), record.getDateOfBirthAsString());
  }

}
