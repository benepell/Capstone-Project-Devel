package info.pelleritoudacity.android.rcapstone.rest;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import info.pelleritoudacity.android.rcapstone.model.reddit.T1;

public class T1Deserializer implements JsonDeserializer<T1> {

    @Override
    public T1 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        T1 t1Response = gson.fromJson(json, T1.class);

        if ((!TextUtils.isEmpty(t1Response.getKind())) &&
                (t1Response.getKind().equals("Listing"))) {
            // The full response as a json object
            final JsonObject jsonObject = json.getAsJsonObject();


        }

        return t1Response;

    }


}