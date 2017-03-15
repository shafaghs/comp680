package com.comp680.sunlink;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

public class LOGGER {
    private LOGGER(){}
    private static String EXCEPTIONERROR= "Error_message";

    public static void info(IOException e){
        Log.w(EXCEPTIONERROR,e);
    }
    public static void info(Exception e){
        Log.w(EXCEPTIONERROR,e);
    }
    public static void jsonInfo(JSONException e){
        Log.w(EXCEPTIONERROR,e);
    }

    public static void parseInfo(java.text.ParseException e){
        Log.w(EXCEPTIONERROR,e);
    }

}
