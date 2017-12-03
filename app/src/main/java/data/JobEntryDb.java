package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import android.database.sqlite.SQLiteDatabase;

/**
 * Created by DevelopX on 2015-09-04.
 */
public class JobEntryDb extends SQLiteOpenHelper {

    static final int DATABASE_VERSION =1;
    static final String DATABASE_NAME="FoodBandL.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String TIMESTAMP = " INTEGER";

    public JobEntryDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {




        String CREATE_TABLE =  "CREATE TABLE " + JobContracts.Menu.TABLE_NAME + " (" +
                JobContracts.Menu._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +COMMA_SEP+
                JobContracts.Menu.COLUMN_Name_Food + TEXT_TYPE + COMMA_SEP +
                JobContracts.Menu.COLUMN_Description + TEXT_TYPE + COMMA_SEP +
                JobContracts.Menu.COLUMN_Price+TEXT_TYPE+COMMA_SEP+
                JobContracts.Menu.COLUMN_Restarent+TEXT_TYPE+COMMA_SEP+
                JobContracts.Menu.COLUMN_EntryID+TEXT_TYPE+COMMA_SEP+
                JobContracts.Menu.COLUMN_TAG+TEXT_TYPE+
                " )";

        String CREATE_TABLE_2 =  "CREATE TABLE " + JobContracts.CheckOut.TABLE_NAME + " (" +
                JobContracts.CheckOut._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +COMMA_SEP+
                JobContracts.CheckOut.COLUMN_Name + TEXT_TYPE + COMMA_SEP +
                JobContracts.CheckOut.COLUMN_description +TEXT_TYPE+COMMA_SEP+
                JobContracts.CheckOut.COLUMN_price +TEXT_TYPE+COMMA_SEP+
                JobContracts.CheckOut.COLUMN_OUT_ID_ + TEXT_TYPE + COMMA_SEP +
                JobContracts.CheckOut.COLUMN_tag_one_+TEXT_TYPE+COMMA_SEP+
                JobContracts.CheckOut.COLUMN_tag_two+TEXT_TYPE+COMMA_SEP+
                JobContracts.CheckOut.COLUMN_tag_three+TEXT_TYPE+
                " )";
        db.execSQL(CREATE_TABLE_2);
        db.execSQL(CREATE_TABLE);
    }







    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ JobContracts.CheckOut.TABLE_NAME);
        onCreate(db);
    }
}
