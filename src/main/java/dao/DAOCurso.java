package dao;

import connection.ConectaBanco;
import model.Usuario;

import java.lang.reflect.Method;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Curso;

public class DAOCurso {
	EntityManager em;
	
	public List procuraObjeto(Class T, String registro) {
		em = ConectaBanco.getInstancia().getEm();
		em.getTransaction().begin();
		Query q = em.createQuery("from " + T.getSimpleName() + " where (registroUnico is '" + registro + "')");
		em.getTransaction().commit();
		return q.getResultList();
	}

    public void inserir(Object objeto) {
        em = ConectaBanco.getInstancia().getEm();
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
    }

    public void alterar(Object objeto) {
        em = ConectaBanco.getInstancia().getEm();
        em.getTransaction().begin();
        em.merge(objeto);
        em.getTransaction().commit();
    }

    public Object recuperaId(Class classe, Long id) {
        em = ConectaBanco.getInstancia().getEm();
        Object retornando = null;
        em.getTransaction().begin();
        retornando = em.find(classe, id);
        em.getTransaction().commit();
        return retornando;
    }
}
