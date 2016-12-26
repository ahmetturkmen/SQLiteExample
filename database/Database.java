package com.ahmetturkmen.android.sqliteexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.ahmetturkmen.android.sqliteexample.objects.Car;
import com.ahmetturkmen.android.sqliteexample.objects.Person;

public class Database extends SQLiteOpenHelper {

    // Defining database version and its name

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME ="DATABASE_EXAMPLE";

    // Defining tables for cars and persons

    private static class PERSON_TABLE{
        private static final String TABLE_NAME="PERSONS";
        private static final String ID="ID";
        private static final String NAME="NAME";
        private static final String SURNAME="SURNAME";
        private static final String TCKNO="TCKNO";
        private static final String ADDRESS="ADDRESS";
        private static final String CAR = "CAR_ID";
    }
    private static class CAR_TABLE{
        private static final String TABLE_NAME="CARS";
        private static final String ID="ID";
        private static final String MARK="MARK";
        private static final String MODEL="MODEL";
        private static final String KM="KM";
        private static final String YEAR="YEAR";
    }

    // Whenever this constructor called our database will be created

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // creating queries for person and car classes

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPersonTablesQuery = "CREATE TABLE " + PERSON_TABLE.TABLE_NAME + "(" +
                PERSON_TABLE.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PERSON_TABLE.NAME + " TEXT, "+
                PERSON_TABLE.SURNAME + " TEXT, "+
                PERSON_TABLE.TCKNO + " TEXT, "+
                PERSON_TABLE.ADDRESS + " TEXT, "+
                PERSON_TABLE.CAR + " INTEGER)";

        String createCarsTablesQuery = "CREATE TABLE " + CAR_TABLE.TABLE_NAME + "(" +
                CAR_TABLE.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAR_TABLE.MARK + " TEXT, "+
                CAR_TABLE.MODEL + " TEXT, "+
                CAR_TABLE.KM + " TEXT, "+
                CAR_TABLE.YEAR + " TEXT)";

