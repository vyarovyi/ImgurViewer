package com.imgur.viewer.activities;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imgur.viewer.R;
import com.imgur.viewer.adapters.FeedPageListAdapter;
import com.imgur.viewer.repositories.database.model.NetworkState;
import com.imgur.viewer.viewmodels.FeedViewModel;

import butterknife.BindView;

public class FeedActivity extends BaseActivity {

    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;

    @BindView(R.id.tvInfo)
    TextView tvInfo;

    protected FeedViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvFeed.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        //TODO: add swipe to refresh that reads from the first page and add elements into the beginning

        FeedPageListAdapter pageListAdapter = new FeedPageListAdapter();
        viewModel.getItems().observe(this, feedItems -> pageListAdapter.submitList(feedItems));
        viewModel.getNetworkState().observe(this, networkState -> {
            boolean showList = true;
            if (networkState == NetworkState.INITIALIZING) {
                tvInfo.setText(R.string.initializing);
                showList = false;
            } else if (networkState == NetworkState.EMPTY) {
                tvInfo.setText(R.string.empty);
                showList = false;
            } else {
                pageListAdapter.setNetworkState(networkState);
            }
            tvInfo.setVisibility(showList ? View.GONE : View.VISIBLE);
            rvFeed.setVisibility(showList ? View.VISIBLE: View.GONE);
        });
        rvFeed.setAdapter(pageListAdapter);
    }
}

