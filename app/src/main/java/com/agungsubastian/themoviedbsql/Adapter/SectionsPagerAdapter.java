package com.agungsubastian.themoviedbsql.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.agungsubastian.themoviedbsql.Fragment.FavoriteMovieFragment;
import com.agungsubastian.themoviedbsql.Fragment.FavoriteTVFragment;
import com.agungsubastian.themoviedbsql.Fragment.MovieFragment;
import com.agungsubastian.themoviedbsql.Fragment.TVFragment;
import com.agungsubastian.themoviedbsql.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.movies,
            R.string.tv,
            R.string.favorite_movies,
            R.string.favorite_tv
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TVFragment();
                break;
            case 2:
                fragment = new FavoriteMovieFragment();
                break;
            case 3:
                fragment = new FavoriteTVFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }
}
