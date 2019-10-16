package com.nhlprospect.agent.datapuller;

import com.nhlprospect.agent.data.ScheduledGame;
import com.nhlprospect.agent.data.parser.ScheduleParser;
import com.nhlprospect.agent.datapuller.http.StatPullerHttpClient;

import java.util.List;
import java.util.Objects;

public class LeagueStatSchedulePuller {

    private static final String URL_FORMAT = "https://lscluster.hockeytech.com/feed/?feed=modulekit&view=schedule&key=%s&" +
            "fmt=json&client_code=%s&lang=en&season_id=%d&fmt=json";

    private String key;

    private LeagueStatLeague league;

    private int seasonId;

    private StatPullerHttpClient client;

    private ScheduleParser parser;

    public LeagueStatSchedulePuller(StatPullerHttpClient client, String key, LeagueStatLeague league, Integer seasonId,
                                    ScheduleParser parser) {
        this.client = Objects.requireNonNull(client);
        this.key = Objects.requireNonNull(key);
        this.league = Objects.requireNonNull(league);
        this.seasonId = Objects.requireNonNull(seasonId);
    }

    public String getSchedule() {
        return client.executeGet(getUrl());
    }

    public List<ScheduledGame> getParsedSchedule() {
        return null;
    }

    private String getUrl() {
        return String.format(URL_FORMAT, key, league.name(), seasonId);
    }
}
