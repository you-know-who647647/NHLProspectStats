package com.nhlprospect.agent.data.parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nhlprospect.agent.data.SeasonSchedule;

import java.util.Objects;

public class ScheduleParser {

    private final Gson gson;

    public ScheduleParser(Gson gson) {
        this.gson = Objects.requireNonNull(gson);
    }

    public SeasonSchedule parse(String jsonString) {
        JsonObject json = gson.fromJson(jsonString, JsonObject.class);

        return null;
    }
}
