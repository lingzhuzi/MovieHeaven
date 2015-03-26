package com.kevin.movieHeaven.fragments;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.kevin.movieHeaven.R;
import com.kevin.movieHeaven.tasks.StarredMovieListAsyncTask;
import com.kevin.movieHeaven.utils.FooterView;
import com.kevin.movieHeaven.utils.MovieListCallback;
import com.kevin.movieHeaven.MovieDetailActivity;

public class StarredMovieListFragment extends ListFragment implements MovieListCallback {
    protected FooterView footerView;
    protected LinearLayout progressLayout;
    protected int currentPage;
    private List<String> movieNameList;
    private List<String> movieUrlList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        progressLayout = (LinearLayout) rootView.findViewById(R.id.progress_layout);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initListView();
    }

    protected void initListView() {
        footerView = new FooterView(getActivity());
        footerView.setClickable(true);
        footerView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loadNextPage();
            }
        });
        getListView().addFooterView(footerView);

        movieNameList = new ArrayList<String>();
        movieUrlList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, movieNameList);
        setListAdapter(adapter);

        currentPage = 1;
        loadData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position >= movieNameList.size()) {
            loadNextPage();
        } else {
            String name = movieNameList.get(position);
            String url = movieUrlList.get(position);

            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

    public void refresh() {
        movieNameList.clear();
        movieUrlList.clear();
        adapter.notifyDataSetChanged();
        currentPage = 1;
        loadData();
    }

    private void loadData() {
        StarredMovieListAsyncTask task = new StarredMovieListAsyncTask(this);
        task.execute(currentPage);
        if (movieNameList.size() > 0) {
            footerView.showProgressBar();
        } else {
            getListView().setVisibility(View.INVISIBLE);
            footerView.hide();
        }
        progressLayout.setVisibility(View.VISIBLE);
    }

    public void loadNextPage() {
        currentPage += 1;
        loadData();
    }

    @Override
    public void succeed(List<String> movieNameList, List<String> movieUrlList, boolean hasNextPage) {
        this.movieNameList.addAll(movieNameList);
        this.movieUrlList.addAll(movieUrlList);
        adapter.notifyDataSetChanged();
        progressLayout.setVisibility(View.INVISIBLE);
        getListView().setVisibility(View.VISIBLE);
        if (hasNextPage) {
            footerView.showText();
        } else {
            footerView.hide();
        }
        if (movieNameList.size() == 0) {
            Toast.makeText(getActivity(), "无结果", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void failed() {}
}
