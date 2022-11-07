package io.manju.vertx.crudapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.manju.vertx.crudapi.entity.Role;


public class RoleDao {
	private static RoleDao instance;
    protected EntityManager entityManager;

    public static RoleDao getInstance()
    	{
        if (instance == null){
            instance = new RoleDao();
        }
        return instance;
    	}

    private RoleDao()
    	{
        entityManager = getEntityManager();
    	}

    private EntityManager getEntityManager()
    	{
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    	}
	 
    


@SuppressWarnings("unchecked")
public List<Role> findAlll() {
  return entityManager.createQuery("FROM " + Role.class.getName()).getResultList();
}
public void persist(Role role) {
    try {
        entityManager.getTransaction().begin();
        entityManager.persist(role);
        entityManager.getTransaction().commit();
    } catch (Exception ex) {
        ex.printStackTrace();
        entityManager.getTransaction().rollback();
    }
}

}