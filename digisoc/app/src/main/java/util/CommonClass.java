package util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import com.education.ActivitySchoolActivity;
import com.education.AttendenceActivity;
import com.education.ExamTabActivity;
import com.education.GrowthActivity;
import com.education.NearbyActivity;
import com.education.ProfileActivity;
import com.education.SchoolProfileActivity;
import com.education.TeacherActivity;

import Config.ConstValue;

/**
 * Created by LENOVO on 4/20/2016.
 */
public class CommonClass {
    Activity activity;
    public SharedPreferences settings;

    public CommonClass(Activity activity){
        this.activity = activity;
        settings = activity.getSharedPreferences(ConstValue.PREF_NAME, 0);
    }
    public void setSession(String key,String value){
        settings.edit().putString(key,value).commit();
    }
    public String getSession(String key){
        return settings.getString(key,"");
    }
    public boolean is_user_login(){
        String key = getSession(ConstValue.COMMON_KEY);
        if (key==null || key.equalsIgnoreCase("")){
            return  false;
        }else {
            return  true;
        }
    }
    public  void open_screen(int position){
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(activity, ProfileActivity.class);
                break;
            case 1:
                intent = new Intent(activity, SchoolProfileActivity.class);
                break;
            case 2:
                intent = new Intent(activity, TeacherActivity.class);
                break;
            case 3:
                intent = new Intent(activity, AttendenceActivity.class);
                break;
            case 4:
                intent = new Intent(activity, ExamTabActivity.class);
                break;
            case 5:
                intent = new Intent(activity, GrowthActivity.class);
                break;
            case 6:
                intent = new Intent(activity, ActivitySchoolActivity.class);
                break;
            case 7:
                intent = new Intent(activity, NearbyActivity.class);
                break;
            case 8:
               // intent = new Intent(activity, MainActivity.class);
                System.out.println("----------------------ghfgjhf-----------------");
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("coming soon...");
                alertDialog.setMessage("....");
                alertDialog.show();

                break;

        }
        if (intent!=null){
            activity.startActivity(intent);
        }
    }
}
