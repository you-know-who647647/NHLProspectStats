package com.nhlprospect.agent.data;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ScheduledGame {

    private int gameId;

    private int seasonId;

    private Date date;

    private String homeTeam;

    private String awayTeam;
}
