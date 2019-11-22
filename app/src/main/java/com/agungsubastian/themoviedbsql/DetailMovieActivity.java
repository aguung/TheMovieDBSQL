package com.agungsubastian.themoviedbsql;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agungsubastian.themoviedbsql.Database.DatabaseContract;
import com.agungsubastian.themoviedbsql.Database.FavoriteMovieHelper;
import com.agungsubastian.themoviedbsql.Model.ResultItemMovies;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_DATA = "extra_data";
    private boolean isFavorite = false;
    private FloatingActionButton favorite;
    private FavoriteMovieHelper favoriteMovieHelper;
    private ResultItemMovies item;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        item = getIntent().getParcelableExtra(EXTRA_DATA);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tv_date = findViewById(R.id.date);
        TextView tv_score = findViewById(R.id.score);
        TextView tv_description = findViewById(R.id.description);
        ImageView iv_image = findViewById(R.id.img_photo);
        favorite = findViewById(R.id.favorite);

        toolbar.setTitle(item.getOriginalTitle());
        tv_date.setText(item.getReleaseDate());
        tv_score.setText(String.valueOf(item.getVoteAverage()));
        tv_description.setText(item.getOverview());
        Glide.with(DetailMovieActivity.this)
                .load(BuildConfig.BASE_URL_IMG+"w500"+item.getPosterPath())
                .error(R.drawable.ic_error)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv_image);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        loadDataSQLite(String.valueOf(item.getId()));

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) FavoriteRemove();
                else FavoriteSave();

                isFavorite = !isFavorite;
                setFavorite();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        if (favoriteMovieHelper != null) favoriteMovieHelper.close();
        super.onDestroy();
    }

    private void setFavorite(){
        if (isFavorite) favorite.setImageResource(R.drawable.ic_favorite);
        else favorite.setImageResource(R.drawable.ic_unfavorite);
    }

    private void loadDataSQLite(String id){
        favoriteMovieHelper = new FavoriteMovieHelper(this);
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(this);
        favoriteMovieHelper.open();

        Cursor cursor = favoriteMovieHelper.queryById(id);

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setFavorite();
    }

    private void FavoriteSave() {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.FavoriteColumns.ID, item.getId());
        cv.put(DatabaseContract.FavoriteColumns.TITLE, item.getOriginalTitle());
        cv.put(DatabaseContract.FavoriteColumns.DESCRIPTION, item.getOverview());
        cv.put(DatabaseContract.FavoriteColumns.DATE, item.getReleaseDate());
        cv.put(DatabaseContract.FavoriteColumns.SCORE, item.getVoteAverage());
        cv.put(DatabaseContract.FavoriteColumns.IMAGE, item.getPosterPath());

        favoriteMovieHelper.insert(cv);
        Toast.makeText(this, R.string.add_movie, Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove() {
        favoriteMovieHelper.deleteById(String.valueOf(item.getId()));
        Toast.makeText(this, R.string.remove_movie, Toast.LENGTH_SHORT).show();
    }
}
