package com.example.testproviderserver;

import android.content.Context;

import java.util.List;

/**
 * Created by AndroidXJ on 2018/11/29.
 */

public class BookDaoUtil {
    private DaoManager mDaoManager;
    private boolean flag=false;

    public BookDaoUtil(Context context){
       mDaoManager=DaoManager.getInstance();
       mDaoManager.init(context);
    }

    /**
     * 添加一条数据
     * @param book
     * @return
     */
    public boolean insertBook(Book book){
        try{
            mDaoManager.getSession().getBookDao().save(book);
            flag=true;
        }catch (Exception e){
            flag=false;
        }
       return  flag;
    }

    /**
     * 更新一条数据
     * @param book
     * @return
     */
    public boolean updataBook(Book book){
        try{
            mDaoManager.getSession().getBookDao().update(book);
            flag=true;
        }catch (Exception e){
            flag=false;
        }

        return flag;
    }

    /**
     * 删除一条记录
     * @param book
     * @return
     */
    public boolean deleteBook(Book book){
        try{
           mDaoManager.getSession().getBookDao().delete(book);
           flag=true;
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }

    /**
     * 根据Id查询数据库
     * @param key
     * @return
     */
    public Book queryBookById(long key){
        Book book = mDaoManager.getSession().getBookDao().queryBuilder().where(BookDao.Properties.Id.eq(key)).unique();
//        return  mDaoManager.getSession().load(Book.class,key);
        return book;
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<Book> queryAllRecords(){
        return mDaoManager.getSession().loadAll(Book.class);
    }

    /**
     * 删除所有的数据
     * @return
     */
    public boolean deleteAll(){
        try{
            mDaoManager.getSession().getBookDao().deleteAll();
            flag=true;
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }
}
