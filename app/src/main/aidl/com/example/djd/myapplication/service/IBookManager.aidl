// IBookManager.aidl
package com.example.djd.myapplication.service;

// Declare any non-default types here with import statements
import com.example.djd.myapplication.service.IOnNewBookArrivedListener;
import com.example.djd.myapplication.service.Book;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addBook(in Book book);

    List<Book> getBookList();

    void registerOnNewBookArrivedListener(in IOnNewBookArrivedListener listener);

    void unregisterOnNewBookArrivedListener(in IOnNewBookArrivedListener listener);
}
