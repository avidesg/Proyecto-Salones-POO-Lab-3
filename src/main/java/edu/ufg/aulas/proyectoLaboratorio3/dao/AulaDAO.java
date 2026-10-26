package edu.ufg.aulas.proyectoLaboratorio3.dao;

import edu.ufg.aulas.proyectoLaboratorio3.entidades.Aula;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.EstadoAula;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.Planta;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.TipoAula;
import edu.ufg.aulas.proyectoLaboratorio3.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AulaDAO {

    public List<Planta> listarPlantas() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery(
                    "FROM Planta p ORDER BY p.numero", Planta.class)
                    .getResultList();
        }
    }

    public List<TipoAula> listarTiposAula() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery(
                    "FROM TipoAula t ORDER BY t.nombre", TipoAula.class)
                    .getResultList();
        }
    }

    public List<EstadoAula> listarEstados() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery(
                    "FROM EstadoAula e ORDER BY e.nombre", EstadoAula.class)
                    .getResultList();
        }
    }

    public boolean existeDuplicado(String nombre, String edificio) {
        String hql = "SELECT COUNT(a) FROM Aula a "
                + "WHERE LOWER(a.nombre) = :nombre "
                + "AND LOWER(a.edificio) = :edificio";

        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Long total = sesion.createQuery(hql, Long.class)
                    .setParameter("nombre", nombre.trim().toLowerCase())
                    .setParameter("edificio", edificio.trim().toLowerCase())
                    .getSingleResult();
            return total > 0;
        }
    }

    public void guardar(Aula aula) {
        Transaction tx = null;
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            tx = sesion.beginTransaction();
            sesion.persist(aula);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}