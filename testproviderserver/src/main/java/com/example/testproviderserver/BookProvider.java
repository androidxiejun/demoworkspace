package com.example.testproviderserver;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by AndroidXJ on 2018/11/28.
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = "MainActivity";
    public static final String AUTHORITY = "com.example.testproviderserver.PROVIDER";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/db_book");
    public static final int BOOK_URI_CODE = 0;
    private BookDaoUtil mDaoUti;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        Book book=new Book();
        book.setBookName("冰与火之歌");
        book.setPrice(100);
        mDaoUti=new BookDaoUtil(getContext());
        boolean flag=mDaoUti.insertBook(book);
        Log.i(TAG,"插入数据----"+flag);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(TAG, "query-----" + Thread.currentThread().getName());
        String table=getTableName(uri);
        if(table==null){
            throw new IllegalArgumentException("Unsupported URI :"+uri);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = "db_book";
                break;
        }
        return tableName;
    }
}
