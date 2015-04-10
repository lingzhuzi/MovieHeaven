package com.kevin.movieHeaven;

import com.kevin.movieHeaven.R;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mAuthor = (TextView) findViewById(R.id.author);
        mAuthor.setText("作者：杨辉");

        TextView mVersion = (TextView) findViewById(R.id.version);
        MovieHeavenApplication app = (MovieHeavenApplication) getApplication();
        String version = app.getVersionName();
        if (version != null) {
            mVersion.setText("V" + version);
        } else {
            mVersion.setVisibility(View.GONE);
        }
    }
}
