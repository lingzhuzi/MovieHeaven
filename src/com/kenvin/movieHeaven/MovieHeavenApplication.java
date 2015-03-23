package com.kenvin.movieHeaven;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.activeandroid.ActiveAndroid;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MovieHeavenApplication extends Application {
	private boolean preferencesChanged;
	private List<String> movieNameList;
	private List<String> movieUrlList;

	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
		preferencesChanged  = true;
		loadMenuData();
	}

	@Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
	
	private void initMenuList() {
		// 实例化SharedPreferences对象（第一步）
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = prefs.edit();
		// 用putString的方法保存数据
		Set<String> set = new HashSet<String>();
		String[] indexes = getResources().getStringArray(R.array.index);
		for (String index : indexes) {
			set.add(index);
		}
		editor.putStringSet("menu_list", set);
		// 提交当前数据
		editor.commit();
	}

	public void preferencesChanged(){
		preferencesChanged  = true;
	}
	
	private void loadMenuData() {
		if(!preferencesChanged){
			return;
		}
		preferencesChanged = false;
		
		String[] titles = getResources().getStringArray(R.array.title);
		String[] urls = getResources().getStringArray(R.array.url);
		String[] indexes = getResources().getStringArray(R.array.index);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Set<String> options = prefs.getStringSet("menu_list", null);

		if (options != null) {
			movieNameList = new ArrayList<String>();
			movieUrlList = new ArrayList<String>();

			for (String index : indexes) {
				if(options.contains(index)){
					int position = Integer.parseInt(index);
					movieNameList.add(titles[position]);
					movieUrlList.add(urls[position]);
				}
			}
		} else {
			initMenuList();
			loadMenuData();
		}
	}

	public List<String> getMovieNameList() {
		loadMenuData();
		return movieNameList;
	}

	public  List<String> getMovieUrlList() {
		loadMenuData();
		return movieUrlList;
	}
}
