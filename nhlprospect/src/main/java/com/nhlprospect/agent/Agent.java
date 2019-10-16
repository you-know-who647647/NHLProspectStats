package com.nhlprospect.agent;

import com.nhlprospect.agent.datapuller.LeagueStatGameDataPuller;
import com.nhlprospect.agent.datapuller.LeagueStatLeague;
import com.nhlprospect.agent.datapuller.http.StatPullerHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Agent {

    public static void main(String [] args) {
        System.out.println("Started");
        StatPullerHttpClient client = new StatPullerHttpClient(HttpClients.createDefault());
        LeagueStatGameDataPuller dataPuller = new LeagueStatGameDataPuller(client, "2976319eb44abe94", LeagueStatLeague.ohl, 23212);
        System.out.println(dataPuller.getGameSummary());
    }
}
