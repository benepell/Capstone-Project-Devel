package info.pelleritoudacity.android.rcapstone.data.rest.deserialize;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.More;
import info.pelleritoudacity.android.rcapstone.utility.Costant;

class MoreDeserializer implements JsonDeserializer<More> {

    @Override
    public More deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        JsonObject objectData = json.getAsJsonObject();

        String strRemoveEmptyString = objectData.toString().replaceAll(Costant.JSON_REPLIES_EMPTY, Costant.JSON_REPLIES_REPLACE);

        return gson.fromJson(strRemoveEmptyString, More.class);

    }

}