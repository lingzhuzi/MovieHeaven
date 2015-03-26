package com.kevin.movieHeaven;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kevin.movieHeaven.R;
import com.kevin.movieHeaven.fragments.MovieListFragment;
import com.kevin.movieHeaven.fragments.NavigationDrawerFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.SearchView;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Map<String, MovieListFragment> fragmentMap;
    private MovieListFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        forceShowOverflowMenu();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        MovieListFragment fragment = getFragment(position);

        if (fragment == currentFragment) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.container, fragment);
        }
        transaction.commit();
        currentFragment = fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save noting
    }

    private MovieListFragment getFragment(int position) {
        MovieHeavenApplication app = (MovieHeavenApplication) getApplication();
        List<String> titles = app.getMovieNameList();
        List<String> urls = app.getMovieUrlList();

        String title = titles.get(position);
        String url = urls.get(position);

        mTitle = title;

        if (fragmentMap == null) {
            fragmentMap = new HashMap<String, MovieListFragment>();
        }

        MovieListFragment fragment = fragmentMap.get(title);
        if (fragment == null) {
            fragment = new MovieListFragment(title, url);
            fragmentMap.put(title, fragment);
        }
        return fragment;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    // force to show overflow menu in actionbar
    private void forceShowOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            // 获取SearchView对象
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            if (searchView == null) {
                Log.e("SearchView", "Fail to get Search View.");
                return true;
            }
            searchView.setIconifiedByDefault(true); // 缺省值就是true，可不专门进行设置，true的输入框更大
            // 获取搜索服务管理器
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            // searchable activity的component name，由此系统可通过intent进行唤起
            ComponentName cn = new ComponentName(this, SearchResultActivity.class);
            // 通过搜索管理器，从searchable
            // activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
            SearchableInfo info = searchManager.getSearchableInfo(cn);
            if (info == null) {
                Log.e("SearchableInfo", "Fail to get search info.");
            }
            // 将searchable activity的搜索信息与search view关联
            searchView.setSearchableInfo(info);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch (id) {
        case R.id.action_settings:
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            break;
        case R.id.action_my_starred:
            intent = new Intent(this, StarredMovieActivity.class);
            startActivity(intent);
            break;
        case R.id.action_about:
            intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            break;
        case R.id.action_refresh:
            currentFragment.refresh();
            break;
        default:
            break;
        }

        return super.onOptionsItemSelected(item);
    }

}
