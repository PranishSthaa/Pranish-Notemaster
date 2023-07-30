package com.example.pranishnotemaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.pranishnotemaster.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    static final String DB_NAME = "NOTE.DB";
    static final int DB_VERSION = 1;

    static final String USER_EMAIL = "email";
    static final String USER_PASSWORD = "password";
    static final String NOTE_TITLE = "title";
    static final String NOTE_CONTENT = "content";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the User table
        db.execSQL("CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_EMAIL + " TEXT NOT NULL, " +
                USER_PASSWORD + " TEXT NOT NULL" +
                ");");

        // Create the Notes table
        db.execSQL("CREATE TABLE Notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE + " TEXT NOT NULL, " +
                NOTE_CONTENT + " TEXT NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Notes");
    }

    void register(String email, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_PASSWORD, password);
        long response = database.insert("User", null, contentValues);
        if(response == -1){
            Toast.makeText(context, "Failed to register user.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully registered, you can login now.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean login(String email, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {USER_EMAIL};
        String selection = USER_EMAIL + " = ? AND " + USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query("User", columns, selection, selectionArgs, null, null, null);
        boolean loginSuccessful = cursor.moveToFirst();
        cursor.close();
        return loginSuccessful;
    }

    void addNote(String title, String content){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, title);
        contentValues.put(NOTE_CONTENT, content);
        long response =  database.insert("Notes", null, contentValues);
        if(response == -1){
            Toast.makeText(context, "Failed to add Note.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully added note.", Toast.LENGTH_SHORT).show();
        }
    }

    List<NoteModel> getAllNotes(){
        List<NoteModel> notes = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Notes", null);

        if(cursor.moveToFirst()){
            do {
                NoteModel note = new NoteModel();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                notes.add(note);
            }  while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return notes;
    }

    void updateNote(int id, String title, String content) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, title);
        contentValues.put(NOTE_CONTENT, content);
        long response = database.update("Notes", contentValues, "id=?", new String[]{String.valueOf(id)});
        if(response == -1){
            Toast.makeText(context, "Failed to update Note.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated note.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteNote(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long response =  database.delete("Notes", "id=?", new String[]{String.valueOf(id)});
        if(response == -1){
            Toast.makeText(context, "Failed to delete Note.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted note.", Toast.LENGTH_SHORT).show();
        }
    }
}
