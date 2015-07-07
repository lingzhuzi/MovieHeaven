package com.kevin.movieHeaven;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.kevin.movieHeaven.R;
import com.kevin.movieHeaven.models.StarredMovie;
import com.kevin.movieHeaven.tasks.MovieDetailAsyncTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MovieDetailActivity extends ActionBarActivity {

    private String title;
    private String url;
    protected LinearLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);

        Intent intent = getIntent();
        title = getMovieName(intent.getStringExtra("name"));
        url = intent.getStringExtra("url");

        setTitle("影片详情--" + title);
        loadMovieDetails();
    }

    private void loadMovieDetails() {
        WebView detailView = (WebView) findViewById(R.id.detail_view);
        detailView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    progressLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
        MovieDetailAsyncTask task = new MovieDetailAsyncTask(detailView);
        task.execute(url);
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_star);
        if (item != null) {
            if (hasStarred(title, url)) {
                item.setTitle("取消收藏");
            } else {
                item.setTitle("收藏");
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        case R.id.action_star:
            starThisMovie();
            break;
        case R.id.action_share:
            shareThisMovie();
            break;
        case R.id.action_refresh:
            loadMovieDetails();
            break;
        default:
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getMovieName(String title) {
        return title.replaceAll("(.*)《(.*)》(.*)", "$2");
    }

    private void starThisMovie() {
        if (hasStarred(title, url)) {
            new Delete().from(StarredMovie.class).where("name = ? or url = ?", title, url).execute();
            Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            new StarredMovie(title, url).save();
            Toast.makeText(this, "收藏成功", Toast.LENGTH_LONG).show();
        }
    }

    private void shareThisMovie() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "【阳光电影】" + title + "：" + url + "，分享自阳光电影安卓客户端[https://raw.githubusercontent.com/lingzhuzi/MovieHeaven/master/release/MovieHeaven.apk]");
        startActivity(intent);
    }

    private boolean hasStarred(String title, String url) {
        int size = new Select().from(StarredMovie.class).where("name = ? and url = ?", title, url).execute().size();
        return size > 0;
    }
}
