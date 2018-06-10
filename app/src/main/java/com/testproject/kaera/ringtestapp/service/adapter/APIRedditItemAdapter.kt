package com.testproject.kaera.ringtestapp.service.adapter

import com.google.gson.*
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

class APIRedditItemAdapter : JsonDeserializer<List<APIRedditItem>> {

    private val gson: Gson = GsonBuilder().serializeNulls().create()

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<APIRedditItem> {
        val itemsArray = getDataObject(json).getAsJsonArray("children")
        val redditItems = ArrayList<APIRedditItem>()

        for (jsonElement in itemsArray) {
            val dataObject = getDataObject(jsonElement)
            val thumbFullSizeUrl = if (dataObject.has("preview")) getFullSizeThumb(dataObject) else ""

            val parsedItem = gson.fromJson<APIRedditItem>(dataObject, APIRedditItem::class.java)
            parsedItem.setThumbFullSize(thumbFullSizeUrl)
            parsedItem.setCreatedDate(Date(TimeUnit.SECONDS.toMillis(parsedItem.getCreated())))

            redditItems.add(parsedItem)
        }
        return redditItems
    }

    private fun getDataObject(jsonElement: JsonElement): JsonObject {
        return jsonElement.asJsonObject.getAsJsonObject("data")
    }

    private fun getFullSizeThumb(asJsonObject: JsonObject): String {
        return asJsonObject.getAsJsonObject("preview")
                .getAsJsonArray("images")
                .get(0)
                .asJsonObject
                .getAsJsonObject("source")
                .getAsJsonPrimitive("url")
                .asString
    }
}