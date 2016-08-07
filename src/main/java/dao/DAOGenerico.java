package dao;

import connection.ConectaBanco;
import model.Usuario;

import java.lang.reflect.Method;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Fernando
 */
public class DAOGenerico{

    EntityManager em;

      public List listaCondicaoStatus(String condicao) {
        em = ConectaBanco.getInstancia().getEm();
        em.getTransaction().begin();
        Query q = em.createQuery("from " + Usuario.class.getSimpleName()+" where (statusSincronizacao is '"+condicao+"')");
        em.getTransaction().commit();
        return q.getResultList();
    }
    

    public void inserir(Object objeto) {
        em = ConectaBanco.getInstancia().getEm();
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
    }

    public void deletar(Object objeto) throws Exception {
        em = ConectaBanco.getInstancia().getEm();
        em.getTransaction().begin();
        Method getChave = objeto.getClass().getMethod("getId", new Class[0]);
        objeto = em.find(objeto.getClass(), getChave.invoke(objeto, new Object[0]));
        em.remove(objeto);
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
