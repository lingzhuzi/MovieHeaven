package com.kenvin.movieHeaven;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchResultActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		String keyWord = getKeyWord(getIntent());
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new SearchResultFragment(keyWord)).commit();
	}

	private String getKeyWord(Intent intent) {
		if (intent == null){
			return null;
		}
		
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) { // 如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
			String keyWord = intent.getStringExtra(SearchManager.QUERY);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setTitle(keyWord + "-搜索结果");
			return keyWord; // 获取搜索内容
		}
		
		return null;
	}

}
