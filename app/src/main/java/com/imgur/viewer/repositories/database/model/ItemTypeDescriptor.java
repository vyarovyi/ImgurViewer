package com.imgur.viewer.repositories.database.model;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ItemTypeDescriptor {
    public static final String TYPE_IMAGE_PNG = "image/png";
    public static final String TYPE_IMAGE_JPG = "image/jpeg";
    public static final String TYPE_GIF = "image/gif";
    public static final String TYPE_VIDEO = "video/mp4";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_IMAGE_PNG, TYPE_IMAGE_JPG, TYPE_GIF, TYPE_VIDEO})
    public @interface ItemTypeDef {
    }
}
