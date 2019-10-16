package com.nhlprospect.agent.data.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class ParserBase {

    JsonObject parseObject(JsonObject json, String key) {
        return parseElement(json, key).getAsJsonObject();
    }

    JsonElement parseElement(JsonObject json, String key) {
        JsonElement element = json.get(key);
        if(element == null) {
            String error = String.format("Error parsing json, expected to find element with key %s, but it was not found.", key);
            throw new ParsingException(error);
        }
        return element;
    }


}
