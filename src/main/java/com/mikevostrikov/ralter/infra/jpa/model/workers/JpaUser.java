package com.mikevostrikov.ralter.infra.jpa.model.workers;

import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class JpaUser extends User implements Serializable {

    @Override
    @Column(name = "DEFAULT_CONTROLLER_ID")
    public int getDefaultControllerId() {
        return super.getDefaultControllerId();
    }

    @Override
    @Column(name = "PASSWORD_HASH")
    public String getUsPassword() {
        return super.getUsPassword();
    }

    @Override
    @Column(name = "USERNAME")
    public String getUsLogin() {
        return super.getUsLogin();
    }

    @Override
    @OneToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    public JpaEmployee getEmployee() {
        return (JpaEmployee) super.getEmployee();
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    public JpaRole getRole() {
        return (JpaRole) super.getRole();
    }

    @Override
    @Id
    @SequenceGenerator(name="USER_SEQ", sequenceName="USER_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Column(name = "USER_ID")
    public Long getUsId() {
        return super.getUsId();
    }
    
}