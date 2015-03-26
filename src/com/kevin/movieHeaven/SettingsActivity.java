package com.kevin.movieHeaven;

import com.kevin.movieHeaven.R;
import com.kevin.movieHeaven.fragments.SettingsFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).commit();
    }
}
