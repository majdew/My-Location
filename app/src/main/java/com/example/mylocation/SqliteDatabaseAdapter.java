package com.example.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

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
            SqliteHelper.LONGITUDE_COLUMN  ,SqliteHelper.DATE_COLUMN , SqliteHelper.IMAGE_COLUMN};
    String [] dateTableColumns = {SqliteHelper.ID_COLUMN ,  SqliteHelper.DATE_COLUMN };


    public SqliteDatabaseAdapter(Context context) {
        this.sqliteHelper = new SqliteHelper(context) ;
    }

    public void open(){
        this.sqLiteDatabase = sqliteHelper.getWritableDatabase();
    }

    public  void close(){
        this.sqLiteDatabase.close();
    }

    public boolean insertLocation(LocationObject location){
        this.open();
        ContentValues locationContentValues = new ContentValues();

        locationContentValues.put(SqliteHelper.Title_COLUMN , location.getTitle());
        locationContentValues.put(SqliteHelper.DESCRIPTION_COLUMN , location.getLocationDescription());
        locationContentValues.put(SqliteHelper.LATITUDE_COLUMN , location.getLatitude());
        locationContentValues.put(SqliteHelper.LONGITUDE_COLUMN, location.getLongitude());
        locationContentValues.put(SqliteHelper.IMAGE_COLUMN , location.getLocationImageBytes());

        long locationRowId = this.sqLiteDatabase.insert(SqliteHelper.LOCATIONS_TABLE , null , locationContentValues);


        this.close();
        return (locationRowId !=0);
    }

    public void deleteLocation(int id){
        this.open();
        this.sqLiteDatabase.delete(SqliteHelper.LOCATIONS_TABLE , SqliteHelper.ID_COLUMN + " = " + id , null);
        this.sqLiteDatabase.delete(SqliteHelper.DATE_TABLE , SqliteHelper.ID_COLUMN + " = " + id , null);
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

    public ArrayList<LocationObject> getAllLocations(){
        this.open();

        ArrayList<LocationObject> locations = new ArrayList<>();

        Cursor cursor = this.sqLiteDatabase.query(SqliteHelper.LOCATIONS_TABLE , this.tableColumns ,null , null , null ,null ,null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            int  locationId = cursor.getInt(0);
            String  locationTitle = cursor.getString(1);
            String  locationDescription = cursor.getString(2);
            double  locationLatitude = cursor.getDouble(3);
            double  locationLongitude = cursor.getDouble(4);
            Timestamp visitDateTimeStamp =  Timestamp.valueOf(cursor.getString(5));
            byte [] locationImageByteArray = cursor.getBlob(6);

            LocationObject location = new LocationObject(locationId , locationLatitude , locationLongitude , locationTitle , locationDescription , locationImageByteArray);
            Date date = new Date();
            date.setTime(visitDateTimeStamp.getTime());
            String formattedDate = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").format(date);
            ArrayList <String> listOfDates = new ArrayList<>();
            listOfDates.add(formattedDate);
            location.setVisitingDate(listOfDates);
            locations.add(location);
            cursor.moveToNext();
        }

        cursor.close();
        this.close();
        return  locations;

    }

    public ArrayList <String> getAllDates(int locationId){
        this.open();
        ArrayList<String> visitingDates = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.rawQuery("SELECT * FROM "+ SqliteHelper.DATE_TABLE + " WHERE " + SqliteHelper.ID_COLUMN +" = " + locationId + ";" , null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            int  locationDateId = cursor.getInt(0);
            Timestamp visitDateTimeStamp =  Timestamp.valueOf(cursor.getString(1));

            Date date = new Date();
            date.setTime(visitDateTimeStamp.getTime());
            String formattedDate = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss ").format(date);

            visitingDates.add(formattedDate);
            cursor.moveToNext();
        }

        cursor.close();
        this.close();
        return  visitingDates;
    }

    public ArrayList<LatLng> getAllLocationsLatLng (){

        ArrayList <LocationObject> locationObjects = getAllLocations();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for(int i=0; i<locationObjects.size() ; i++){
            LocationObject locationObject = locationObjects.get(i);
            double latitude = locationObject.getLatitude();
            double longitude = locationObject.getLongitude();
            LatLng latLng = new LatLng(latitude , longitude);
            latLngs.add(latLng);
        }
        return  latLngs;
    }

    public  boolean insertDate(int locationId){
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteHelper.ID_COLUMN , locationId);
        long dateRowId = this.sqLiteDatabase.insert(SqliteHelper.DATE_TABLE , null, contentValues);
        this.close();
        return  (dateRowId !=0);

    }
}
