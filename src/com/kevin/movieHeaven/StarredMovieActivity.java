package com.kevin.movieHeaven;

import com.kevin.movieHeaven.R;
import com.kevin.movieHeaven.fragments.StarredMovieListFragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;

public class StarredMovieActivity extends ActionBarActivity {

    private StarredMovieListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_movie);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragment = new StarredMovieListFragment();
            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
        } else {
            fragment = (StarredMovieListFragment) fragmentManager.findFragmentById(R.id.container);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.starred_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        case R.id.action_refresh:
            fragment.refresh();
            break;
        default:
            break;
        }

        return super.onOptionsItemSelected(item);
    }

}
