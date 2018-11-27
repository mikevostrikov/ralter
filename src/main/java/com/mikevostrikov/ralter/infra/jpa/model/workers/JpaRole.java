package com.mikevostrikov.ralter.infra.jpa.model.workers;
// Generated 28.03.2009 2:16:48 by Hibernate Tools 3.2.1.GA


import com.mikevostrikov.ralter.app.domain.model.workers.Role;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "ROLE")
public class JpaRole extends Role implements Serializable {

    @Override
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name ="ROLE_X_RESOURCE", joinColumns=@JoinColumn(name="ROLE_ID"))
    @Column(name="RESOURCE_ID")
    public Set<Long> getResourceIds() {
        return super.getResourceIds(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @OneToMany(targetEntity = JpaUser.class, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<User> getUsers() {
        return (Set<User>) super.getUsers(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Column(name = "NAME")
    public String getRoName() {
        return super.getRoName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Id
    @SequenceGenerator(name="ROLE_SEQ", sequenceName="ROLE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
    @Column(name = "ROLE_ID")
    public Long getRoId() {
        return super.getRoId(); //To change body of generated methods, choose Tools | Templates.
    }
        
}
