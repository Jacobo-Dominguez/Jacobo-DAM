package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.Util.HibernateUtil;
import org.example.javafx_hibernate.entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SocioDAOImpl implements SocioDAO {

    @Override
    public List<Socio> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socio", Socio.class).list();
        }
    }

    @Override
    public Socio findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Socio.class, id);
        }
    }

    @Override
    public List<Socio> findByNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socio WHERE nombre LIKE :nombre", Socio.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .list();
        }
    }

    @Override
    public List<Socio> findByTelefono(String telefono) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socio WHERE telefono = :telefono", Socio.class)
                    .setParameter("telefono", telefono)
                    .list();
        }
    }

    @Override
    public Socio create(Socio socio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(socio);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return socio;
    }

    @Override
    public Socio update(Socio socio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(socio);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return socio;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Socio socio = session.get(Socio.class, id);
            if (socio != null) {
                session.remove(socio);
                tx.commit();
                return true;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }
}
