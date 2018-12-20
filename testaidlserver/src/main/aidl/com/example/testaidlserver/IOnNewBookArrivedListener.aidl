// IOnNewBookArrivedListener.aidl
package com.example.testaidlserver;
import com.example.testaidlserver.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
