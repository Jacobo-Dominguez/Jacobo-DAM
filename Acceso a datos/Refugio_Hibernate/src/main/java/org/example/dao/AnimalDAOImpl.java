package org.example.dao;

import org.example.Util.HibernateUtil;
import org.example.entities.Animal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class AnimalDAOImpl implements AnimalDAO {

    @Override
    public List<Animal> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Animal> animales = session.createQuery("FROM Animal", Animal.class).list();
        session.close();
        return animales;
    }

    @Override
    public Animal findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Animal animal = session.get(Animal.class, id);
        session.close();
        return animal;
    }

    @Override
    public List<Animal> findByEspecie(String especie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Animal> animales = session.createQuery(
                        "FROM Animal WHERE especie = :especie", Animal.class)
                .setParameter("especie", especie)
                .list();
        session.close();
        return animales;
    }

    @Override
    public Animal create(Animal animal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(animal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return animal;
    }

    @Override
    public Animal update(Animal animal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(animal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return animal;
    }

    @Override
    public boolean deleteById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean borrado = false;
        try {
            transaction = session.beginTransaction();
            Animal animal = session.get(Animal.class, id);
            if (animal != null) {
                session.remove(animal);
                borrado = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return borrado;
    }

    @Override
    public boolean updateEstado(Integer id, String nuevoEstado) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean actualizado = false;
        try {
            transaction = session.beginTransaction();
            Animal animal = session.get(Animal.class, id);
            if (animal != null) {
                animal.setEstado(nuevoEstado);
                session.merge(animal);
                actualizado = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return actualizado;
    }
}
