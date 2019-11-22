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

import com.agungsubastian.themoviedbsql.Adapter.TVAdapter;
import com.agungsubastian.themoviedbsql.Helper.ApiClient;
import com.agungsubastian.themoviedbsql.Model.ResultItemTV;
import com.agungsubastian.themoviedbsql.Model.TVModel;
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
public class TVFragment extends Fragment {

    private ProgressBar progressBar;
    private TVAdapter adapter;
    private ApiClient apiClient = new ApiClient();
    private String language;
    public TVFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView rv_tv = view.findViewById(R.id.rv_tv);

        adapter = new TVAdapter();
        rv_tv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv_tv.setAdapter(adapter);
        if(Locale.getDefault().getDisplayLanguage().equals("Indonesia")){
            language = "id-ID";
        }else{
            language = "en-US";
        }

        if(savedInstanceState != null){
            String languageold = savedInstanceState.getString("language");
            assert languageold != null;
            if(languageold.equals(language)){
                ArrayList<ResultItemTV> list;
                list = savedInstanceState.getParcelableArrayList("tv");
                adapter.setTVResult(list);
                rv_tv.setAdapter(adapter);
            }else{
                getDataTV();
            }
        }else{
            getDataTV();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("tv", new ArrayList<>(adapter.getList()));
        outState.putString("language", language);
    }

    private void getDataTV(){
        showLoading();
        Call<TVModel> apiCall = apiClient.getService().getTV(language);
        apiCall.enqueue(new Callback<TVModel>() {
            @Override
            public void onResponse(@NonNull Call<TVModel> call, @NonNull Response<TVModel> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    adapter.replaceAll(response.body().getResults());
                } else {
                    Toast.makeText(getContext(), R.string.error_load, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TVModel> call, @NonNull Throwable t) {
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
