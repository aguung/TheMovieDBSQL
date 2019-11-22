package com.agungsubastian.themoviedbsql.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agungsubastian.themoviedbsql.Adapter.MoviesAdapter;
import com.agungsubastian.themoviedbsql.Helper.ApiClient;
import com.agungsubastian.themoviedbsql.Model.MoviesModel;
import com.agungsubastian.themoviedbsql.Model.ResultItemMovies;
import com.agungsubastian.themoviedbsql.R;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private MoviesAdapter adapter;
    private ApiClient apiClient = new ApiClient();
    private String language;
    public MovieFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView rv_movies = view.findViewById(R.id.rv_movie);

        adapter = new MoviesAdapter();
        rv_movies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv_movies.setAdapter(adapter);

        if(Locale.getDefault().getDisplayLanguage().equals("Indonesia")){
            language = "id-ID";
        }else{
            language = "en-US";
        }

        if(savedInstanceState != null){
            String languageold = savedInstanceState.getString("language");
            assert languageold != null;
            if(languageold.equals(language)){
                ArrayList<ResultItemMovies> list;
                list = savedInstanceState.getParcelableArrayList("movies");
                adapter.setMovieResult(list);
                rv_movies.setAdapter(adapter);
            }else{
                getDataMovie();
            }
        }else{
            getDataMovie();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", new ArrayList<>(adapter.getList()));
        outState.putString("language", language);
    }

    private void getDataMovie(){
        showLoading();
        Call<MoviesModel> apiCall = apiClient.getService().getMovies(language);
        apiCall.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    adapter.replaceAll(response.body().getResults());
                } else {
                    Toast.makeText(getContext(), R.string.error_load, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), R.string.time_out, Toast.LENGTH_SHORT).show();
                } else if (t instanceof UnknownHostException) {
                    Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }

}
