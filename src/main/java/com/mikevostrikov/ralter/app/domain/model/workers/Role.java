package com.mikevostrikov.ralter.app.domain.model.workers;

import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Role {

    private Long roId;
    private String roName;
    private Set<User> users;
    private Set<Long> resourceIds;
   
    public Long getRoId() {
        return this.roId;
    }
    
    public void setRoId(Long roId) {
        this.roId = roId;
    }
    public String getRoName() {
        return this.roName;
    }
    
    public void setRoName(String roName) {
        this.roName = roName;
    }  
    
    public boolean isAuthorizedForResourceId(long resourceId) {
        return resourceIds.contains(resourceId);
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Long> getResourceIds() {
        return Collections.unmodifiableSet(resourceIds);
    }

    public void setResourceIds(Set<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true);
    }
    
}
