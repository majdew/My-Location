package com.example.mylocation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapConvertorClass {

    public static byte [] getBytes (Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG , 0 , byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public  static  Bitmap getImage(byte [] data){
        return BitmapFactory.decodeByteArray(data , 0 , data.length);
    }
}
