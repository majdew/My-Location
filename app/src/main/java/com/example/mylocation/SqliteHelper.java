package com.example.mylocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LocationDatabase";
    public static final String LOCATIONS_TABLE = "locationsTable";
    public static final String DATE_TABLE = "dateTable";
    public static final String ID_COLUMN = "locationId";
    public static final String Title_COLUMN = "locationTitle";
    public static final String DESCRIPTION_COLUMN = "locationDescription";
    public static final String LATITUDE_COLUMN = "locationLatitude";
    public static final String LONGITUDE_COLUMN = "locationLogitude";
    public static final String DATE_COLUMN = "visitDate";

    public static final int VERSION = 6;

    public static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + LOCATIONS_TABLE
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Title_COLUMN + " VARCHAR (50) NOT NULL, "
            + DESCRIPTION_COLUMN + " VARCHAR (250) NOT NULL,  "
            + LATITUDE_COLUMN + " DECIMAL (10,5) NOT NULL , "
            + LONGITUDE_COLUMN + " DECIMAL (10,5) NOT NULL , "
            + LONGITUDE_COLUMN + " DECIMAL (10,5) NOT NULL , "
            + DATE_COLUMN + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP ) ;";

    public static final String CREATE_TABLE_DATE = "CREATE TABLE " + DATE_TABLE
            + " ( " + ID_COLUMN + " INTEGER ,"
            + DATE_COLUMN + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,"
            + " CONSTRAINT DATE_TABLE_PK PRIMARY KEY ( "
            + ID_COLUMN +" , " + DATE_COLUMN + " ) ) ;";


    public SqliteHelper (Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_DATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOCATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATE_TABLE);
        this.onCreate(db);

    }
}
