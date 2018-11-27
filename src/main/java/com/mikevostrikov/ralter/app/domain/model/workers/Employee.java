package com.mikevostrikov.ralter.app.domain.model.workers;

import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Employee {

    private Long emId;
    private String emName;
    private String emPhone;
    private String emAddress;
    private String emPosition;
    private String emComment;
    private String emEmail;
    private BigDecimal emSalary;
    private User user;
    
    private String trimIfNotNull(String input) {
        if (input == null) return null;
        return input.trim();
    }

    public Long getEmId() {
        return this.emId;
    }    
    public void setEmId(Long emId) {
        this.emId = emId;
    }
    
    public String getEmName() {
        return this.emName;
    }
    public void setEmName(String emName) {
        this.emName = trimIfNotNull(emName);
    }
    
    public String getEmPhone() {
        return this.emPhone;
    }
    public void setEmPhone(String emPhone) {
        this.emPhone = trimIfNotNull(emPhone);
    }
    
    public String getEmAddress() {
        return this.emAddress;
    }
    public void setEmAddress(String emAddress) {
        this.emAddress = trimIfNotNull(emAddress);
    }
    
    public String getEmPosition() {
        return this.emPosition;
    }
    public void setEmPosition(String emPosition) {
        this.emPosition = trimIfNotNull(emPosition);
    }
    
    public String getEmComment() {
        return this.emComment;
    }
    public void setEmComment(String emComment) {
        this.emComment = trimIfNotNull(emComment);
    }
    
    public String getEmEmail() {
        return this.emEmail;
    }
    public void setEmEmail(String emEmail) {
        this.emEmail = trimIfNotNull(emEmail);
    }
    
    public BigDecimal getEmSalary() {
        return this.emSalary;
    }
    public void setEmSalary(BigDecimal emSalary) {
        this.emSalary = emSalary;
    }
    
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Employee) {
            return this.emId.equals(((Employee)object).emId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.emId);
        return hash;
    }
    
}