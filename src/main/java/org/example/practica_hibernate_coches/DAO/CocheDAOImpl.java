package org.example.practica_hibernate_coches.DAO;

import org.example.practica_hibernate_coches.model.Coche;
import org.example.practica_hibernate_coches.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CocheDAOImpl implements CocheDAO {

    public void saveCoche(Coche coche, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(coche);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }


    public Coche getCocheById(int id, Session session) {
        Transaction transaction = null;
        Coche coche = null;
        try {
            transaction = session.beginTransaction();
            coche = session.get(Coche.class, id);
            transaction.commit();


        } catch (Exception e) {

            if (transaction != null)
                transaction.rollback();
        }
        return coche;
    }


    @SuppressWarnings("unchecked")
    public List<Coche> getAllCoches(Session session) {
        Transaction transaction = null;
        List<Coche> coches = null;
        try {
            transaction = session.beginTransaction();
            coches = (List<Coche>) session.createQuery("from Coche", Coche.class).list();
            Query query = session.createQuery("from Coches");
            coches = (List<Coche>) query.getResultList();
            coches = session.createQuery("from User").list();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
        return coches;
    }


    public void updateCoche(Coche coche, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(coche);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }


    public void deleteCocheById(int id, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Coche coche = session.get(Coche.class, id);
            session.delete(coche);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }
}
