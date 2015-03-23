package com.kenvin.movieHeaven;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.Bundle;
import android.widget.Toast;

public class SearchResultFragment extends MovieListFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String SECTION_KEY = "section_key";
	

	public SearchResultFragment(String keyWord) {
		super("", "");
		Bundle args = new Bundle();
		args.putString(SECTION_KEY, keyWord);
		this.setArguments(args);
	}

	protected String getUrl(){
		String keyWord = getArguments().getString(SECTION_KEY);
		try {
			if(keyWord.getBytes().length < 3){
				Toast.makeText(getActivity(), "关键字不能少于3个字节", Toast.LENGTH_LONG).show();
				footerView.hide();
				return null;
			}
			String url = "http://s.kujian.com/plus/search.php?kwtype=0&PageNo=" + currentPage + "&keyword=" + URLEncoder.encode(keyWord, "gbk");
			return url;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
