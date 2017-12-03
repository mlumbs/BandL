package Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gqoha.developerx.bandl.Buying;
import com.gqoha.developerx.bandl.CustomDialogFragment;
import com.gqoha.developerx.bandl.MenusFragment;
import com.gqoha.developerx.bandl.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import data.JobContracts;

/**
 * Created by DeveloperX on 18-Nov-17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    // Set numbers of Tiles in RecyclerView.
    private static final int LENGTH = 18;
    FragmentManager fragmentManager;
    //private final String[] mPlaces;
   // private final Drawable[] mPlacePictures;
    CustomDialogFragment newFragment ;
    private Context mContext;

    private Cursor mCurso ;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView picture;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_tile, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            try {

                int adapter_posotion = getAdapterPosition();
                mCurso.moveToPosition(adapter_posotion);
                int ID_CULUMN_INDEX = mCurso.getColumnIndex(JobContracts.Menu._ID);
                int p = mCurso.getInt(ID_CULUMN_INDEX);

                Bundle arguments = new Bundle();
                arguments.putParcelable(CustomDialogFragment.DETAIL_URI, JobContracts.Menu.BuildJobId(p+""));

                newFragment.setArguments(arguments);
                newFragment.show(fragmentManager, "dialog");
//                fragmentManager.beginTransaction()
//                        .replace(R.id.my_recycler_view, new Buying())
//                        .commit();

            }catch (Exception e){
                Log.e("Error","The dialogue is already active");
            }
        }
    }

    public MenuAdapter(Context context) {

        mContext=context;
        FragmentActivity c;
        c=(FragmentActivity)mContext;
        fragmentManager = c.getSupportFragmentManager();
        newFragment = new CustomDialogFragment();
//        Resources resources = context.getResources();
//        mPlaces = resources.getStringArray(R.array.food_description);
//        TypedArray a = resources.obtainTypedArray(R.array.food_picture);
//        mPlacePictures = new Drawable[a.length()];
//        for (int i = 0; i < mPlacePictures.length; i++) {
//            mPlacePictures[i] = a.getDrawable(i);
//        }
//        a.recycle();
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuAdapter.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        mCurso.moveToPosition(position);

        holder.name.setText(mCurso.getString(MenusFragment.COL_NAME));
        //String url = "http://192.168.56.1:8080/B&L/"+ mCurso.getString(MenusFragment.COL_TAG)+".jpg";
        String url = "http://192.168.56.1:8080/B&L/ab.png";

//        Log.v("debug",url);
//        Glide.with(mContext)
//                .load(url)
//                .into(holder.picture);
        holder.picture.setImageResource(getDrawable(position));
    }



    public void swapCursor(Cursor newCursor)
    {
        mCurso =newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        if(null==mCurso){
            Log.v("debug","0");

            return 0;}

        return mCurso.getCount();
    }

    public static int getDrawable(int image) {
        if (image ==0) {
            return R.drawable.aa;
        } else if (image ==1) {
            return R.drawable.bb;
        } else if (image==2) {
            return R.drawable.cc;
        } else if (image == 3) {
            return R.drawable.dd;
        } else if (image ==4) {
            return R.drawable.ee;
        } else if (image ==5) {
            return R.drawable.af;
        }
        return 0;
    }





}
