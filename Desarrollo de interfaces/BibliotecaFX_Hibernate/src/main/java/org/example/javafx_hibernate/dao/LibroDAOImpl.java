package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.Util.HibernateUtil;
import org.example.javafx_hibernate.entities.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public List<Libro> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Libro> libros = session.createQuery("FROM Libro l LEFT JOIN FETCH l.autor", Libro.class).list();
            return libros; // los autores ya están cargados
        }
    }

    @Override
    public Libro findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Libro.class, id);
        }
    }

    @Override
    public Libro findByIsbn(String isbn) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Libro WHERE isbn = :isbn", Libro.class)
                    .setParameter("isbn", isbn)
                    .uniqueResult();
        }
    }

    @Override
    public List<Libro> findByTitulo(String titulo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Libro WHERE titulo LIKE :titulo", Libro.class)
                    .setParameter("titulo", "%" + titulo + "%")
                    .list();
        }
    }

    @Override
    public List<Libro> findByAutorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Libro l WHERE l.autor.nombre LIKE :nombre", Libro.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .list();
        }
    }

    @Override
    public List<Libro> findDisponibles() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Libro l LEFT JOIN FETCH l.autor WHERE l.disponible = true", Libro.class)
                    .list();
        }
    }

    @Override
    public Libro create(Libro libro) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(libro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return libro;
    }

    @Override
    public Libro update(Libro libro) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(libro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return libro;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Libro libro = session.get(Libro.class, id);
            if (libro != null) {
                session.remove(libro);
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
