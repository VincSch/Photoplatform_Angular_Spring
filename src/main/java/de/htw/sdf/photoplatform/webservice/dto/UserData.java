/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data transfer object to get and update user data.
 * Represents the domain object User.
 *
 * @author Sergej Meister
 */
public class UserData {

    @NotEmpty
    protected Long id;

    @NotEmpty
    @Size(min = 3, max = 30)
    protected String username;

    protected Integer index;

    protected Boolean banned;

    protected Boolean enabled;

    protected Boolean admin;

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Returns user id.
     *
     * @return user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets user id.
     *
     * @param id uder id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns index.
     *
     * @return index.
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index index.
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * Is user banned.
     *
     * @return true if banned.
     */
    public Boolean isBanned() {
        return banned;
    }

    /**
     * Sets value user banned.
     *
     * @param banned is banned.
     */
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }


    /**
     * Is admin.
     *
     * @return true if user has role admin.
     */
    public Boolean isAdmin() {
        return admin;
    }

    /**
     * Set admin value.
     *
     * @param admin isAdmin.
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * Returns is user enabled.
     *
     * @return true if enabled.
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets value to enabled.
     * 
     * @param enabled true if enabled.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
