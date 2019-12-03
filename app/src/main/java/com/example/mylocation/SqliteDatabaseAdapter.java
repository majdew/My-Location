package com.example.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void insertLocation(LocationObject location){
        this.open();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SqliteHelper.Title_COLUMN , location.getTitle());
        contentValues.put(SqliteHelper.DESCRIPTION_COLUMN , location.getLocationDescription());
        contentValues.put(SqliteHelper.LATITUDE_COLUMN , location.getLatitude());
        contentValues.put(SqliteHelper.LONGITUDE_COLUMN, location.getLongitude());

        this.sqLiteDatabase.insert(SqliteHelper.LOCATIONS_TABLE , null , contentValues);
        this.close();
    }

    public void deleteLocation(int id){
        this.open();
        this.sqLiteDatabase.delete(SqliteHelper.LOCATIONS_TABLE , SqliteHelper.ID_COLUMN + " = " + id , null);
        this.close();

    }

    public void  updateLocation(int id , String locationTitle , String locationDescription){
        this.open();
        String SQLStatement = "UPDATE " + SqliteHelper.LOCATIONS_TABLE +
                              " SET " + SqliteHelper.Title_COLUMN + " = \' "
                              + locationTitle + "\' , " + SqliteHelper.DESCRIPTION_COLUMN + " = \' "
                              + locationDescription + "\' " + " WHERE " + SqliteHelper.ID_COLUMN + " = "
                              + id +" ; ";
        sqLiteDatabase.execSQL(SQLStatement);
        this.close();
    }

    public List<LocationObject> getAllLocations(){
        this.open();

        List<LocationObject> locations = new ArrayList<>();

        Cursor cursor = this.sqLiteDatabase.query(SqliteHelper.LOCATIONS_TABLE , this.tableColumns ,null , null , null ,null ,null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            int  locationId = cursor.getInt(0);
            String  locationTitle = cursor.getString(1);
            String  locationDescription = cursor.getString(2);
            double  locationLatitude = cursor.getDouble(3);
            double  locationLongitude = cursor.getDouble(4);
            Timestamp visitDateTimeStamp =  Timestamp.valueOf(cursor.getString(5));

            Date date = new Date();
            date.setTime(visitDateTimeStamp.getTime());
            String formattedDate = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").format(date);

            LocationObject location = new LocationObject(locationId , locationLatitude , locationLongitude , locationTitle , locationDescription );
            location.setVisitingDate(formattedDate);
            locations.add(location);
            cursor.moveToNext();
        }

        cursor.close();
        this.close();
        return  locations;


    }
}
