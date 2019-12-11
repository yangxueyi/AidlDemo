// BookController.aidl
package com.yxy.service;
import com.yxy.service.Book;

// Declare any non-default types here with import statements

interface BookController {
    List<Book> getBookList();
    String addBookInOut(inout Book book);
}
