package com.example.testaidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AndroidXJ on 2018/11/28.
 */

public class Book implements Parcelable {
    private String name;
    private int price;

    public Book(String name,int price){
        this.name=name;
        this.price=price;
    }

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name="+name+
                ",price="+price+
                "}";
    }
}
