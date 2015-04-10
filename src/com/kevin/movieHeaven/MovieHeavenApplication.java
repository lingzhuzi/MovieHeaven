package com.kevin.movieHeaven;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.activeandroid.ActiveAndroid;
import com.kevin.movieHeaven.R;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

public class MovieHeavenApplication extends Application {
    private boolean preferencesChanged;
    private List<String> movieNameList;
    private List<String> movieUrlList;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        preferencesChanged = true;
        initData();
        loadMenuData();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    private void initData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean inited = prefs.getBoolean("inited", false);
        if (!inited) {
            SharedPreferences.Editor editor = prefs.edit();
            initMenuList(editor);
            initSite(editor);
            editor.putBoolean("inited", true);
            editor.commit();
        }
    }

    private void initMenuList(SharedPreferences.Editor editor) {
        Set<String> set = new HashSet<String>();
        String[] indexes = getResources().getStringArray(R.array.index);
        for (String index : indexes) {
            set.add(index);
        }
        editor.putStringSet("menu_list", set);
    }

    private void initSite(SharedPreferences.Editor editor) {
        editor.putString("site_list", "0");
    }

    public void preferencesChanged() {
        preferencesChanged = true;
    }

    private void loadMenuData() {
        if (!preferencesChanged) {
            return;
        }

        movieNameList = new ArrayList<String>();
        movieUrlList = new ArrayList<String>();

        String[] titles = getResources().getStringArray(R.array.title);
        String[] urls = getResources().getStringArray(R.array.url);
        String[] indexes = getResources().getStringArray(R.array.index);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> options = prefs.getStringSet("menu_list", null);

        if (options != null) {
            for (String index : indexes) {
                if (options.contains(index)) {
                    int position = Integer.parseInt(index);
                    movieNameList.add(titles[position]);
                    movieUrlList.add(urls[position]);
                }
            }
        }

        preferencesChanged = false;
    }

    public List<String> getMovieNameList() {
        loadMenuData();
        return movieNameList;
    }

    public List<String> getMovieUrlList() {
        loadMenuData();
        return movieUrlList;
    }

    public String getSite() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String siteIndex = prefs.getString("site_list", "0");
        String[] sites = getResources().getStringArray(R.array.sites);
        return sites[Integer.parseInt(siteIndex)];
    }

    public int getVersionCode() {
        PackageInfo packageInfo = getPackageInfo();
        return packageInfo != null ? packageInfo.versionCode : null;
    }

    public String getVersionName() {
        PackageInfo packageInfo = getPackageInfo();
        return packageInfo != null ? packageInfo.versionName : null;
    }

    private PackageInfo getPackageInfo() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
