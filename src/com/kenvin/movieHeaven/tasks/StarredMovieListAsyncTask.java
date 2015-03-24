package com.kenvin.movieHeaven.tasks;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Select;
import com.kenvin.movieHeaven.models.StarredMovie;
import com.kenvin.movieHeaven.utils.MovieListCallback;

import android.os.AsyncTask;

public class StarredMovieListAsyncTask extends AsyncTask<Integer, Integer, String> {
	private List<String> movieNameList;
	private List<String> movieUrlList;
	private MovieListCallback callback;
	
	public StarredMovieListAsyncTask(MovieListCallback callBack){
		this.movieNameList = new ArrayList<String>();
		this.movieUrlList = new ArrayList<String>();
		this.callback = callBack;
	}
	
	@Override
	protected String doInBackground(Integer... params) {
		int page = params[0];
		List<StarredMovie> movieList = new Select().from(StarredMovie.class).limit(30 + "").offset(30*(page-1) + "").orderBy("ID desc").execute();
		movieNameList = new ArrayList<String>();
		movieUrlList = new ArrayList<String>();
		for(StarredMovie movie : movieList){
			movieNameList.add(movie.name);
			movieUrlList.add(movie.url);
		}
		return movieList.size() == 30 ? "y" : "n";
	}

	@Override  
    protected void onPostExecute(String result) {  
		callback.succeed(movieNameList, movieUrlList, "y".equals(result));
    }  
}
