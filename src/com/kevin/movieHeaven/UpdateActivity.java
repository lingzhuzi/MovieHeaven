package com.kevin.movieHeaven;

import com.kevin.movieHeaven.models.UpdateInfo;
import com.kevin.movieHeaven.tasks.CheckUpdateInfoAsyncTask;
import com.kevin.movieHeaven.utils.UpdateInfoCallback;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class UpdateActivity extends ActionBarActivity implements UpdateInfoCallback {

    private TextView mUpdateInfo;
    private Button mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mUpdateInfo = (TextView) findViewById(R.id.update_info);
        mUpdate = (Button) findViewById(R.id.update_now);

        CheckUpdateInfoAsyncTask task = new CheckUpdateInfoAsyncTask(this);
        task.execute("");
    }

    @Override
    public void succeed(UpdateInfo info, boolean hasNextPage) {
        if (hasNewVersion(info.getVersion())) {
            mUpdateInfo.setText("有新版本V" + info.getVersion() + "\n\n" + info.getLogs());
            mUpdate.setVisibility(View.VISIBLE);

            mUpdate.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String url = "https://raw.githubusercontent.com/lingzhuzi/MovieHeaven/master/release/MovieHeaven.apk";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
        } else {
            mUpdateInfo.setText("已经是最新版本");
        }
    }

    private boolean hasNewVersion(String versionName) {
        MovieHeavenApplication app = (MovieHeavenApplication) getApplication();
        String currentVersionName = app.getVersionName();

        float currentVersion = parseFloat(currentVersionName);
        float version = parseFloat(versionName);

        return version > currentVersion;
    }

    private float parseFloat(String str) {
        return Float.parseFloat(str);
    }

    @Override
    public void failed() {
        mUpdateInfo.setText("检查新版本信息失败，请检查联网设置");
    }
}
