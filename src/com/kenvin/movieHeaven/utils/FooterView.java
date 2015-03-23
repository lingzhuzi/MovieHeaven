package com.kenvin.movieHeaven.utils;

import com.kenvin.movieHeaven.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class FooterView extends LinearLayout {

	private LinearLayout mText;
	private ProgressBar mProgressBar;

	private int paddingTop;
	private int paddingBottom;

	public FooterView(Context context) {
		this(context, null);
	}

	public FooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.footer_view, this);
		findViews();
	}

	/**
	 * 初始化属性
	 */
	public void findViews() {

		mText = (LinearLayout) findViewById(R.id.text_layout);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

		paddingTop = getPaddingTop();
		paddingBottom = getPaddingBottom();

		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * 隐藏footer
	 */
	public void hide() {
		setPadding(0, -1 * getHeight(), 0, 0);
		setVisibility(View.GONE);
	}

	public void show() {
		showText();
	}

	/**
	 * 显示“更多”并隐藏progressBar
	 */
	public void showText() {
		setPadding(0, paddingTop, 0, paddingBottom);
		setVisibility(View.VISIBLE);
		mText.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * 显示footer中的progressBar，并隐藏“更多”
	 */
	public void showProgressBar() {
		mText.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

}
