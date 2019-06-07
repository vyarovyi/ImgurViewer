package com.imgur.viewer.activities;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imgur.viewer.R;
import com.imgur.viewer.adapters.FeedPageListAdapter;
import com.imgur.viewer.viewmodels.FeedViewModel;

import butterknife.BindView;

public class FeedActivity extends BaseActivity {

    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;

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

        FeedPageListAdapter pageListAdapter = new FeedPageListAdapter();
        viewModel.getItems().observe(this, feedItems -> pageListAdapter.submitList(feedItems));
        viewModel.getNetworkState().observe(this, networkState -> pageListAdapter.setNetworkState(networkState));
        rvFeed.setAdapter(pageListAdapter);
    }
}

