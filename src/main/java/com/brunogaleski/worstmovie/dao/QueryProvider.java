package com.brunogaleski.worstmovie.dao;

public class QueryProvider {

    public final static String MULTIPLE_WINNERS = "WITH multiple_winners AS (\n" +
            "    SELECT * FROM (\n" +
            "        SELECT n2.year as previousWin, n1.year as followingWin, p.name as producer, (n1.year - n2.year) as interval_year\n" +
            "        FROM nominee n1, nominee n2, producer p, nominee_producer np1, nominee_producer np2\n" +
            "        WHERE n1.winner = true AND n2.winner = true \n" +
            "        AND n1.id = np1.nominee_id AND  n2.id = np2.nominee_id\n" +
            "        AND np1.producer_id = np2.producer_id\n" +
            "        AND np1.producer_id = p.id\n" +
            "        AND n1.id <> n2.id\n" +
            "    ) \n" +
            "    WHERE interval_year >= 0 \n" +
            "    GROUP BY producer, previousWin, followingWin\n" +
            "    ORDER BY interval_year\n" +
            "), \n" +
            "intervals AS (SELECT MIN(interval_year) as min_interval, MAX(interval_year) as max_interval FROM multiple_winners)";

    public final static String WINNERS_SHORTEST_INTERVAL = "SELECT * FROM multiple_winners, intervals WHERE interval_year = min_interval";

    public final static String WINNERS_LONGEST_INTERVAL = "SELECT * FROM multiple_winners, intervals WHERE interval_year = max_interval";
}
