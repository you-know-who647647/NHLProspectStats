package com.nhlprospect.agent.data.parser;

import com.google.gson.Gson;
import com.nhlprospect.agent.data.*;
import com.nhlprospect.agent.stats.PlayerStatsAccumulator;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class LeagueStatGameSummaryParserTest {

    private static final Gson gson = new Gson();

    @Test
    public void testParse() {
        String json = readFile("./test-resources/valid-game-summary.json");
        LeagueStatGameSummaryParser parser = new LeagueStatGameSummaryParser(gson);
        GameSummary summary = parser.parse(json);
        assertNotNull(summary);
        assertEquals(23212, summary.gameId());
        assertValidDate(summary.date());
        assertEquals("SAG", summary.homeTeam());
        assertEquals("SSM", summary.awayTeam());
        assertEquals("ohl", summary.league());
        assertEquals(7, summary.goals().size());
        assertValidGoal(summary.goals().get(0));
    }

    @Test
    public void demo() {
        String json = readFile("./test-resources/valid-game-summary.json");
        LeagueStatGameSummaryParser parser = new LeagueStatGameSummaryParser(gson);
        List<GameSummary> summaries = Arrays.asList(parser.parse(json), parser.parse(json), parser.parse(json), parser.parse(json));
        PlayerStatsAccumulator accumulator = new PlayerStatsAccumulator();
        for(GameSummary gameSummary : summaries) {
            accumulator.accumulateStats(gameSummary);
        }
        for(Player player : accumulator.players()) {
            PlayerSituationalStats stats = player.getSituationalStats(GameSituation.FIVE_ON_FIVE);
            if(stats.goals() > 0 || stats.primaryAssist() > 0 || stats.secondaryAssist() > 0) {
                System.out.println(player.identity().fullName());
                System.out.println(String.format("\tGoals:%d. A1:%d. A2:%d. GF:%d. GA:%d", stats.goals(), stats.primaryAssist(),
                        stats.secondaryAssist(), stats.goalsFor(), stats.goalsAgainst()));
                System.out.println("");
            }
        }
    }
    private void assertValidGoal(Goal goal) {
        assertFalse(goal.isEN());
        assertFalse(goal.isPenaltyShot());
        assertFalse(goal.isSH());
        assertTrue(goal.isPP());
        assertEquals("SAG", goal.teamFor());
        assertEquals("SSM", goal.teamAgainst());
        PlayerIdentity goalScorer = PlayerIdentity.builder().firstName("Cole").lastName("Perfetti").id(7902).build();
        assertPlayerIdentityEquals(goalScorer, goal.goalScorer());
        PlayerIdentity primaryAssist = PlayerIdentity.builder().firstName("DJ").lastName("Busdeker").id(7585).build();
        assertPlayerIdentityEquals(primaryAssist, goal.primaryAssist());
        assertNull(goal.secondaryAssist());
        assertEquals(5, goal.plusPlayers().size());
        assertEquals(4, goal.minusPlayers().size());
        assertEquals(GameSituation.FIVE_ON_FOUR, goal.situation());
    }

    private void assertPlayerIdentityEquals(PlayerIdentity expected, PlayerIdentity actual) {
        assertEquals(expected.firstName(), actual.firstName());
        assertEquals(expected.lastName(), actual.lastName());
        assertEquals(expected.id(), actual.id());
    }

    private void assertValidDate(Date date) {
        DateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);
        String dateString = format.format(date);
        assertEquals("Wednesday, September 19, 2018", dateString);
    }

    private String readFile(String filepath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
