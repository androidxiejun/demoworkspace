// IBookInterface.aidl
package com.example.testaidlserver;
import com.example.testaidlserver.Book;
import com.example.testaidlserver.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book>getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}
