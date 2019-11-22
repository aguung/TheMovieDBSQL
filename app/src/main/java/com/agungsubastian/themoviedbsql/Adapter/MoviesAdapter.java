package com.agungsubastian.themoviedbsql.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agungsubastian.themoviedbsql.BuildConfig;
import com.agungsubastian.themoviedbsql.DetailMovieActivity;
import com.agungsubastian.themoviedbsql.Model.ResultItemMovies;
import com.agungsubastian.themoviedbsql.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {
    private List<ResultItemMovies> listGetMovies = new ArrayList<>();

    public MoviesAdapter() {}


    public void replaceAll(List<ResultItemMovies> items) {
        listGetMovies.clear();
        listGetMovies = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv, viewGroup, false);
        return new MoviesHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        holder.bind(listGetMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return listGetMovies.size();
    }

    class MoviesHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtScore;
        private TextView txtDate;
        private TextView txtDescription;
        private ImageView imgData;

        MoviesHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtScore = itemView.findViewById(R.id.txt_score);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtDescription = itemView.findViewById(R.id.txt_description);
            imgData = itemView.findViewById(R.id.img_photo);
        }

        void bind(final ResultItemMovies item) {
            txtTitle.setText(item.getOriginalTitle());
            txtScore.setText(String.valueOf(item.getVoteAverage()));
            txtDate.setText(item.getReleaseDate());
            txtDescription.setText(item.getOverview());
            Glide.with(itemView.getContext())
                    .load(BuildConfig.BASE_URL_IMG +"/w92/"+item.getPosterPath())
                    .error(R.drawable.ic_error)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imgData);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_DATA, item);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public List<ResultItemMovies> getList(){
        return listGetMovies;
    }

    public void setMovieResult(List<ResultItemMovies> movieResult){
        this.listGetMovies = movieResult;
    }
}
