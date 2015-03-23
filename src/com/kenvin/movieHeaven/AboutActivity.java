package com.kenvin.movieHeaven;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TextView mAuthor = (TextView)findViewById(R.id.author);
		mAuthor.setText("作者：杨辉");
	}
}
