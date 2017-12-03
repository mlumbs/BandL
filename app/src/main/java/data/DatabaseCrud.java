package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DevelopX on 2016-08-17.
 * All CRUD Functions will be called as the  Service Sequences
 */
public class DatabaseCrud {
    //Write the shared preference automatically thus not saving to the database!!
    //So we need only need Noti1,Noti2 and Delete entry Preferences(This is command to delete obsolete values or entries)
    //every time the the NotificationUtil is called the SharePreference values must be re-written just delete old text and give it new text
    //they write only once
    //the Database must not take action of saving when C and Po and EntryID are Empty
    //this means that NotificationUtils can be sent without the need of a Job Entry
    //Also if The NotificationUtils has been clicked or viewed it should be automatically be deleted
    //Thus the NotificationUtils only fires if and only if the notification is not empty
    //Therefore if the notification is empty it should not fire

    /**
     * @param c            Context
     * @param Name_of_Food
     * @param Description
     * @param Price
     * @param id
     * @param Resta
     * @param ImageLink
     */

    public static void ToAaddMenu(Context c, String Name_of_Food, String Description,
                                  String Price, String id, String Resta, String ImageLink) {
        ContentValues JobValues = new ContentValues();
        Log.v("add menu", Name_of_Food + "  descr " + Description + " price " + Price + " id " + id + "  ToAaddReportEntry");
        JobValues.put(JobContracts.Menu.COLUMN_Name_Food, Name_of_Food);
        JobValues.put(JobContracts.Menu.COLUMN_Description, Description);
        JobValues.put(JobContracts.Menu.COLUMN_Price, Price);
        JobValues.put(JobContracts.Menu.COLUMN_EntryID, id);
        JobValues.put(JobContracts.Menu.COLUMN_Restarent, Resta);
        JobValues.put(JobContracts.Menu.COLUMN_TAG, ImageLink);
        c.getContentResolver().insert(JobContracts.Menu.CONTENT_URI, JobValues);
        Log.v("Crud", "entering on Crud");
    }

    /**
     *
     * @param c
     * @param Name_of_Food
     * @param price
     * @param decri
     */
    public static void ToAaddCheckout(Context c, String Name_of_Food, String price,
                                      String decri,String id) {
        ContentValues JobValues = new ContentValues();
        Log.v("ins", Name_of_Food + "  " + price + " " + decri + " ");
        JobValues.put(JobContracts.CheckOut.COLUMN_Name, Name_of_Food);
        JobValues.put(JobContracts.CheckOut.COLUMN_description, decri);
        JobValues.put(JobContracts.CheckOut.COLUMN_price, price);//price
        JobValues.put(JobContracts.CheckOut.COLUMN_OUT_ID_, id);//id
        c.getContentResolver().insert(JobContracts.CheckOut.CONTENT_URI, JobValues);
    }

    public static double AutoRefresh(Context c) {
        double l = 0.0;
        Log.v("auto","auto 1");
        try {
            Cursor cursor = c.getContentResolver().query(JobContracts.CheckOut.CONTENT_URI, null, null, null, null);
            //assert cursor != null;
            while (cursor.moveToNext() && !cursor.isAfterLast()) {
                Log.v("auto","auto 2");

                l += cursor.getDouble(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_price));
            }
            return l;
        } catch (Exception e) {
            Log.v("auto","auto 3");

            return 0.0;
        }
    }


    public static String SendOrder(Context c) {
        String l = "";
        Log.v("auto","auto 1");
        try {
            Cursor cursor = c.getContentResolver().query(JobContracts.CheckOut.CONTENT_URI, null, null, null, null);
            //assert cursor != null;
            while (cursor.moveToNext() && !cursor.isAfterLast()) {
                Log.v("auto","auto 2");
                l +="<div><p>"+cursor.getString(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_Name))+"</p>"
                        +"<p>"+cursor.getString(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_description))+"</p>"+
                        "<p>"+cursor.getString(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_price))+"</p>"+
                        "<p>"+cursor.getString(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_OUT_ID_))+"</p><div>";
            }
          return replaceWhite(l);
            //return l;
        } catch (Exception e) {
            Log.v("auto","auto 3");
            return "";
        }
    }

    public static String replaceWhite(String ab){
        return ab.replace(" ","-");
    }

//    public static String SendOrder(Context c) {
//        String l = "";
//        Log.v("auto","auto 1");
//        try {
//            Cursor cursor = c.getContentResolver().query(JobContracts.CheckOut.CONTENT_URI, null, null, null, null);
//            //assert cursor != null;
//            while (cursor.moveToNext() && !cursor.isAfterLast()) {
//                Log.v("auto","auto 2");
//                l +="["+cursor.getDouble(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_Name))+")"
//                        +"["+cursor.getDouble(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_description))+")"+
//                        "["+cursor.getDouble(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_price))+")"+
//                        "["+cursor.getDouble(cursor.getColumnIndex(JobContracts.CheckOut.COLUMN_OUT_ID_))+")";
//            }
//            return l;
//        } catch (Exception e) {
//            Log.v("auto","auto 3");
//            return "";
//        }
//    }





    public static void ToDeleteCheck(Context c, String se) {
        String selection = JobContracts.CheckOut._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(se)};
        c.getContentResolver().delete(JobContracts.CheckOut.BuildCheckId(se), selection, selectionArgs);
        //Check out database table
    }


}




















