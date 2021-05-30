package com.david.records;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Record {

  private final String firstName;
  private final String lastName;
  private final String email;
  private final String favoriteColor;
  private final LocalDate dateOfBirth;

  private static final String PATTERN = "M/d/yyyy";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN, Locale.US);

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

  @Override
  public String toString() {
    return "Record{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", favoriteColor='" + favoriteColor + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Record record = (Record) o;
    return firstName.equals(record.firstName) &&
        lastName.equals(record.lastName) &&
        email.equals(record.email) &&
        favoriteColor.equals(record.favoriteColor) &&
        dateOfBirth.equals(record.dateOfBirth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, email, favoriteColor, dateOfBirth);
  }
}
