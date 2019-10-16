package com.nhlprospect.agent.data;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class GameSummary {

    private String league;

    private String homeTeam;

    private String awayTeam;

    private int gameId;

    private Date date;

    private List<Goal> goals;
}
