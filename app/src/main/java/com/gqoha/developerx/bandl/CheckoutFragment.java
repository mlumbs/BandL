package com.gqoha.developerx.bandl;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Adapters.CheckAdapter;
import data.JobContracts;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{



    public static final String[] JOB_COLUMNS = {
            JobContracts.CheckOut._ID ,
            JobContracts.CheckOut.COLUMN_Name,
            JobContracts.CheckOut.COLUMN_description,
            JobContracts.CheckOut.COLUMN_price,
    };
    public static final int COL_ID = 0;  //ID
    public static final int COL_NAME = 1;//Name
    public static final int COL_DESC= 2; //resta
    public static final int COL_PRICE=3;//price;


    private static final int _LOADER = 0;
    CheckAdapter adapter;
    public CheckoutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //   return inflater.inflate(R.layout.fragment_checkout, container, false);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_checkout, container, false);
       adapter = new CheckAdapter(getContext(), (TextView) getActivity().findViewById(R.id.total));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //   Log.v("Job onCreateLoader", "mUri is"+mUri);
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                JobContracts.CheckOut.CONTENT_URI,
                JOB_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(_LOADER, null, this);

    }
}
