package com.example.addrecyclecard1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION=1;
    private static final String DB_NAME="UserInfo";
    private static final String TABLE_NAME="tbl_user";
    private static final String KEY_TITLE="title";
    private static final String KEY_DESC="desc";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable="Create Table "+TABLE_NAME+"("+KEY_TITLE+" TEXT,"+KEY_DESC+" TEXT"+")";
        db.execSQL(createUserTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql=("drop table if exists " +TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }
    public void insert(PersonBean personBean){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_TITLE,personBean.getTitle());
        values.put(KEY_DESC,personBean.getDesc());
        db.insert(TABLE_NAME,null,values);
    }
    public List<PersonBean> selectUserData(){
        ArrayList<PersonBean> userList=new ArrayList<PersonBean>();
        SQLiteDatabase db= getReadableDatabase();
        String[] columns={KEY_TITLE,KEY_DESC};
        Cursor c =db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (c.moveToNext()){
            String title=c.getString(0);
            String desc=c.getString(1);
            PersonBean personBean=new PersonBean();
            personBean.setTitle(title);
            personBean.setDesc(desc);
            userList.add(personBean);
        }
        return userList;
    }
    public void delete(String title){
        SQLiteDatabase db =getWritableDatabase();
        String whereClause=KEY_TITLE+"='"+title+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }
    public void update(PersonBean personBean){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_DESC,personBean.getDesc());
        String whereClause=KEY_TITLE+"='"+personBean.getTitle()+"'";
        db.update(TABLE_NAME,values,whereClause,null);
    }
}