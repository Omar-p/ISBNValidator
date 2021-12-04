package com.virtualpairprogrammers.isbntools;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class ValidateISBN {
  private static final int ISBN_13_LEN = 13;
  private static final int ISBN_10_LEN = 10;
  private static final int ISBN_13_MULTIPLIER = 10;
  private static final int ISBN_10_MULTIPLIER = 11;
  public boolean checkISBN(String isbn) {
    if (isbn.length() == ISBN_10_LEN) return checkISBN10(isbn);
    else if (isbn.length() == ISBN_13_LEN) return checkISBN13(isbn);
    else throw new NumberFormatException();
  }

  private boolean checkISBN13(String isbn) {
    return  (sum(x -> x % 2 != 0, x -> x * 3, isbn) + sum(x -> x % 2 == 0, x -> x, isbn))
        % ISBN_13_MULTIPLIER == 0;
  }

  private int sum(IntPredicate predicate, Function<Integer, Integer> f, String isbn13) {
    return IntStream.range(0, ISBN_13_LEN)
        .filter(predicate)
        .map(isbn13::charAt)
        .map(Character::getNumericValue)
        .reduce(0, (s, x) -> f.apply(x) + s);
  }

  private boolean checkISBN10(String isbn) {
    boolean isAnyNotDigit = !isbn.chars()
        .limit(9)
        .allMatch(Character::isDigit);

    if (isAnyNotDigit || !(Character.isDigit(isbn.charAt(9)) || isbn.charAt(9) == 'X'))
      throw new NumberFormatException();

    AtomicInteger i = new AtomicInteger(ISBN_10_LEN);
    int sum = isbn.chars()
        .limit(9)
        .map(Character::getNumericValue)
        .reduce(0, (s, e) -> s + e * i.getAndDecrement());
    return isbn.charAt(9) != 'X' ?
        (sum + Character.getNumericValue(isbn.charAt(9))) % 11 == 0 : (sum+10) % ISBN_10_MULTIPLIER == 0;
  }
}
