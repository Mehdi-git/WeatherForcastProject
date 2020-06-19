package com.example.weatherforcastproject.repository.db;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.weatherforcastproject.utils.Pair;
import java.util.ArrayList;
import java.util.List;


public class CitySQLiteHelper extends SQLiteOpenHelper {

    private static CitySQLiteHelper sInstance;
    private static final String TABLE_NAME = "Cities";
    private static final int DATABASE_VERSION = 1;

    public static synchronized CitySQLiteHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new CitySQLiteHelper(context);
        }
        return sInstance;
    }


    private CitySQLiteHelper(Context context ) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "city TEXT," +
                "country TEXT," +
                "cityId INTEGER" +
                ")";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //This method check whether the desirable city exist in database or not
    private boolean isExist(int id){
        boolean theCityIsExist = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String isExist = " SELECT cityId FROM " + TABLE_NAME + " WHERE cityId =" + id;
            Cursor cursor = db.rawQuery(isExist, null);
            if (cursor.getCount()>0)
                theCityIsExist = true;

        }catch (Exception ex){
            Log.d("MyTag","Some Error while check city existence! " + ex.getMessage());
        }
        return theCityIsExist;
    }

    // This method insert information of city to database
    public void insertCity(String city, String country, int cityId) {
        String INSERT_CITY_QUERY = "INSERT INTO " + TABLE_NAME + "(" +
                "city," +
                "country," +
                "cityId" +
                ")" +
                "VAlUES" +
                "(" +
                "'" + city + "'" + "," +
                "'" + country + "'" + "," +
                cityId +
                ")";
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            if(!isExist(cityId)){
                db.execSQL(INSERT_CITY_QUERY);
            }

        }catch (Exception ex){
            Log.d("MyTag","Some Error while add city To db " + ex.getMessage());
        }finally {
            db.close();
        }
    }
    //To delete city information from database
    public void deleteCity(int cityId){
        Log.d("SQLite", ""+cityId );
        String DELETE_CITY_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE cityId = "+cityId;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_CITY_QUERY);
        db.close();
    }
    // this method get city and country from database and make them as List of pairs
    public List<Pair<String, String>> getCityListAsPair() {
        List<Pair<String, String>> cities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String GET_CITY_LIST = " SELECT city,country FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(GET_CITY_LIST, null);

        while (cursor.moveToNext()) {
            Pair<String, String> pair = new Pair<>(cursor.getString(0),
                    cursor.getString(1));
            cities.add(pair);
            db.close();
        }
        return cities;
    }
    //This method make list of all city's Id
    public List <Integer> getListOfCityIds () {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> result = new ArrayList<>();
        String GET_CITY_IDS = " SELECT cityId FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(GET_CITY_IDS, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getInt(0));
            db.close();
        }
        Log.d("MyTag","Return fo getListOfCityIds method:"+result.toString());
        return result;
    }
}






