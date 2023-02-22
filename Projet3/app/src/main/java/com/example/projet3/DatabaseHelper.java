package com.example.projet3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db";

    //Books table
    private static final String TABLE_NAME = "library";
    private static final String BOOK_ID = "id";
    private static final String BOOK_TITLE = "book_title";
    private static final String BOOK_AUTHOR = "book_author";
    private static final String BOOK_PAGES = "book_pages";

    //Users table
    private static final String TABLE_NAME1 = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    //Creating tables query
    String query = "CREATE TABLE " + TABLE_NAME +
            " (" + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BOOK_TITLE + " TEXT, " + BOOK_AUTHOR + " TEXT, " + BOOK_PAGES + " INTEGER);";

    String query2 = "CREATE TABLE " + TABLE_NAME1 +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT NOT NULL, " + COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_PASSWORD + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);

    }
    public Boolean insertUserData(String username, String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert(TABLE_NAME1, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    // ylh zdtha
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users ",null);
        return cursor;
    }
    public Boolean updateUserData(String userName,String email,String number){
        SQLiteDatabase DBW = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName",userName);
        contentValues.put("Email",email);
        Cursor cursor = DBW.rawQuery("Select * from Users where username = ?",new String[]{userName});
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            long l = DBW.update("Users",contentValues,"username=?",new String[]{userName});
            if (l == -1) {
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Boolean deleteUserData(String userName){
        SQLiteDatabase DBW = this.getWritableDatabase();
        Cursor cursor = DBW.rawQuery("Select * from Users where username = ?",new String[]{userName});
        if (cursor.getCount()>0){
            long l =DBW.delete("Users","username = ?",new String[]{userName});
            if (l == -1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

 /*   public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

  */


    public void addBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_TITLE, title);
        cv.put(BOOK_AUTHOR, author);
        cv.put(BOOK_PAGES, pages);
        long result = db.insert(TABLE_NAME,null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id, String title, String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BOOK_TITLE, title);
        cv.put(BOOK_AUTHOR, author);
        cv.put(BOOK_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


}