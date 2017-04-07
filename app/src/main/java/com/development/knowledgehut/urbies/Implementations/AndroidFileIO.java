package com.development.knowledgehut.urbies.Implementations;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.development.knowledgehut.urbies.Frameworks.FileIO;


public class AndroidFileIO implements FileIO{

    private Context context;

    public AndroidFileIO(Context context){
        this.context = context;
    }


    public SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
