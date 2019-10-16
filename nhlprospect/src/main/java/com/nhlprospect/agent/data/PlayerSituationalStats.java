package com.nhlprospect.agent.data;

import lombok.Getter;

@Getter
public class PlayerSituationalStats {

    private int goals = 0;

    private int primaryAssist = 0;

    private int secondaryAssist = 0;

    private int goalsFor = 0;

    private int goalsAgainst = 0;

    public void addGoal() {
        goals += 1;
    }

    public void addPrimaryAssist() {
        primaryAssist += 1;
    }

    public void addSecondaryAssist() {
        secondaryAssist += 1;
    }

    public void addGoalFor() {
        goalsFor += 1;
    }

    public void addGoalAgainst() {
        goalsAgainst += 1;
    }
}
