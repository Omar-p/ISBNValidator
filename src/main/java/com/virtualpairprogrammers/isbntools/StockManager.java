package com.virtualpairprogrammers.isbntools;

public class StockManager {
  private final ExternalISBNDataService externalISBNWebService;
  private final ExternalISBNDataService ISBNDatabase;

  public StockManager(ExternalISBNDataService externalISBNDataService, ExternalISBNDataService isbnDatabase) {
    this.externalISBNWebService = externalISBNDataService;
    ISBNDatabase = isbnDatabase;
  }


  public String getLocatorCode(String isbn) {
    Book book;
    if (ISBNDatabase.lookup(isbn) != null) book = ISBNDatabase.lookup(isbn);
    else book = externalISBNWebService.lookup(isbn);

    StringBuilder locator = new StringBuilder();
    locator.append(book.getIsbn().substring(isbn.length() - 4));
    locator.append(book.getAuthor().substring(0, 1));
    locator.append(book.getTitle().split(" ").length);
    return locator.toString();
  }
}
