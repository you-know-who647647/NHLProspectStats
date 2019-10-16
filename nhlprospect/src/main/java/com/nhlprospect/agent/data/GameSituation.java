package com.nhlprospect.agent.data;

public enum GameSituation {
    FIVE_ON_FIVE("5v5"),
    FIVE_ON_FOUR("5v4"),
    FIVE_ON_THREE("5v3"),
    FOUR_ON_FOUR("4v4"),
    FOUR_ON_THREE("4v3"),
    FOUR_ON_FIVE("4v5"),
    THREE_ON_THREE("3v3"),
    THREE_ON_FOUR("3v4"),
    THREE_ON_FIVE("3v5"),
    THREE_ON_EN("3vEN"),
    FOUR_ON_EN("4vEN"),
    FIVE_ON_EN("5vEN"),
    EN_ON_THREE("ENv3"),
    EN_ON_FOUR("ENv4"),
    EN_ON_FIVE("ENv5"),
    EN_ON_EN("ENvEN"),
    PENALTY_SHOT("PS");

    private String situation;

    GameSituation(String situation) {
        this.situation = situation;
    }

    public String situation() {
        return this.situation;
    }

    public static GameSituation when(int teamPlayerCount, int oppositionCount, boolean penaltyShot) {
        if(penaltyShot) {
            return GameSituation.PENALTY_SHOT;
        }
        String goalForTeamPlayers = teamPlayerCount > 5 ? "EN" : teamPlayerCount + "";
        String goalAgainstTeamPlayers = oppositionCount > 5 ? "EN" : oppositionCount + "";
        String situation = goalForTeamPlayers + "v" + goalAgainstTeamPlayers;
        for(GameSituation gameSituation : GameSituation.values()) {
            if(gameSituation.situation().equals(situation)) {
                return gameSituation;
            }
        }
        throw new IllegalStateException("No matching situation found for " + situation);
    }
}
