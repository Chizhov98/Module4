package dao;

import entity.Horse;
import entity.Race;
import entity.RaceList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

public class RaceDao extends DefaultDao{
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Race race = session.get(Race.class, id);
            if (race != null) {
                session.delete(race);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Race read(int id) {
        Race race = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            race = session.get(Race.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return race;
    }
}
