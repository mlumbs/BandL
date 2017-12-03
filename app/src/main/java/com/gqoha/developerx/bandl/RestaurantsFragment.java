package com.gqoha.developerx.bandl;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapters.RestaAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class RestaurantsFragment extends Fragment {

    public RestaurantsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_restaurants, container, false);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_restaurants, container, false);
//        RestaAdapter adapter = new RestaAdapter(recyclerView.getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;



    }
}
