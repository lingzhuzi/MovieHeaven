package com.kenvin.movieHeaven;

import android.support.v7.app.ActionBarActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TextView mAuthor = (TextView)findViewById(R.id.author);
		mAuthor.setText("作者：杨辉");
		
		// 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        TextView mVersion = (TextView)findViewById(R.id.version);
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(),0);
			String version = packInfo.versionName;
			mVersion.setText("V" + version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			mVersion.setVisibility(View.GONE);
		}
	}
}
