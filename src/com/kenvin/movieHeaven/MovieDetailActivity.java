package com.kenvin.movieHeaven;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

public class MovieDetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);
		Intent intent = getIntent();
		String title = intent.getStringExtra("name");
		String url = intent.getStringExtra("url");
		setTitle(title);
		WebView detailView = (WebView)findViewById(R.id.detail_view);
		
		MovieDetailAsyncTask task = new MovieDetailAsyncTask(detailView);
		task.execute(url);
	}

}
