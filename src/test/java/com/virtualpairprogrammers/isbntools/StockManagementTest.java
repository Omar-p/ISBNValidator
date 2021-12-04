package com.virtualpairprogrammers.isbntools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockManagementTest {

  ExternalISBNDataService webServiceMock;
  ExternalISBNDataService databaseServiceMock;
  StockManager stockManager;

  @BeforeEach
  public void setUp() {
    webServiceMock = mock(ExternalISBNDataService.class);
    databaseServiceMock = mock(ExternalISBNDataService.class);
    stockManager = new StockManager(webServiceMock, databaseServiceMock);
  }

  @Test
  public void testCanGetCorrectLocatorCode() {
    when(webServiceMock.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Of Mice And Men", "J. steinbeck"));
    when(databaseServiceMock.lookup("0140177396"))
        .thenReturn(null);

    String isbn = "0140177396";
    String locatorCode = stockManager.getLocatorCode(isbn);

    assertEquals("7396J4", locatorCode);
  }

  @Test
  public void databaseIsUsedIfDataIsPresent() {

    when(databaseServiceMock.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Of Mice And Men", "J. steinbeck"));


    String isbn = "0140177396";
    String locatorCode = stockManager.getLocatorCode(isbn);


    verify(databaseServiceMock, times(2))
        .lookup(isbn);
  }

  @Test
  public void dataServiceIsUsedIfDataIsNotPresentInDatabase() {
    when(databaseServiceMock.lookup("0140177396"))
        .thenReturn(null);
    when(webServiceMock.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Of Mice And Men", "J. steinbeck"));

    String locatorCode = stockManager.getLocatorCode("0140177396");

    verify(webServiceMock, times(1))
        .lookup(anyString());

  }
}
