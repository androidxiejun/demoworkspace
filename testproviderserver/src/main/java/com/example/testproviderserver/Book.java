package com.example.testproviderserver;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AndroidXJ on 2018/11/29.
 */
@Entity(nameInDb = "db_book")
public class Book implements Parcelable{
    @Id
    private Long id;

    @Property
    private String bookName;

    @Property
    private int price;

    protected Book(Parcel in) {
    }

    @Generated(hash = 254018534)
    public Book(Long id, String bookName, int price) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
    }

    @Generated(hash = 1839243756)
    public Book() {
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
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
        dest.writeString(bookName);
        dest.writeInt(price);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName=" +bookName+
                ",price="+price+
                "}";
    }
}
