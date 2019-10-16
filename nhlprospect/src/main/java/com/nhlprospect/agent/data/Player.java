package com.nhlprospect.agent.data;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Player {

    private PlayerIdentity identity;

    private Map<GameSituation, PlayerSituationalStats> situationalStatsMap = new HashMap<>();

    private int goals, assists, points, penaltyMinutes, shots;

    public Player(PlayerIdentity identity) {
        this.identity = identity;
    }

    public PlayerSituationalStats getSituationalStats(GameSituation situation) {
        if(!situationalStatsMap.containsKey(situation)) {
            return new PlayerSituationalStats();
        }
        return situationalStatsMap.get(situation);
    }


    public void addGoalFor(GameSituation situation) {
        initSituationIfNeeded(situation);
        situationalStatsMap.get(situation).addGoalFor();
    }

    public void addGoalAgainst(GameSituation situation) {
        initSituationIfNeeded(situation);
        situationalStatsMap.get(situation).addGoalAgainst();
    }

    public void addGoal(GameSituation situation) {
        initSituationIfNeeded(situation);
        situationalStatsMap.get(situation).addGoal();
    }

    public void addPrimaryAssist(GameSituation situation) {
        initSituationIfNeeded(situation);
        situationalStatsMap.get(situation).addPrimaryAssist();
    }

    public void addSecondaryAssist(GameSituation situation) {
        initSituationIfNeeded(situation);
        situationalStatsMap.get(situation).addSecondaryAssist();
    }

    private void initSituationIfNeeded(GameSituation situation) {
        if(!situationalStatsMap.containsKey(situation)) {
            situationalStatsMap.put(situation, new PlayerSituationalStats());
        }
    }
}
