package com.david.records;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public class Parser {

  /*
   This pattern matches a string with the following form:
   Minelli|Alister|aminelli2@webs.com|Violet|6/24/1990

   The first [^|]+ matches any string up to the first pipe character,
   then this is repeated 4 more times for the remaining strings separated
   by each comma character.
   NOTE: The pipe is a special regex character, which is
   why it must be escaped by the double backslash (\\) characters.
  */
  private static final Pattern PIPE_DELIMITED_PATTERN = Pattern.compile("^[^|]+(\\|[^|]+){4}$");

  /*
   This pattern matches a string with the following form:
   Minelli,Alister,aminelli2@webs.com,Violet,6/24/1990

   The first [^,]+ matches any string up to the first comma character,
   then this is repeated 4 more times for the remaining strings separated
   by each comma character.
  */
  private static final Pattern COMMA_DELIMITED_PATTERN = Pattern.compile("^[^,]+(,[^,]+){4}$");

  /*
    All-purpose parsing method that automatically determines which delimiter
    the line is using, parses the data, and creates a Record.
   */
  public static Record parse(String line) {
    if (line == null) {
      return null;
    }

    // Assume a space is used as the delimiter unless a pipe or
    // comma character is found
    String delimiterRegex = "[\\s]+";
    if (PIPE_DELIMITED_PATTERN.matcher(line).find()) {
      delimiterRegex = "\\|";
    } else if (COMMA_DELIMITED_PATTERN.matcher(line).find()) {
      delimiterRegex = ",";
    }

    return parse(line, delimiterRegex);
  }

  @Nullable
  private static Record parse(String line, String delimeterRegex) {
    String[] parts = line.split(delimeterRegex);
    if (parts.length != 5) {
      return null;
    }
    return new Record(parts[1].trim(), parts[0].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim());
  }

  private Parser() {}
}
