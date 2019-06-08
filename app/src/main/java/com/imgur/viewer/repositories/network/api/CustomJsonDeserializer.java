package com.imgur.viewer.repositories.network.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.imgur.viewer.BuildConfig;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.ItemTypeDescriptor;
import com.imgur.viewer.repositories.network.models.FeedResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomJsonDeserializer implements JsonDeserializer {
    @Override
    public FeedResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FeedResponse response = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray itemsJsonArray = jsonObject.getAsJsonArray("data");
            List<FeedItem> items = new ArrayList<>();
            for (int i = 0; i < itemsJsonArray.size(); i++) {
                JsonElement typeElement = itemsJsonArray.get(i).getAsJsonObject().get("type");
                FeedItem dematerialized = null;
                if (typeElement != null) {
                    dematerialized = context.deserialize(itemsJsonArray.get(i), FeedItem.class);
                } else {
                    JsonElement image = itemsJsonArray.get(i).getAsJsonObject().get("images").getAsJsonArray().get(0);
                    dematerialized = context.deserialize(image, FeedItem.class);
                }

                switch (dematerialized.getType()){
                    case ItemTypeDescriptor.TYPE_IMAGE_PNG:
                    case ItemTypeDescriptor.TYPE_IMAGE_JPG:
                        items.add(dematerialized);
                        break;
                    case ItemTypeDescriptor.TYPE_VIDEO:
                        if(!BuildConfig.SKIP_VIDEO){
                            items.add(dematerialized);
                        }
                        break;
                    case ItemTypeDescriptor.TYPE_GIF:
                        //Imgur gifs are very buggy, 70-80% doesn't want to load even on PC
                        if(!BuildConfig.SKIP_GIF){
                            items.add(dematerialized);
                        }
                        break;
                }
            }
            boolean success = jsonObject.get("success").getAsBoolean();
            int status = jsonObject.get("status").getAsInt();
//            items.clear();
            response = new FeedResponse(success, status, items);
        } catch (JsonParseException e) {
            Log.e("ERROR", e.toString());
        }
        return response;
    }
}
