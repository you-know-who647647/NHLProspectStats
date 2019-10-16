package com.nhlprospect.agent.data;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class SeasonSchedule {

    private int seasonId;

    private List<ScheduledGame> games;

    public SeasonSchedule(int seasonId, List<ScheduledGame> games) {
        this.seasonId = seasonId;
        this.games = Objects.requireNonNull(games);
    }

}
