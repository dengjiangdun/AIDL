package com.example.djd.myapplication.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by djd on 18-7-18.
 */

public class Book implements Parcelable {
    private int bookid;
    private String bookName;

    public Book(int id, String bookName) {
        this.bookid = id;
        this.bookName = bookName;

    }

    public Book(Parcel in){
        this.bookid = in.readInt();
        this.bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookid);
        dest.writeString(bookName);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId ="+bookid+
                "bookName"+bookName+"}";
    }
}
