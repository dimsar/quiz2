package com.example.pustikom.adapterplay.com.example.pustikom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;
import com.example.pustikom.adapterplay.com.example.pustikom.user.StudentList;

/**
 * Created by Dimas Sartika on 11/11/2016.
 */

public class StudentDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "college.db";
    private static final int DATABASE_VERSION=1;
    private StudentDBHelper db;

    /*public StudentDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }*/

    public StudentDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_TABEL = "CREATE TABLE " + StudentContract.TABLE_NAME + " " +
        StudentContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentContract.STUDENT_NIM + "TEXT NOT NULL," +
                StudentContract.STUDENT_NAME + "TEXT NOT NULL," +
                StudentContract.STUDENT_GENDER + "INTEGER," +
                StudentContract.STUDENT_MAIL + "TEXT," +
                StudentContract.STUDENT_PHONE + "TEXT";

        db.execSQL(SQL_CREATE_TABEL);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void insertStudent (SQLiteDatabase db, Student student) {
        ContentValues  values = new ContentValues();
        values.put(StudentContract.STUDENT_NAME, student.getName());
        values.put(StudentContract.STUDENT_NIM, student.getNoreg());
        values.put(StudentContract.STUDENT_GENDER, student.getGender());
        values.put(StudentContract.STUDENT_MAIL, student.getMail());
        values.put(StudentContract.STUDENT_PHONE, student.getPhone());
        db.insert(StudentContract.TABLE_NAME, null, values);
    }

    public void updateStudent (SQLiteDatabase db, Student student) {
        ContentValues values = new ContentValues();
        values.put(StudentContract.STUDENT_NIM, student.getNoreg());
        values.put(StudentContract.STUDENT_NAME, student.getName());
        values.put(StudentContract.STUDENT_GENDER, student.getGender());
        values.put(StudentContract.STUDENT_MAIL, student.getMail());
        values.put(StudentContract.STUDENT_PHONE, student.getPhone());
        String condition = StudentContract._ID + "= ?";
        String[] conditionArgs = {student.getId() + " "};
        db.update(StudentContract.TABLE_NAME, values, condition, conditionArgs);
    }

    public void deleteStudent(SQLiteDatabase db, Student student) {
        ContentValues values = new ContentValues();
        values.put(StudentContract.STUDENT_NIM, student.getNoreg());
        values.put(StudentContract.STUDENT_NAME, student.getName());
        values.put(StudentContract.STUDENT_GENDER, student.getGender());
        values.put(StudentContract.STUDENT_MAIL, student.getMail());
        values.put(StudentContract.STUDENT_PHONE, student.getPhone());
        db.delete(StudentContract.TABLE_NAME, student.getId()+ " = ?",
                new String[] { String.valueOf(student.getId()) });
        db.close();
    }

    public void truncateStudent(SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.STUDENT_NIM, student.getNoreg());
        values.put(StudentContract.STUDENT_NAME, student.getName());
        values.put(StudentContract.STUDENT_GENDER, student.getGender());
        values.put(StudentContract.STUDENT_MAIL, student.getMail());
        values.put(StudentContract.STUDENT_PHONE, student.getPhone());
        String sql = "DELETE FROM " + StudentContract.TABLE_NAME + ";VACUUM;";
        db.execSQL(sql);
    }

    public StudentList fetchData(SQLiteDatabase db) {
        String[] projection = {
                StudentContract._ID,
                StudentContract.STUDENT_NIM,
                StudentContract.STUDENT_NAME,
                StudentContract.STUDENT_GENDER,
                StudentContract.STUDENT_MAIL,
                StudentContract.STUDENT_PHONE
    };
        Cursor cursor = db.query(
                StudentContract.TABLE_NAME,    // The table to query
                projection,                 // The columns to return
                null,                       // The columns for the WHERE clause
                null,                       // The values for the WHERE clause
                null,                       // Don't group the rows
                null,                       // Don't filter by row groups
                null);                      // The sort order

        // Figure out the index of each column
        int idIndex = cursor.getColumnIndex(StudentContract._ID);
        int nimIndex = cursor.getColumnIndex(StudentContract.STUDENT_NIM);
        int nameIndex = cursor.getColumnIndex(StudentContract.STUDENT_NAME);
        int genderIndex = cursor.getColumnIndex(StudentContract.STUDENT_GENDER);
        int phoneIndex = cursor.getColumnIndex(StudentContract.STUDENT_PHONE);
        int mailIndex = cursor.getColumnIndex(StudentContract.STUDENT_MAIL);

        StudentList studentList = new StudentList();
        while (cursor.moveToNext()) {
            int currentID = cursor.getInt(idIndex);
            String currentNim = cursor.getString(nimIndex);
            String currentName = cursor.getString(nameIndex);
            int currentGender = cursor.getInt(genderIndex);
            String currentMail = cursor.getString(mailIndex);
            String currentPhone = cursor.getString(phoneIndex);

            StudentList student = new StudentList();
            studentList.add(new Student(currentNim,currentName,currentGender,currentMail,currentPhone));
        }

        cursor.close();
        return studentList;
    }

}