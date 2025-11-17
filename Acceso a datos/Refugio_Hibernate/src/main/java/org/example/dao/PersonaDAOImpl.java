package org.example.dao;

import org.example.Util.HibernateUtil;
import org.example.entities.Persona;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {

    @Override
    public List<Persona> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Persona> personas = session.createQuery("FROM Persona", Persona.class).list();
        session.close();
        return personas;
    }

    @Override
    public Persona findByDni(String dni) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Persona persona = session.get(Persona.class, dni);
        session.close();
        return persona;
    }

    @Override
    public Persona create(Persona persona) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(persona);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return persona;
    }

    @Override
    public Persona update(Persona persona) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(persona);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return persona;
    }

    @Override
    public boolean deleteByDni(String dni) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean borrado = false;
        try {
            tx = session.beginTransaction();
            Persona persona = session.get(Persona.class, dni);
            if (persona != null) {
                session.remove(persona);
                borrado = true;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return borrado;
    }
}