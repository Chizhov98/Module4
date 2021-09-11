package dao;

import entity.Horse;
import entity.Race;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class RaceDao extends DefaultDao {
    public Race read(int id) {
        Race race = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    public List<Race> readAll() {
        List<Race> races = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Race";
            Query query = session.createQuery(hql);
            races = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return races;
    }

    public int getRaceCount() {
        List<Race> races = readAll();
        return races != null ? races.size() : 0;
    }
}
