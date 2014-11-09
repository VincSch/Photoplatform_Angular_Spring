/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseEntity;

/**
 * Entity class for a role representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "SYS_ROLE")
public class Role extends AbstractBaseEntity {

    /** Role Admin. */
    public static final String ADMIN = "ADMIN";

    /** Role User. */
    public static final String CUSTOMER = "CUSTOMER"; // (Member)

    /** Role Photographer. */
    public static final String PHOTOGRAPHER = "PHOTOGRAPHER";

    /**
     * Default roles, added on start up. (Order is important).
     */
    public static final List<String> DEFAULT_ROLES = new ArrayList<>();
    static
    {
        DEFAULT_ROLES.add(ADMIN); // Role has id 1
        DEFAULT_ROLES.add(CUSTOMER);
        DEFAULT_ROLES.add(PHOTOGRAPHER);
    }

    private static final long serialVersionUID = -219754255462744875L;

    @Column(name = "NAME", unique = true)
    private String name;

    /**
     * Role constructor.
     */
    public Role() {
        super();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
