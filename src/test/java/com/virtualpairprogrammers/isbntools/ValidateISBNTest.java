package com.virtualpairprogrammers.isbntools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateISBNTest {

  ValidateISBN validator;

  @BeforeEach
  public void setUp() {
    validator = new ValidateISBN();
  }

  @Test
  public void checkValidISBN() {
    boolean result = validator.checkISBN("0140449116");
    assertTrue(result);
  }

  @Test
  public void checkInValidISBN() {
    boolean result = validator.checkISBN("0140449117");
    assertFalse(result);
  }

  @Test
  public void checkISBNWithWrongLength() {
    assertAll(
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("123456789")),
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("12345678")),
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("1234567"))
    );
  }

  @Test
  public void checkISBNWithNotDigitsInput() {
    assertAll(
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("123456789a")),
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("12345678bd")),
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("1234567ces")),
        () -> assertThrows(NumberFormatException.class, () -> validator.checkISBN("helloworld"))
    );
  }

  @Test
  public void ISBNWithAnXAtEndAreValid() {
    assertTrue(validator.checkISBN("012000030X"));
  }

  @Test
  public void ISBNWith13Digit() {
    assertAll(
        () -> assertTrue(validator.checkISBN("9781734314502")),
        () -> assertTrue(validator.checkISBN("9781684512294")),
        () -> assertTrue(validator.checkISBN("9781501135972"))
    );
  }

  @Test
  public void ISBN13WithWrongInputs() {
    assertAll(
        () -> assertFalse(validator.checkISBN("9781734314503")),
        () -> assertFalse(validator.checkISBN("9781684512295")),
        () -> assertFalse(validator.checkISBN("9781501135973"))
    );
  }
}