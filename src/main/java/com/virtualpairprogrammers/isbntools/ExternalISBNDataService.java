package com.virtualpairprogrammers.isbntools;

@FunctionalInterface
public interface ExternalISBNDataService {
  Book lookup(String isbn);
}
