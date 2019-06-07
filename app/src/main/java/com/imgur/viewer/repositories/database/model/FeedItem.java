package com.imgur.viewer.repositories.database.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "feeds")
public class FeedItem extends BaseObservable {

    @androidx.annotation.NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "type")
    @SerializedName(value = "type")
    @ItemTypeDescriptor.ItemTypeDef
    String type;

    @ColumnInfo(name = "width")
    @SerializedName(value = "width")
    int width;

    @ColumnInfo(name = "height")
    @SerializedName(value = "height")
    int height;

    @ColumnInfo(name = "link")
    @SerializedName(value = "link")
    String link;

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public String getType() {
        return type;
    }

    @Bindable
    public int getWidth() {
        return width;
    }

    @Bindable
    public int getHeight() {
        return height;
    }

    @Bindable
    public String getLink() {
        return link.replace("http:", "https:"); //hack, some urls are http://
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static DiffUtil.ItemCallback<FeedItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<FeedItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull FeedItem oldItem, @NonNull FeedItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FeedItem oldItem, @NonNull FeedItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };
}
