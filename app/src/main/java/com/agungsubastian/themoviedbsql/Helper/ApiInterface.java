package com.agungsubastian.themoviedbsql.Helper;

import com.agungsubastian.themoviedbsql.Model.MoviesModel;
import com.agungsubastian.themoviedbsql.Model.TVModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie")
    Call<MoviesModel> getMovies(@Query("language") String language);

    @GET("tv")
    Call<TVModel> getTV(@Query("language") String language);
}
