package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.Util.HibernateUtil;
import org.example.javafx_hibernate.entities.Prestamo;
import org.example.javafx_hibernate.entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {

    @Override
    public List<Prestamo> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Prestamo ORDER BY fechaPrestamo DESC", Prestamo.class).list();
        }
    }

    @Override
    public List<Prestamo> findPrestamosActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Prestamo WHERE fechaDevolucion IS NULL", Prestamo.class)
                    .list();
        }
    }

    @Override
    public List<Prestamo> findHistorialBySocio(Socio socio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Prestamo WHERE socio = :socio ORDER BY fechaPrestamo DESC", Prestamo.class)
                    .setParameter("socio", socio)
                    .list();
        }
    }

    @Override
    public Prestamo create(Prestamo prestamo) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(prestamo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return prestamo;
    }

    @Override
    public boolean marcarComoDevuelto(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Prestamo prestamo = session.get(Prestamo.class, id);
            if (prestamo != null && prestamo.getFechaDevolucion() == null) {
                prestamo.setFechaDevolucion(LocalDate.now());
                // Actualizar también el libro a disponible
                prestamo.getLibro().setDisponible(true);
                session.merge(prestamo);
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