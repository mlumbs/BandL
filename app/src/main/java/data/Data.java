package data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DevelopX on 2016-08-01.
 */
public class Data {

    //Note that SharedPreferences are called after onCreate of an activity ,So the caller of this class must be aware of this


    Context context ;
    SharedPreferences sharedPref;// object points to a file containing key-value pairs and provides simple methods to read and write them
    public static int track_objects =0;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    public static int general=2;
    public static int notification=3;


    private String TAG = Data.class.getSimpleName();

    public static final String PREF_FILE="jobfocus.developx.onfleeck.co.za.jobfocus_key";
    // All Shared Preferences Keys
    public static final String KEY_FACEBOOK_ID = "face_id"; //Use later for facebook Preferences
    public static final String KEY_NOTI_HEADER = "header"; //this is a String
    public static final String KEY_NOTI_BODY = "body";//this is a String
    public static final String KEY_NOTI_NUMBER = "number";//this is a value that holds the id of the incrementing notification
    public static final String KEY_TONE_ = "tone"; //This is a boolean to make noise or not
    public static final String KEY_PROMO = "promo"; //This is a promo for office only
    public static final String KEY_SMART_CV_REG = "smartcvreg"; //This is a smart cv entry
    public static final String LAST_ENTRY = "last_entry"; //Read the last db entry,This by default is -1
    public static final String BACK_ENTRY = "back_entry"; //The default is background true,assume the app is always offline
    public static final String REC_ENTRY="receive_entry";
    public static final String NOTI_FRONT = "MainActivity_UI";//This holds the ui notification area,must be boolean,if it true this highlights
    public static final String APP_VERSION = "APP_version";
/*
Decided to change the above to public since they also point to a public file named sharedPreference

 */
     //@SuppressLint("CommitPrefEdits")
     public Data(Context m){
           this.context=m;
           this.sharedPref =m.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
           this.editor =sharedPref.edit();
           track_objects++;
           storeApp_version(2);
}

    public void storeFace(String face) {
        editor.putString(KEY_FACEBOOK_ID, face);
        editor.commit();
    }

    public void storeReceive(boolean rec) {
        editor.putBoolean(REC_ENTRY, rec);
        editor.commit();
    }
    public void EditBack(boolean face) {
        editor.putBoolean(BACK_ENTRY, face);
        editor.apply();
    }


    public void storeNotification(String head, String body) {
        editor.putString(KEY_NOTI_HEADER, head);
        editor.putString(KEY_NOTI_BODY, body);
        editor.commit();
    }

    public void storeFront(boolean t) {
        editor.putBoolean(NOTI_FRONT, t);
        editor.commit();
    }

    public void storeTone(Boolean tone) {
        editor.putBoolean(KEY_TONE_, tone);
        editor.commit();
    }
    public void storeApp_version(int ver) {
        editor.putInt(APP_VERSION, ver);
        editor.commit();
    }

    public void storePromo(String promo) {
        editor.putString(KEY_PROMO, promo);
        editor.commit();
    }

    public void storeSCV(String sm) {
        editor.putString(KEY_SMART_CV_REG, sm);
        editor.commit();
    }

    public void storeEntry(String entry) {
        editor.putString(LAST_ENTRY, entry);
        editor.commit();
    }

    public String getFace() {
       return sharedPref.getString(KEY_FACEBOOK_ID,"");
    }

    public String getNotificationHead() {
        return sharedPref.getString(KEY_NOTI_HEADER,"Head not Initialized");
    }
    public int getApp_version() {
        return sharedPref.getInt(APP_VERSION,2);
    }

    public String getNotificationBody() {
        return sharedPref.getString(KEY_NOTI_BODY,"body not Initialized");
    }


    public Boolean getReceive(){
        return sharedPref.getBoolean(REC_ENTRY,true);
    }
    public Boolean getTone() {
        return sharedPref.getBoolean(KEY_TONE_,true);
    }

    public Boolean getBack() {
        return sharedPref.getBoolean(BACK_ENTRY,false);
    }

    public Boolean getFront() {
        return sharedPref.getBoolean(NOTI_FRONT,true);
    }

    public String getPromo() {
        return sharedPref.getString(KEY_PROMO,"");
    }

    public String getSCV() {
        return sharedPref.getString(KEY_SMART_CV_REG, "");
    }

    public String getEntry() {
        return sharedPref.getString(LAST_ENTRY,"-1");
    }


}
