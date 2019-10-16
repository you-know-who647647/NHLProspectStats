package com.nhlprospect.agent.data;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class Goal {

    private boolean isPP;

    private boolean isEN;

    private boolean isSH;

    private boolean isPenaltyShot;

    private String teamFor;

    private String teamAgainst;

    private PlayerIdentity goalScorer;

    private PlayerIdentity primaryAssist;

    private PlayerIdentity secondaryAssist;

    private List<PlayerIdentity> plusPlayers = new ArrayList<>();

    private List<PlayerIdentity> minusPlayers = new ArrayList<>();

    private GameSituation situation;

    public void complete() {
        situation = GameSituation.when(plusPlayers.size(), minusPlayers.size(), isPenaltyShot);
    }

    public Optional<PlayerIdentity> a1(){
        return Optional.ofNullable(primaryAssist);
    }

    public Optional<PlayerIdentity> a2(){
        return Optional.ofNullable(primaryAssist);
    }

}
