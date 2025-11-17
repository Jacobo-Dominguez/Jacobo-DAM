package org.example.dao;

import org.example.Util.HibernateUtil;
import org.example.entities.Clasificacion;
import org.example.entities.TipoAnimal;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClasificacionDAOImpl implements ClasificacionDAO {

    @Override
    public Clasificacion findByTipoAnimal(TipoAnimal tipoAnimal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Clasificacion clasificacion = session.createQuery(
                        "FROM Clasificacion c WHERE c.tipoAnimal = :tipo", Clasificacion.class)
                .setParameter("tipo", tipoAnimal)
                .uniqueResult();
        session.close();
        return clasificacion;
    }

    @Override
    public Clasificacion create(Clasificacion clasificacion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(clasificacion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return clasificacion;
    }
}
