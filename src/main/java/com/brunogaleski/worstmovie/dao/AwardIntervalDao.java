package com.brunogaleski.worstmovie.dao;

import com.brunogaleski.worstmovie.model.AwardInterval;
import com.brunogaleski.worstmovie.model.AwardIntervalsDTO;
import com.brunogaleski.worstmovie.model.IntervalType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AwardIntervalDao {

    private final EntityManagerFactory entityManagerFactory;

    private Session currentSession;

    private Transaction currentTransaction;

    public AwardIntervalDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public AwardIntervalsDTO getAwardIntervals() {
        Session session = openCurrentSessionWithTransaction();
        List<AwardInterval> shortestIntervalWinners = getIntervalWinners(session, IntervalType.SHORTEST);
        List<AwardInterval> longestIntervalWinners = getIntervalWinners(session, IntervalType.LONGEST);
        closeCurrentSessionWithTransaction();

        return new AwardIntervalsDTO(shortestIntervalWinners, longestIntervalWinners);
    }

    public List<AwardInterval> getIntervalWinners(Session session, IntervalType intervalType) {
        List<AwardInterval> awardIntervals = new ArrayList<>();
        String intervalTypeQuery = intervalType.equals(IntervalType.SHORTEST) ? QueryProvider.WINNERS_SHORTEST_INTERVAL : QueryProvider.WINNERS_LONGEST_INTERVAL;
        Query query = session.createNativeQuery(QueryProvider.MULTIPLE_WINNERS + intervalTypeQuery);

        List<Object[]> rows = query.getResultList();
        for(Object[] row : rows){
            AwardInterval awardInterval = new AwardInterval();
            awardInterval.setPreviousWin(Integer.parseInt(row[0].toString()));
            awardInterval.setFollowingWin(Integer.parseInt(row[1].toString()));
            awardInterval.setProducer(row[2].toString());
            awardInterval.setInterval(Integer.parseInt(row[3].toString()));
            awardIntervals.add(awardInterval);
        }

        return awardIntervals;
    }

    private SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

}
