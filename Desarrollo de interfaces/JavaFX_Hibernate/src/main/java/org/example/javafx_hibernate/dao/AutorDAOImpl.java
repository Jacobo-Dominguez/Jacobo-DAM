package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.Util.HibernateUtil;
import org.example.javafx_hibernate.entities.Autor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AutorDAOImpl implements AutorDAO {

    @Override
    public List<Autor> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Autor", Autor.class).list();
        }
    }

    @Override
    public Autor findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Autor.class, id);
        }
    }

    @Override
    public List<Autor> findByNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Autor WHERE nombre LIKE :nombre", Autor.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .list();
        }
    }

    @Override
    public Autor create(Autor autor) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(autor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return autor;
    }

    @Override
    public Autor update(Autor autor) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(autor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return autor;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Autor autor = session.get(Autor.class, id);
            if (autor != null) {
                // Opcional: verificar que no tenga libros asociados
                session.remove(autor);
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