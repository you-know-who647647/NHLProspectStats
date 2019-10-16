package com.nhlprospect.agent.stats;

import com.nhlprospect.agent.data.*;

import java.util.*;

/**
 * Generates stats from game by game summaries
 */
public class PlayerStatsAccumulator {

    private Map<PlayerIdentity, Player> identityToPlayer = new HashMap<>();

    public PlayerStatsAccumulator(){}

    public PlayerStatsAccumulator(List<Player> players) {
        for(Player player : players) {
            identityToPlayer.put(player.identity(), player);
        }
    }

    public Collection<Player> players() {
        return identityToPlayer.values();
    }

    public void accumulateStats(GameSummary gameSummary) {
        for(Goal goal : gameSummary.goals()) {
            initPlayersIfNeeded(goal);
            accumulateStats(goal);
        }
    }

    private void accumulateStats(Goal goal) {
        GameSituation situation = goal.situation();
        goal.plusPlayers().forEach(plusPlayer -> {
            identityToPlayer.get(plusPlayer).addGoalFor(situation);
        });
        goal.minusPlayers().forEach(minusPlayerIdentity -> {
            identityToPlayer.get(minusPlayerIdentity).addGoalAgainst(situation);
        });
        goal.a1().ifPresent(primaryAssistIdentity -> {
            identityToPlayer.get(primaryAssistIdentity).addPrimaryAssist(situation);
        });
        goal.a2().ifPresent(secondaryAssistIdentity -> {
            identityToPlayer.get(secondaryAssistIdentity).addSecondaryAssist(situation);
        });
        identityToPlayer.get(goal.goalScorer()).addGoal(situation);
    }

    private void initPlayersIfNeeded(Goal goal) {
        List<PlayerIdentity> playersInvolved = new LinkedList<>();
        playersInvolved.addAll(goal.plusPlayers());
        playersInvolved.addAll(goal.minusPlayers());
        for(PlayerIdentity playerIdentity : playersInvolved) {
            if(!identityToPlayer.containsKey(playerIdentity)) {
                identityToPlayer.put(playerIdentity, new Player(playerIdentity));
            }
        }
    }
}
