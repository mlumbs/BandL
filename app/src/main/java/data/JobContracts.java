package data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DevelopX on 2015-09-04.
 * Dont forget to change the package name
 */
public class JobContracts {
    public static final String CONTENT_AUTHORITY="com.gqoha.developerx.bandl";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_Food ="DataFoods";
    public static final String PATH_Check ="DataCheck";

    public static final class Menu implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_Food).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Food;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Food;
        public static final String TABLE_NAME ="Food";

        public static final String COLUMN_Name_Food ="C";
        public static final String COLUMN_Description="P";
        public static final String COLUMN_Price="Price";
        public static final String COLUMN_EntryID="ID";
        public static final String COLUMN_Restarent="Re";
        public static final String COLUMN_TAG="tag";//Image link

        public  static Uri buildJobUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri BuildJobId(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri Build_all_resturent_food(String id){
            return CONTENT_URI.buildUpon().appendPath("").appendPath(id).build();
        }

        public static String getIDByFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
        public static String getLastFromUri(Uri uri){
            return uri.getLastPathSegment().toLowerCase();
        }
        //String query = uri.getLastPathSegment().toLowerCase();
    }


    public static final class CheckOut implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_Check).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Check;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Check;
        public static final String TABLE_NAME ="checkout";
        public static final String COLUMN_Name ="name";
        public static final String COLUMN_description ="descr";
        public static final String COLUMN_price ="price";
        public static final String COLUMN_OUT_ID_="out_one";
        public static final String COLUMN_tag_one_="one";
        public static final String COLUMN_tag_two="two";
        public static final String COLUMN_tag_three="three";

        public  static Uri buildJobUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri BuildCheckId(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
        public static String getIDByFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

    }








}
