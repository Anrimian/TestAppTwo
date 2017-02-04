package com.testapptwo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.database.dao.CommentsDAO;
import com.testapptwo.database.dao.ImageInfoDAO;

import java.sql.SQLException;

/**
 * Created on 18.01.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="testapptwo.db";

    private static final int DATABASE_VERSION = 3;

    private ImageInfoDAO imageInfoDAO;
    private CommentsDAO commentsDAO;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, ImageInfo.class);
            TableUtils.createTable(connectionSource, CommentInfo.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer){
        try {
            TableUtils.dropTable(connectionSource, ImageInfo.class, true);
            TableUtils.dropTable(connectionSource, CommentInfo.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    public ImageInfoDAO getImageInfoDAO() throws SQLException {
        if (imageInfoDAO == null){
            imageInfoDAO = new ImageInfoDAO(getConnectionSource(), ImageInfo.class);
        }
        return imageInfoDAO;
    }

    public CommentsDAO getCommentsDAO() throws SQLException {
        if (commentsDAO == null){
            commentsDAO = new CommentsDAO(getConnectionSource(), CommentInfo.class);
        }
        return commentsDAO;
    }

    @Override
    public void close(){
        super.close();
        imageInfoDAO = null;
        commentsDAO = null;
    }
}

