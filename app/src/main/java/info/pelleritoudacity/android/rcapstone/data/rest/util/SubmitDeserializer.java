package info.pelleritoudacity.android.rcapstone.data.rest.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.SubmitData;

class SubmitDeserializer implements JsonDeserializer<SubmitData> {

    @Override
    public SubmitData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject objectData = json.getAsJsonObject();
        JsonArray jsonArray = objectData.getAsJsonArray("jquery");


        List<List<String>> resultList = new ArrayList<>();
        List<JsonArray> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.get(i).getAsJsonArray());
        }
        
        for (JsonArray s : list) {
            List<String> innerList = new ArrayList<>();
            for (int i = 0; i < s.size(); i++) {
                String jsonElementString = s.get(i).toString();
                String stringValue = jsonElementString.replace("[", "")
                        .replace("]", "").replace("\"", "");
                innerList.add(stringValue);
            }
            resultList.add(innerList);
        }


        return new SubmitData(
                objectData.get("success").getAsBoolean(),
                resultList);

    }

}