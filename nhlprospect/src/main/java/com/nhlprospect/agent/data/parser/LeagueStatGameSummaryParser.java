package com.nhlprospect.agent.data.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nhlprospect.agent.data.GameSummary;
import com.nhlprospect.agent.data.Goal;
import com.nhlprospect.agent.data.PlayerIdentity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeagueStatGameSummaryParser extends ParserBase {

    private static final Integer GOAL_FOR_TEAM = 0;

    private static final Integer GOAL_AGAINST_TEAM = 1;

    private final Gson gson;

    private static final DateFormat DEFAULT = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);

    public LeagueStatGameSummaryParser(Gson gson) {
        this.gson = Objects.requireNonNull(gson);
    }

    public GameSummary parse(String jsonString) {
        JsonObject json = gson.fromJson(jsonString, JsonObject.class);
        JsonObject gameSummary = parseObject(parseObject(json, "GC"), "Gamesummary");
        String homeTeam = parseElement(gameSummary, "home").getAsJsonObject().get("team_code").getAsString();
        String awayTeam = parseElement(gameSummary, "visitor").getAsJsonObject().get("team_code").getAsString();
        return GameSummary.builder()
                .league(parseLeagueName(json))
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .gameId(parseElement(gameSummary, "game_ident").getAsInt())
                .date(getDate(parseElement(gameSummary, "game_date").getAsString(), DEFAULT))
                .goals(parseGoals(gameSummary, homeTeam, awayTeam))
                .build();
    }

    private List<Goal> parseGoals(JsonObject gameSummary, String homeTeam, String awayTeam) {
        List<Goal> goals = new ArrayList<>();
        JsonArray goalsArray = parseElement(gameSummary, "goals").getAsJsonArray();
        for(JsonElement goalElement : goalsArray) {
            goals.add(parseGoal(goalElement.getAsJsonObject(), homeTeam, awayTeam));
        }
        return goals;
    }

    private Goal parseGoal(JsonObject goalJson, String homeTeam, String awayTeam) {
        List<String> goalTeamsInfo = getGoalTeamsInfo(goalJson, homeTeam, awayTeam);
        Goal goal = Goal.builder()
                .isEN(parseIntAsBoolean(goalJson, "empty_net"))
                .isPenaltyShot(parseIntAsBoolean(goalJson, "penalty_shot"))
                .isPP(parseIntAsBoolean(goalJson, "power_play"))
                .isSH(parseIntAsBoolean(goalJson, "short_handed"))
                .teamFor(goalTeamsInfo.get(GOAL_FOR_TEAM))
                .teamAgainst(goalTeamsInfo.get(GOAL_AGAINST_TEAM))
                .goalScorer(parsePlayerIdentity(goalJson, "goal_scorer"))
                .primaryAssist(parsePlayerIdentity(goalJson, "assist1_player"))
                .plusPlayers(parsePlayerIdentities(goalJson, "plus"))
                .minusPlayers(parsePlayerIdentities(goalJson, "minus"))
                .build();
        goal.complete();
        return goal;
    }

    private List<PlayerIdentity> parsePlayerIdentities(JsonObject goalJson, String key) {
        List<PlayerIdentity> players = new ArrayList<>();
        JsonElement element = goalJson.get(key);
        if(element == null || element.isJsonNull()) {
            return players;
        }
        for(JsonElement player : element.getAsJsonArray()) {
            players.add(parsePlayerIdentity(player.getAsJsonObject()));
        }
        return players;
    }

    private List<String> getGoalTeamsInfo(JsonObject goalJson, String homeTeam, String awayTeam) {
        List<String> goalTeamsInfo = new ArrayList<>(2);
        int scoredByHomeTeam = parseElement(goalJson, "home").getAsInt();
        if(scoredByHomeTeam == 1) {
            goalTeamsInfo.add(GOAL_FOR_TEAM, homeTeam);
            goalTeamsInfo.add(GOAL_AGAINST_TEAM, awayTeam);
        } else {
            goalTeamsInfo.add(GOAL_FOR_TEAM, awayTeam);
            goalTeamsInfo.add(GOAL_AGAINST_TEAM, homeTeam);
        }
        return goalTeamsInfo;
    }

    private PlayerIdentity parsePlayerIdentity(JsonObject goalJson, String key) {
        JsonObject player = parseObject(goalJson, key);
        JsonElement playerIdElement  = player.get("player_id");
        if(playerIdElement == null || playerIdElement.isJsonNull()) {
            return null;
        }
        return parsePlayerIdentity(player);
    }

    private PlayerIdentity parsePlayerIdentity(JsonObject player) {
        return PlayerIdentity.builder()
                .id(parseElement(player, "player_id").getAsInt())
                .firstName(parseElement(player, "first_name").getAsString())
                .lastName(parseElement(player, "last_name").getAsString())
                .build();
    }

    private boolean parseIntAsBoolean(JsonObject goalJson, String key) {
        int val = parseElement(goalJson, key).getAsInt();
        return val != 0;
    }

    private String parseLeagueName(JsonObject json) {
        JsonObject params = parseObject(parseObject(json, "GC"), "Parameters");
        return parseElement(params, "client_code").getAsString();
    }

    private Date getDate(String dateString, DateFormat format) {
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
