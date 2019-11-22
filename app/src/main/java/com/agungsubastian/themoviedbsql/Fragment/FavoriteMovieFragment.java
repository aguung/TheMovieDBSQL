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

import com.agungsubastian.themoviedbsql.Adapter.MoviesAdapter;
import com.agungsubastian.themoviedbsql.Database.FavoriteMovieHelper;
import com.agungsubastian.themoviedbsql.Model.ResultItemMovies;
import com.agungsubastian.themoviedbsql.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {

    private ProgressBar progressBar;
    private MoviesAdapter adapter;
    private FavoriteMovieHelper favoriteMovieHelper;

    public FavoriteMovieFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView rv_movies = view.findViewById(R.id.rv_movie);

        adapter = new MoviesAdapter();
        rv_movies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv_movies.setAdapter(adapter);
        showLoading();
        favoriteMovieHelper = new FavoriteMovieHelper(getContext());
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(getContext());
        favoriteMovieHelper.open();
        new LoadDataMovie().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadDataMovie extends AsyncTask<Void,Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteMovieHelper.queryAll();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            List<ResultItemMovies> items = new ArrayList<>();
            while (cursor.moveToNext()){
                ResultItemMovies item = new ResultItemMovies();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setOriginalTitle(cursor.getString(1));
                item.setReleaseDate(cursor.getString(2));
                item.setOverview(cursor.getString(3));
                item.setPosterPath(cursor.getString(4));
                item.setVoteAverage(Float.parseFloat(cursor.getString(5)));
                items.add(item);
            }
            adapter.replaceAll(items);
            favoriteMovieHelper.close();
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
