package com.agungsubastian.themoviedbsql.Fragment;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.agungsubastian.themoviedbsql.Adapter.TVAdapter;
import com.agungsubastian.themoviedbsql.Database.FavoriteTVHelper;
import com.agungsubastian.themoviedbsql.Model.ResultItemTV;
import com.agungsubastian.themoviedbsql.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVFragment extends Fragment {

    private ProgressBar progressBar;
    private TVAdapter adapter;
    private FavoriteTVHelper favoriteTVHelper;

    public FavoriteTVFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView rv_tv = view.findViewById(R.id.rv_tv);

        adapter = new TVAdapter();
        rv_tv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv_tv.setAdapter(adapter);
        showLoading();

        favoriteTVHelper = new FavoriteTVHelper(getContext());
        favoriteTVHelper = FavoriteTVHelper.getInstance(getContext());
        favoriteTVHelper.open();

        new LoadDataTV().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadDataTV extends AsyncTask<Void,Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteTVHelper.queryAll();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            List<ResultItemTV> items = new ArrayList<>();
            while (cursor.moveToNext()){
                ResultItemTV item = new ResultItemTV();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setOriginalName(cursor.getString(1));
                item.setFirstAirDate(cursor.getString(2));
                item.setOverview(cursor.getString(3));
                item.setPosterPath(cursor.getString(4));
                item.setVoteAverage(Float.parseFloat(cursor.getString(5)));
                items.add(item);
            }
            adapter.replaceAll(items);
            hideLoading();
        }
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }

}
