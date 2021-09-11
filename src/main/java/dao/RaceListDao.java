package dao;

import entity.Race;
import entity.RaceList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class RaceListDao extends DefaultDao {
    private RaceDao raceDao = new RaceDao();

    public int getWinRate() {
        Transaction transaction = null;
        int count = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM RaceList r WHERE r.isChosen= true AND r.position = 1";
            Query query = session.createQuery(hql);
            count = query.list().size();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }

    public List<RaceList> getRaceInfo(Race race) {
        Transaction transaction = null;
        List<RaceList> raceLists = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM RaceList r WHERE r.race= :race";
            Query query = session.createQuery(hql);
            query.setParameter("race", race);
            raceLists = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return raceLists;
    }
}
