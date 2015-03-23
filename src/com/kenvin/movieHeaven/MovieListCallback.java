package com.kenvin.movieHeaven;

import java.util.List;

public interface MovieListCallback {
	public void succeed(List<String> movieNameList, List<String> movieUrlList, boolean hasNextPage);
	public void failed();
}
