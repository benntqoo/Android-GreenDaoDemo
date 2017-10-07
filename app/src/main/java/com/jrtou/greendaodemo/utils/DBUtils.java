package com.jrtou.greendaodemo.utils;

import android.content.Context;

import com.jrtou.greendaodemo.sqlite.BookEntryDao;
import com.jrtou.greendaodemo.sqlite.DaoMaster;
import com.jrtou.greendaodemo.sqlite.DaoSession;

/**
 * Created by jrtou on 10/7/17.
 */

public class DBUtils {
    private static final String DB_NAME = "book.db";

    private static DBUtils mDBUtils = null;
    private DaoSession mDaoSession;

    public static DBUtils Instance(Context context) {
        if (mDBUtils == null)
            mDBUtils = new DBUtils(context);

        return mDBUtils;

    }

    private DBUtils(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        mDaoSession = daoMaster.newSession();
    }

    public BookEntryDao getBookDao() {
        return mDaoSession.getBookEntryDao();
    }
}
