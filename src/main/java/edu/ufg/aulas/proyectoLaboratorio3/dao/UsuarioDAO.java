package edu.ufg.aulas.proyectoLaboratorio3.dao;

import edu.ufg.aulas.proyectoLaboratorio3.entidades.Usuario;
import edu.ufg.aulas.proyectoLaboratorio3.util.HibernateUtil;
import org.hibernate.Session;

public class UsuarioDAO {

    public Usuario autenticar(String correo, String clave) {
        String hql = "FROM Usuario u "
                + "WHERE u.Correo = :correo AND u.Clave = :clave "
                + "AND u.Estado = 'ACTIVO'";

        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            return sesion.createQuery(hql, Usuario.class)
                    .setParameter("correo", correo)
                    .setParameter("clave", clave)
                    .uniqueResultOptional()
                    .orElse(null);
        }
    }
}