package com.gqoha.developerx.bandl;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import data.DatabaseCrud;
import data.JobContracts;


/**
 * Created by DevelopX on 2016-07-22.
 */
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    /*
    This is the dialogue for a tablet screen called differently from that of detail Activity
     */
    private Uri mUri;
    public static final String DETAIL_URI = "URI";
    private static final int DETAIL_LOADER = 0;

    FloatingActionButton fab;

    @Override
        public void onClick(View v) {

    }
    public String name;
    public  String price;
    public String descr ;
    public String id ;

    private static final String[] DETAIL_COLUMNS = {
            JobContracts.Menu.COLUMN_Name_Food,
            JobContracts.Menu.COLUMN_Description,
            JobContracts.Menu.COLUMN_Price,
            JobContracts.Menu.COLUMN_EntryID
    };

    public static final int COL_NAME = 1;
    static final int COL_DECR= 2;
    static final int COL_PRICE= 3;
    static final int COL_EN= 4;


    private TextView A ;
    private TextView B ;
    private TextView C ;
    private TextView D ;
    Button buy;

    View rootView;
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(CustomDialogFragment.DETAIL_URI);
        }
        rootView = inflater.inflate(R.layout.pop, container, false);

A=rootView.findViewById(R.id.name);
B=rootView.findViewById(R.id.price);
C=rootView.findViewById(R.id.descr);
buy=rootView.findViewById(R.id.add);
D=rootView.findViewById(R.id.en);

       buy.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               DatabaseCrud.ToAaddCheckout(getContext(),name,descr,price,id);
               Log.v("all",name+"=="+price+"=="+descr);

               try {
                   CustomDialogFragment close = (CustomDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                   close.dismiss();
                   Snackbar.make(view, "Added to check out", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
               }catch (Exception e){

               }
               Intent intent = new Intent(getActivity(),Checkout.class);
               startActivity(intent);
           }
       });
        return rootView;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Hi");
       // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            //   Log.v("Job onCreateLoader", "mUri is"+mUri);
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String a,b,c,d;
        if (data != null && data.moveToFirst()) {
            a=data.getString(COL_NAME);
            A.setText(a);
            b=data.getString(COL_DECR);
            B.setText(b);
            c=data.getString(COL_PRICE);//COL_PRICE
            C.setText(c);
            d=data.getString(COL_EN);
            D.setText(d);
name=a;
price=b;
descr=c;
id=d;
        }
//        A.setText(Html.fromHtml(data.getString(COL_NAME)));
//        B.setText(Html.fromHtml(data.getString(COL_PRICE)));
//        C.setText(Html.fromHtml(data.getString(COL_DECR)));
//        D.setText(Html.fromHtml(data.getString(COL_EN)));



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }




}
