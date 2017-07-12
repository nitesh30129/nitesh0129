package com.example.nitesh.fareyetask1.localDb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.example.nitesh.fareyetask1.DataModel;

import java.util.ArrayList;

/**
 * Created by nitesh on 8/7/17.
 */

public class FareyeDb extends SQLiteOpenHelper {
    ArrayList<DataModel> arraylist;

    private static final int DATABASE_VERSION =1;
    // Database Name
    private static final String DATABASE_NAME = "TaskFetch";
    // Contacts table name
    private static final String TABLE_DATA = "fetchdata";
    private static final String Album_Id="Task_Album_Id";
    private static final String Id = "Task_Id";
    private static final String Title = "Task_Title";
    private static final String URL = "Task_Url";
    private static final String Thumbnail_URL = "Task_Thumbnail_Url";
    Context context;

    String CREATE_TABLE = "CREATE TABLE " + TABLE_DATA + "("
            + Album_Id + " INTEGER ,"
            + Id + " INTEGER PRIMARY KEY ,"
            + Title + " TEXT ," + URL + " TEXT ," + Thumbnail_URL + " TEXT "+  ")";

     String FAVOURITE_TABLE = "CREATE TABLE " + TABLE_DATA + "("
            + Album_Id + " INTEGER ,"
            + Id + " INTEGER PRIMARY KEY ,"
            + Title + " TEXT ," + URL + " TEXT ," + Thumbnail_URL + " TEXT "+  ")";

    public FareyeDb(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     //db.execSQL(FAVOURITE_TABLE);
        db.execSQL(CREATE_TABLE);
        Log.d("database created==", "Table created for album one");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table1");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table2");
        onCreate(sqLiteDatabase);

    }
    public void deleteItem(DataModel data) {
        Log.d("MySQLiteHelper", "in deleteItem");
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_DATA + " WHERE " + Id + " = " + data.getId());
            db.close();
            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MySQLiteHelper", e.getMessage());
        }
    }

   public void insertData(ArrayList<DataModel> data ){
       String sql="INSERT INTO " + TABLE_DATA +" ( Task_Album_Id, Task_Id, Task_Title, Task_Url, Task_Thumbnail_Url) VALUES (?,?,?,?,?) ";
       SQLiteDatabase db = this.getWritableDatabase();
       db.beginTransactionNonExclusive();
       SQLiteStatement stmt=db.compileStatement(sql);
       Log.d("data inserted","number of insertion"+sql+"DATA INSRTED");
       for (DataModel dataModel : data){
           stmt.bindLong(1, dataModel.getAlbumid());
           stmt.bindLong(2, dataModel.getId());
           stmt.bindString(3, dataModel.getTitle());
           stmt.bindString(4, dataModel.getUrl());
           stmt.bindString(5, dataModel.getThumbnail());
           stmt.execute();
           stmt.clearBindings();
       }
       db.setTransactionSuccessful();
      db.endTransaction();
       db.close();
   }


    public void deletdata(){
        SQLiteDatabase db= this.getReadableDatabase();
        db.execSQL("delete from fetchdata");
        //db=this.getWritableDatabase();
        db.close();
    }

    public ArrayList<DataModel> getDataDb(){

        ArrayList<DataModel> datauniversal=new ArrayList();
        SQLiteDatabase database= this.getReadableDatabase();
        String selectQuery="SELECT * FROM fetchdata";
        Log.d("query maker", selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("number of row in ", ""+cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                datauniversal.add(new DataModel(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return datauniversal;
    }


    public void insertSingleData(ArrayList<DataModel> arraylist) {
        String sql="INSERT INTO " + TABLE_DATA +" ( Task_Album_Id, Task_Id, Task_Title, Task_Url, Task_Thumbnail_Url) VALUES (?,?,?,?,?) ";

    }
}
