package com.example.qrattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Sharedpref {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MyPref";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_Branch = "branch";
    public static final String KEY_rollno = "rollno";
    public static final String KEY_year = "year";
    public static final String KEY_semster = "sem";


    public Sharedpref(Context context)
    {
        this.context =context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();

    }
    public void createLoginSession(String phone, String password,String rollno,String name,String branch ,String year,String sem){

        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_year, year);
        editor.putString(KEY_semster, sem);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_rollno, rollno);
        editor.putString(KEY_Branch, branch);
        editor.commit();
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public HashMap<String , String > getuserdetails()
    {
        HashMap<String , String> user  = new HashMap<String, String>();
        user.put(KEY_PHONE,pref.getString(KEY_PHONE,null));
        // user email id
        user.put(KEY_PASSWORD,pref.getString(KEY_PASSWORD,null));
        user.put(KEY_NAME,pref.getString(KEY_NAME,null));
        user.put(KEY_rollno,pref.getString(KEY_rollno,null));
        user.put(KEY_Branch,pref.getString(KEY_Branch,null));
        user.put(KEY_year,pref.getString(KEY_year,null));
        user.put(KEY_semster,pref.getString(KEY_semster,null));

        return  user;
    }
    public String getusername ()
    {
        return pref.getString(KEY_NAME,null);

    }
    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, FacultyLogin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
