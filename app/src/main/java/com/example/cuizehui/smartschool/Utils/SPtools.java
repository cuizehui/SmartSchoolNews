package com.example.cuizehui.smartschool.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cuizehui on 2016/8/22at ${time}.
 */
public class SPtools {

        public static void savesetsp(Context context, String key, String value){
            SharedPreferences sharedPreferences=context.getSharedPreferences("set",Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(key, value).commit();
        }
        public static String  getsetsp(Context context,String key){
            SharedPreferences sharedPreferences=context.getSharedPreferences("set",Context.MODE_PRIVATE);

            return   sharedPreferences.getString(key,null);
        }
}
