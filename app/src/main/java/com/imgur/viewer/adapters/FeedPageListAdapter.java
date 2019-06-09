package com.imgur.viewer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imgur.viewer.R;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.imgur.viewer.repositories.database.model.ItemTypeDescriptor.TYPE_GIF;
import static com.imgur.viewer.repositories.database.model.ItemTypeDescriptor.TYPE_IMAGE_JPG;
import static com.imgur.viewer.repositories.database.model.ItemTypeDescriptor.TYPE_IMAGE_PNG;
import static com.imgur.viewer.repositories.database.model.ItemTypeDescriptor.TYPE_VIDEO;

public class FeedPageListAdapter extends PagedListAdapter<FeedItem, RecyclerView.ViewHolder> {

    private NetworkState networkState;

    public FeedPageListAdapter() {
        super(FeedItem.DIFF_CALLBACK);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.item_feed) {
            View view = layoutInflater.inflate(R.layout.item_feed, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        } else if (viewType == R.layout.item_network) {
            View view = layoutInflater.inflate(R.layout.item_network, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalStateException("Unknown type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_feed:
                ((ItemViewHolder) holder).onBindViewHolder(getItem(position));
                break;
            case R.layout.item_network:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network;
        } else {
            return R.layout.item_feed;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView image;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        //TODO: 1) implement thumbnail loading
        //TODO: 2) pre calculate correct size for a view even if image hasn't been loaded yet
        //TODO: 3) load images in advance
        void onBindViewHolder(FeedItem item) {
            switch (item.getType()) {
                case TYPE_IMAGE_PNG:
                case TYPE_IMAGE_JPG:
                    Glide.with(image.getContext())
                            .asBitmap()
                            .override(item.getWidth(), item.getHeight())
                            .thumbnail(0.25f)
                            .load(item.getLink())
                            .fitCenter()
                            .placeholder(R.drawable.loading_placeholder)
                            .error(R.drawable.error_placeholder)
                            .into(image);
                    break;
                case TYPE_GIF:
                    Glide.with(image.getContext())
                            .asGif()
                            .override(item.getWidth(), item.getHeight())
                            .thumbnail(0.25f)
                            .load(item.getLink())
                            .fitCenter()
                            .placeholder(R.drawable.loading_placeholder)
                            .error(R.drawable.error_placeholder)
                            .into(image);
                    break;
                case TYPE_VIDEO:
                    //TODO: implement video player, for now we skip all video items. Check {@link CustomJsonDeserializer}
                    break;
            }
        }
    }

    static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.error_msg)
        TextView errorMsg;

        NetworkStateItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
