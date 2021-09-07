package dao;

import entity.Horse;
import entity.RaceList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

public class RaceListDao extends DefaultDao {

    public void delete(int id){
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            RaceList raceList = session.get(RaceList.class, id);
            if (raceList != null) {
                session.delete(raceList);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public RaceList read(int id) {
        RaceList raceList = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            raceList = session.get(RaceList.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return raceList;
    }
}
