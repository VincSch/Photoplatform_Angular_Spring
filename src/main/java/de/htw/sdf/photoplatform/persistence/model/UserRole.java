/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.*;

/**
 * Entity class for a user and his role/s recipes corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "SYS_USER_ROLE")
public class UserRole extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = 1517204631630105586L;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;

    /**
     * UserRole constructor.
     */
    public UserRole() {

    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

}
