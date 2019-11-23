package com.example.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class SqliteDatabaseAdapter {

    SQLiteDatabase sqLiteDatabase ;
    SqliteHelper sqliteHelper ;
    String [] tableColumns = {SqliteHelper.ID_COLUMN , SqliteHelper.Title_COLUMN ,
            SqliteHelper.DESCRIPTION_COLUMN , SqliteHelper.LATITUDE_COLUMN ,
            SqliteHelper.LONGITUDE_COLUMN , SqliteHelper.DATE_COLUMN };


    public SqliteDatabaseAdapter(Context context) {
        this.sqliteHelper = new SqliteHelper(context) ;
    }

    public void open(){
        this.sqLiteDatabase = sqliteHelper.getWritableDatabase();
    }

    public  void close(){
        this.sqLiteDatabase.close();
    }

    public void insertLocation(Location location){
        ContentValues contentValues = new ContentValues();

        contentValues.put(SqliteHelper.Title_COLUMN , location.getTitle());
        contentValues.put(SqliteHelper.DESCRIPTION_COLUMN , location.getLocationDescription());
        contentValues.put(SqliteHelper.LATITUDE_COLUMN , location.getLatitude());
        contentValues.put(SqliteHelper.LONGITUDE_COLUMN, location.getLongitude());

        this.sqLiteDatabase.insert(SqliteHelper.LOCATIONS_TABLE , null , contentValues);
    }

    public void deleteLocation(int id){
        this.sqLiteDatabase.delete(SqliteHelper.LOCATIONS_TABLE , SqliteHelper.ID_COLUMN + " = " + id , null);

    }

    public void  updateLocation(int id , Location location){
        ContentValues contentValues = new ContentValues();

        contentValues.put(SqliteHelper.Title_COLUMN , location.getTitle());
        contentValues.put(SqliteHelper.DESCRIPTION_COLUMN , location.getLocationDescription());
        contentValues.put(SqliteHelper.LATITUDE_COLUMN , location.getLatitude());
        contentValues.put(SqliteHelper.LONGITUDE_COLUMN, location.getLongitude());

        this.sqLiteDatabase.update(SqliteHelper.LOCATIONS_TABLE , contentValues , SqliteHelper.ID_COLUMN + " = " + id , null);

    }

    public List <Location> getAllLocations(){

        List <Location> locations = new ArrayList<>();

        Cursor cursor = this.sqLiteDatabase.query(SqliteHelper.LOCATIONS_TABLE , this.tableColumns ,null , null , null ,null ,null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            String  locationTitle = cursor.getString(1);
            String  locationDescription = cursor.getString(2);
            double  locationLatitude = cursor.getDouble(3);
            double  locationLongitude = cursor.getDouble(4);
            String visitDate = cursor.getString(5);

            Location location = new Location(locationLatitude , locationLongitude , locationTitle , locationDescription ,  visitDate);
            locations.add(location);
            cursor.moveToNext();
        }

        cursor.close();
        return  locations;

    }
}
