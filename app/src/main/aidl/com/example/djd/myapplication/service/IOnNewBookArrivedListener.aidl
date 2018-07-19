// IMyAidlInterface.aidl
package com.example.djd.myapplication.service;

// Declare any non-default types here with import statements
import com.example.djd.myapplication.service.Book;
interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void newBookArrived(in Book book);
}
