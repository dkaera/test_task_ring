package com.testproject.kaera.ringtestapp.service.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class APIRedditItemAdapter implements JsonDeserializer<List<APIRedditItem>> {

    private Gson gson;

    public APIRedditItemAdapter() {
        this.gson = new GsonBuilder().serializeNulls().create();
    }

    @Override public List<APIRedditItem> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray itemsArray = getDataObject(json).getAsJsonArray("children");
        List<APIRedditItem> redditItems = new ArrayList<>();

        for (JsonElement jsonElement : itemsArray) {
            JsonObject dataObject = getDataObject(jsonElement);
            String thumbFullSizeUrl = dataObject.has("preview") ? getFullSizeThumb(dataObject) : "";

            APIRedditItem parsedItem = gson.fromJson(dataObject, APIRedditItem.class);
            parsedItem.setThumbFullSize(thumbFullSizeUrl);
            parsedItem.setCreatedDate(new Date(TimeUnit.SECONDS.toMillis(parsedItem.getCreated())));

            redditItems.add(parsedItem);
        }
        return redditItems;
    }

    private JsonObject getDataObject(JsonElement jsonElement) {
        return jsonElement.getAsJsonObject().getAsJsonObject("data");
    }

    private String getFullSizeThumb(JsonObject asJsonObject) {
        return asJsonObject.getAsJsonObject("preview")
                .getAsJsonArray("images")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("source")
                .getAsJsonPrimitive("url")
                .getAsString();
    }
}