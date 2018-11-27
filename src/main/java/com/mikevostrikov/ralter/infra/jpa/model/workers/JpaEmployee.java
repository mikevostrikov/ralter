package com.mikevostrikov.ralter.infra.jpa.model.workers;

import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class JpaEmployee extends Employee implements Serializable {

    @Override
    @OneToOne(targetEntity=JpaUser.class, mappedBy="employee", cascade = {})
    public JpaUser getUser() {
        return (JpaUser) super.getUser();
    }

    @Override
    @Column(name = "SALARY")
    public BigDecimal getEmSalary() {
        return super.getEmSalary();
    }

    @Override
    @Column(name = "EMAIL")
    public String getEmEmail() {
        return super.getEmEmail();
    }

    @Override
    @Column(name = "COMMENT")
    public String getEmComment() {
        return super.getEmComment();
    }

    @Override
    @Column(name = "POSITION")
    public String getEmPosition() {
        return super.getEmPosition();
    }

    @Override
    @Column(name = "ADDRESS")
    public String getEmAddress() {
        return super.getEmAddress();
    }

    @Override
    @Column(name = "PHONE")
    public String getEmPhone() {
        return super.getEmPhone();
    }

    @Override
    @Column(name = "NAME")
    public String getEmName() {
        return super.getEmName();
    }

    @Override
    @Id
    @SequenceGenerator(name="EMPLOYEE_SEQ", sequenceName="EMPLOYEE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_SEQ")
    @Column(name = "EMPLOYEE_ID")
    public Long getEmId() {
        return super.getEmId();
    }

}