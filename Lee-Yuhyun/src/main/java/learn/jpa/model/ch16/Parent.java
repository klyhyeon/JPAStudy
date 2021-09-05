package learn.jpa.model.ch16;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parent {

    @Id
    private Long id;

}
