package com.nhlprospect.agent.datapuller;

import com.nhlprospect.agent.datapuller.http.StatPullerHttpClient;

import java.util.Objects;

public class LeagueStatGameDataPuller {

    private static final String URL_FORMAT = "https://cluster.leaguestat.com/feed/index.php?" +
            "feed=gc&fmt=json&lang_code=en&key=%s&client_code=%s&game_id=%d&tab=%s";

    private static final String GAME_SUMMARY = "gamesummary";

    private String key;

    private LeagueStatLeague league;

    private Integer gameId;

    private StatPullerHttpClient client;

    public LeagueStatGameDataPuller(StatPullerHttpClient client, String key, LeagueStatLeague league, Integer gameId) {
        this.client = Objects.requireNonNull(client);
        this.key = Objects.requireNonNull(key);
        this.league = Objects.requireNonNull(league);
        this.gameId = Objects.requireNonNull(gameId);
    }

    public String getGameSummary(){
        String url = getUrl(GAME_SUMMARY);
        return client.executeGet(url);
    }

    private String getUrl(String target) {
        return String.format(URL_FORMAT, key, league.name(), gameId, target);
    }
}
