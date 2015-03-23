package com.kenvin.movieHeaven;

import com.kenvin.movieHeaven.fragments.StarredMovieListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class StarredMovieActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starred_movie);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new StarredMovieListFragment()).commit();
		}
	}
}
