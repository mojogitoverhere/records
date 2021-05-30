package com.david.records;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Record {

  private final String firstName;
  private final String lastName;
  private final String email;
  private final String favoriteColor;
  private final LocalDate dateOfBirth;

  private static String PATTERN = "M/d/yyyy";
  private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN, Locale.US);

  public Record(String firstName, String lastName, String email, String favoriteColor, LocalDate dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.favoriteColor = favoriteColor;
    this.dateOfBirth = dateOfBirth;
  }

  public Record(String firstName, String lastName, String email, String favoriteColor, String dateOfBirthAsString) {
    this(firstName, lastName, email, favoriteColor, parseDate(dateOfBirthAsString));
  }

  public static LocalDate parseDate(String dateAsString) {
    return LocalDate.parse(dateAsString, FORMATTER);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getFavoriteColor() {
    return favoriteColor;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getDateOfBirthAsString() {
    return this.dateOfBirth.format(FORMATTER);
  }
}
