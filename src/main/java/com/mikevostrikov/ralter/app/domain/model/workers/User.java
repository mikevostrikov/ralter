package com.mikevostrikov.ralter.app.domain.model.workers;

import java.util.Collection;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User {
    
    private Long usId;
    private Role role;
    private Employee employee;
    private String usLogin;
    private String usPassword;
    private Integer defaultControllerId;
    
    public User(){}

    public User(Long usId, Role role, Employee employees, String usLogin, String usPassword, Integer defaultControllerId) {
        this.usId = usId;
        this.role = role;
        this.employee = employees;
        this.usLogin = usLogin;
        this.usPassword = usPassword;
        this.defaultControllerId = defaultControllerId;
    }
   
    public Long getUsId() {
        return this.usId;
    }
    
    public void setUsId(Long usId) {
        this.usId = usId;
    }
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    public Employee getEmployee() {
        return this.employee;
    }
    
    public void setEmployee(Employee employees) {
        this.employee = employees;
    }
    public String getUsLogin() {
        return this.usLogin;
    }
    
    public void setUsLogin(String usLogin) {
        this.usLogin = usLogin;
    }
    public String getUsPassword() {
        return this.usPassword;
    }
    
    public void setUsPassword(String usPassword) {
        this.usPassword = usPassword;
    }
    
    public void setDefaultControllerId(Integer defaultControllerId) {
        this.defaultControllerId = defaultControllerId;
    }
    
    public int getDefaultControllerId() {
        return defaultControllerId;
    }
    
    public boolean isAuthorizedForResourceId(long resourceId) {
        return role.isAuthorizedForResourceId(resourceId);
    }

    public Collection<Long> getResourceIds() {
        return role.getResourceIds();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true);
    }
    
}