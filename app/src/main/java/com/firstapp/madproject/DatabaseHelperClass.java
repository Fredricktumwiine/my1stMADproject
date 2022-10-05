package com.firstapp.madproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=BcpVlXo2F3U

public class DatabaseHelperClass extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME = "frima.db";
    private SQLiteDatabase sqLiteDatabase;

    //CONSTRUCTOR
    public DatabaseHelperClass(@Nullable Context context) {
        super(context,"frima.db", null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase MyDb) {
        MyDb.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDb.execSQL("create Table employees(id integer primary key autoincrement, name TEXT, email TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDb, int oldVersion, int newVersion) {
        MyDb.execSQL("drop Table if exists users");
        MyDb.execSQL("DROP TABLE IF EXISTS employees");
        onCreate(MyDb);

    }

    //ADD DATA
   public void addEmployee(EmployeeModelClass employeeModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", employeeModelClass.getName());
        contentValues.put("email", employeeModelClass.getEmail());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("employees", null, contentValues);
    }

    //getting data
    public List<EmployeeModelClass> getEmployeeList(){
        String sql = "select * from employees" ;
        sqLiteDatabase = this.getReadableDatabase();
        List<EmployeeModelClass> storeEmployee = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                storeEmployee.add(new EmployeeModelClass(id,name,email));

            }while(cursor.moveToNext());
        }
        cursor.close();
        return storeEmployee;
    }

    public void updateEmployee(EmployeeModelClass employeeModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",employeeModelClass.getName());
        contentValues.put("email",employeeModelClass.getEmail());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update("employees",contentValues,"ID" + " = ?" , new String[]
                {String.valueOf(employeeModelClass.getId())});
    }


    public void deleteEmployee(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("employees", "ID" + " = ? ", new String[]
                {String.valueOf(id)});
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDb.insert("users", null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean checkUpUsername(String username){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("select * from users where username = ?", new String[] {username});
        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkUpUsernamePassword(String username, String password){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("select * from users where username = ? and password =?", new String[] {username, password});

        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }


}
