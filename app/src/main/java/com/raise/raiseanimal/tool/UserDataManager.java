package com.raise.raiseanimal.tool;


import android.content.Context;
import android.content.SharedPreferences;

public class UserDataManager {
    private static UserDataManager userDataManager;

    private SharedPreferences sp;

    public UserDataManager(Context context) {
        sp = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
    }

    public static synchronized UserDataManager getInstance(Context context){
        if (userDataManager == null){
            userDataManager = new UserDataManager(context);
        }
        return userDataManager;
    }

    public void saveShowDialog(boolean isCheck){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isShow",isCheck);
        editor.apply();
    }
    public boolean isShow(){
        return sp.getBoolean("isShow",true);
    }
}