        sqLiteDatabase.execSQL(createCarsTablesQuery);
        sqLiteDatabase.execSQL(createPersonTablesQuery);
    }

    // Lets begin with creating methods to update, create, delete persons and cars information from database

    public void addPersonToDatabase(String name,String surname,String tckno,String address,int carID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // This part is just putting info to object of ContentValues class

        contentValues.put(PERSON_TABLE.NAME,name);
        contentValues.put(PERSON_TABLE.SURNAME,surname);
        contentValues.put(PERSON_TABLE.TCKNO,tckno);
        contentValues.put(PERSON_TABLE.ADDRESS,address);
        contentValues.put(PERSON_TABLE.CAR,carID);

        // After, the info about contentvalues is assigned, it is putted to PERSON_TABLE via insert command

        sqLiteDatabase.insert(PERSON_TABLE.TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    public void addCarToDatabase(String marka,String model,int km,int year){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CAR_TABLE.MARK,marka);
        contentValues.put(CAR_TABLE.MODEL,model);
        contentValues.put(CAR_TABLE.KM,km);
        contentValues.put(CAR_TABLE.YEAR,year);

        sqLiteDatabase.insert(CAR_TABLE.TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    public void deletePersonFromDatabase(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(PERSON_TABLE.TABLE_NAME,PERSON_TABLE.ID + " = ?",new String[] {String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void deleteCarFromDatabase(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(CAR_TABLE.TABLE_NAME,CAR_TABLE.ID + " = ?",new String[] {String.valueOf(id)});
        sqLiteDatabase.close();
    }
    public void updatePersonFromDatabase(int id,String name,String surname,String tckno,String address,int carID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PERSON_TABLE.NAME,name);
        contentValues.put(PERSON_TABLE.SURNAME,surname);
        contentValues.put(PERSON_TABLE.TCKNO,tckno);
        contentValues.put(PERSON_TABLE.ADDRESS,address);
        contentValues.put(PERSON_TABLE.CAR,carID);

        sqLiteDatabase.update(PERSON_TABLE.TABLE_NAME,contentValues,PERSON_TABLE.ID + " = ?",new String[] {String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void updateCarFromDatabase(int id,String marka,String model,int km,int year){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CAR_TABLE.MARK,marka);
        contentValues.put(CAR_TABLE.MODEL,model);
        contentValues.put(CAR_TABLE.KM,km);
        contentValues.put(CAR_TABLE.YEAR,year);

        sqLiteDatabase.update(CAR_TABLE.TABLE_NAME,contentValues,CAR_TABLE.ID + " = ?",new String[] {String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public ArrayList<Person> getPersonsFromDatabase(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + PERSON_TABLE.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        ArrayList<Person> personsList = new ArrayList<>();

        while(cursor.moveToNext()){
            Person person = new Person();
            for(int i =0;i<cursor.getColumnCount();i++){
                String columnName = cursor.getColumnName(i);
                switch (columnName){
                    case PERSON_TABLE.ID:
                        person.setId(Long.parseLong(cursor.getString(i)));
                        break;
                    case PERSON_TABLE.NAME:
                        person.setName(cursor.getString(i));
                        break;
                    case PERSON_TABLE.SURNAME:
                        person.setSurname(cursor.getString(i));
                        break;
                    case PERSON_TABLE.TCKNO:
                        person.setTckno(cursor.getString(i));
                        break;
                    case PERSON_TABLE.ADDRESS:
                        person.setAddress(cursor.getString(i));
                        break;
                    case PERSON_TABLE.CAR:
                        person.setCar(getCar(Integer.parseInt(cursor.getString(i))));
                        break;
                }
            }
            personsList.add(person);
        }
        return personsList;
    }

    public ArrayList<Car> getCarsFromDatabase(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + CAR_TABLE.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        ArrayList<Car> carsList = new ArrayList<>();

        while(cursor.moveToNext()){
            Car car = new Car();
            for(int i =0;i<cursor.getColumnCount();i++){
                String columnName = cursor.getColumnName(i);
                switch (columnName){
                    case CAR_TABLE.ID:
                        car.setId(Long.parseLong(cursor.getString(i)));
                        break;
                    case CAR_TABLE.MARK:
                        car.setMarka(cursor.getString(i));
                        break;
                    case CAR_TABLE.MODEL:
                        car.setModel(cursor.getString(i));
                        break;
                    case CAR_TABLE.KM:
                        car.setKm(Integer.parseInt(cursor.getString(i)));
                        break;
                    case CAR_TABLE.YEAR:
                        car.setYear(Integer.parseInt(cursor.getString(i)));
                        break;
                }
            }
            carsList.add(car);
        }
        return carsList;
    }


    public Person getPerson(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + PERSON_TABLE.TABLE_NAME + " WHERE " + PERSON_TABLE.ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        Person person = new Person();
        cursor.moveToFirst();
        if(cursor.getCount() >0){
            for(int i =0;i<cursor.getColumnCount();i++){
                String columnName = cursor.getColumnName(i);
                switch (columnName){
                    case PERSON_TABLE.ID:
                        person.setId(Long.parseLong(cursor.getString(i)));
                        break;
                    case PERSON_TABLE.NAME:
                        person.setName(cursor.getString(i));
                        break;
                    case PERSON_TABLE.SURNAME:
                        person.setSurname(cursor.getString(i));
                        break;
                    case PERSON_TABLE.TCKNO:
                        person.setTckno(cursor.getString(i));
                        break;
                    case PERSON_TABLE.ADDRESS:
                        person.setAddress(cursor.getString(i));
                        break;
                    case PERSON_TABLE.CAR:
                        person.setCar(getCar(Integer.parseInt(cursor.getString(i))));
                        break;
                }
            }
            sqLiteDatabase.close();
            return person;
        }
        return null;
    }

    // getCar method calls cars according to their id's.

    public Car getCar(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + CAR_TABLE.TABLE_NAME + " WHERE " + CAR_TABLE.ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        Car car = new Car();

        cursor.moveToFirst();
        if(cursor.getCount() >0){
            for(int i =0;i<cursor.getColumnCount();i++){
                String columnName = cursor.getColumnName(i);
                switch (columnName){
                    case CAR_TABLE.ID:
                        car.setId(Long.parseLong(cursor.getString(i)));
                        break;
                    case CAR_TABLE.MARK:
                        car.setMarka(cursor.getString(i));
                        break;
                    case CAR_TABLE.MODEL:
                        car.setModel(cursor.getString(i));
                        break;
                    case CAR_TABLE.KM:
                        car.setKm(Integer.parseInt(cursor.getString(i)));
                        break;
                    case CAR_TABLE.YEAR:
                        car.setYear(Integer.parseInt(cursor.getString(i)));
                        break;
                }
            }
            sqLiteDatabase.close();
            return car;
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
