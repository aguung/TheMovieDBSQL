package com.agungsubastian.themoviedbsql.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVModel {

    @SerializedName("results")
    @Expose
    private List<ResultItemTV> results = null;

    public List<ResultItemTV> getResults() {
        return results;
    }
}