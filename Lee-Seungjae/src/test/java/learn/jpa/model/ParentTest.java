package learn.jpa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

@DataJpaTest
class ParentTest {

    @PersistenceUnit
    EntityManagerFactory emf;

    EntityManager em;
    EntityTransaction tx;

    @BeforeEach
    void connectJPA(){
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }
    @AfterEach
    void closeAll(){
        em.close();
        emf.close();
    }

    @Test
    void 영속성전이테스트(){
        tx.begin();

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();

        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
        em.flush();

        tx.commit();
    }
}