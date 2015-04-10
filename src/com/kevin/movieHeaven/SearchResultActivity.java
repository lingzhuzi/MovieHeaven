package com.kevin.movieHeaven;

import com.kevin.movieHeaven.R;
import com.kevin.movieHeaven.fragments.SearchResultFragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchResultActivity extends ActionBarActivity {

    private SearchResultFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            String keyWord = getKeyWord(getIntent());

            fragment = new SearchResultFragment(keyWord);
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else {
            fragment = (SearchResultFragment) fragmentManager.findFragmentById(R.id.container);
        }
    }

    private String getKeyWord(Intent intent) {
        if (intent == null) {
            return null;
        }

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) { // 如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
            String keyWord = intent.getStringExtra(SearchManager.QUERY);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(keyWord + "--搜索结果");
            return keyWord; // 获取搜索内容
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_result, menu);
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
