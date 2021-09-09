package dao;

import entity.Horse;
import entity.RaceList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

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
    public List<RaceList> getRaceInfo(int raceId){
        List<RaceList> list = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM RaceList WHERE race = :id ORDER BY position";
            Query query = session.createQuery(hql);
            query.setParameter("id", raceId);
            list = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return list;
    }
}
