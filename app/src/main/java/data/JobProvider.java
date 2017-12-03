package data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by DevelopX on 2015-09-04.
 */
public class JobProvider extends ContentProvider {


    private static final int menu_foods = 101;
    private static final int food_by =102;
    private static final int food_by_cate =103;
    private static final int check_cat =104;
    private static final int check_cat_sp =105;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private JobEntryDb mDbHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = JobContracts.CONTENT_AUTHORITY;
        matcher.addURI(authority, JobContracts.PATH_Food , menu_foods);
        matcher.addURI(authority, JobContracts.PATH_Food+ "/*", food_by);
        matcher.addURI(authority, JobContracts.PATH_Food+ "/*/*", food_by_cate);

        matcher.addURI(authority, JobContracts.PATH_Check, check_cat);
        matcher.addURI(authority, JobContracts.PATH_Check+"/*", check_cat_sp);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new JobEntryDb(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case menu_foods:
                return JobContracts.Menu.CONTENT_TYPE;
            case check_cat:
                return JobContracts.CheckOut.CONTENT_TYPE;
            case food_by:
                return JobContracts.Menu.CONTENT_ITEM_TYPE;
            case food_by_cate:
                return JobContracts.Menu.CONTENT_ITEM_TYPE;
            case check_cat_sp:
                return JobContracts.Menu.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

          case menu_foods:
            {
                retCursor = mDbHelper.getReadableDatabase().query(
                        JobContracts.Menu.TABLE_NAME,
                        null,
                        null,//mSelectionClause
                        null,//mSelectionArgs
                        null,
                        null,
                        sortOrder);
                break;
            }

            case check_cat:
            {
                retCursor = mDbHelper.getReadableDatabase().query(
                        JobContracts.CheckOut.TABLE_NAME,
                        null,
                        null,//mSelectionClause
                        null,//mSelectionArgs
                        null,
                        null,
                        sortOrder);
                break;
            }

            case check_cat_sp:
            {
                retCursor = getReportId(uri,JobContracts.CheckOut.TABLE_NAME + "." + JobContracts.CheckOut._ID + " = ?",JobContracts.CheckOut.TABLE_NAME);
                break;
            }

            case food_by:
            {
                retCursor = getFoodbyId(uri);
                break;
            }
            case food_by_cate:
            {
                retCursor = getAll_food_resterants(uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown " + uri);
        }

       // uri= JobContracts.Menu.CONTENT_URI;
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getFoodbyId(Uri uri) {
        String Id = JobContracts.Menu.getIDByFromUri(uri);
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = Id;
        String mSelectionClause = JobContracts.Menu.TABLE_NAME + "." + JobContracts.Menu._ID + " = ?";
        return mDbHelper.getReadableDatabase().query(
                JobContracts.Menu.TABLE_NAME,
                null,
                mSelectionClause,
                mSelectionArgs,
                null,
                null,
                null);

    }


    private Cursor getAll_food_resterants(Uri uri) {
        String Id = JobContracts.Menu.getLastFromUri(uri);
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = Id;
        String mSelectionClause = JobContracts.Menu.TABLE_NAME + "." + JobContracts.Menu.COLUMN_Restarent + " = ?";
        return mDbHelper.getReadableDatabase().query(
                JobContracts.Menu.TABLE_NAME,
                null,
                mSelectionClause,
                mSelectionArgs,
                null,
                null,
                null);

    }

    private Cursor getReportId(Uri uri, String mSelectionClause , String table) {

        String Id = JobContracts.CheckOut.getIDByFromUri(uri); //Calling for each type ,I believe it a formality not a necessity required
        String[] mSelectionArgs = {""};//But eventually the execution will not be different since the methods are static
        mSelectionArgs[0] = Id;
        return mDbHelper.getReadableDatabase().query(
                table,
                null,
                mSelectionClause,
                mSelectionArgs,
                null,
                null,
                null);

    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db =mDbHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case menu_foods:{
                long _id=db.insert(JobContracts.Menu.TABLE_NAME, null, values);
                if(_id>0){
                    returnUri= JobContracts.Menu.buildJobUri(_id);
                }
                else
                    throw new android.database.SQLException("Failed to insert A row");
                break;
            }
            case check_cat:{
                long _id=db.insert(JobContracts.CheckOut.TABLE_NAME, null, values);
                if(_id>0){
                    returnUri= JobContracts.CheckOut.buildJobUri(_id);
                }
                else
                    throw new android.database.SQLException("Failed to insert A row");
                break;
            }
            case check_cat_sp:{
                long _id=db.insert(JobContracts.CheckOut.TABLE_NAME, null, values);
                if(_id>0){
                    returnUri= JobContracts.CheckOut.buildJobUri(_id);
                }
                else
                    throw new android.database.SQLException("Failed to insert A row");
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);//notifies conten observer that the data ha changed see vid 24
        //db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db =mDbHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);
        int rowsDeleted=0;

        switch (match) {
            case food_by:
                rowsDeleted=db.delete(JobContracts.Menu.TABLE_NAME,selection,selectionArgs);
                break;
            case menu_foods:
                rowsDeleted=db.delete(JobContracts.Menu.TABLE_NAME,selection,selectionArgs);
                break;
            case check_cat_sp:
                rowsDeleted=db.delete(JobContracts.CheckOut.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        if (null==selection ||0!=rowsDeleted){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            final SQLiteDatabase db=mDbHelper.getWritableDatabase();
            final int match =sUriMatcher.match(uri);
            int rowsUpdated;
            switch (match) {
                case food_by:
                   rowsUpdated = db.update(JobContracts.Menu.TABLE_NAME, values, selection,
                           selectionArgs);
                    //Log.v("UPDATE","UPDATE OPN");
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri"+uri);
            }
            if (null==selection ||0!=rowsUpdated){
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;
    }
}
