/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import de.htw.sdf.photoplatform.persistence.model.PhotographerData;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object to get and update user data. Represents the domain
 * object User.
 *
 * @author Eric Haller
 */
public class UserPasswordChange implements Serializable {



    @NotEmpty
    private String password;

    @NotEmpty
    @Size(min = 3, max = 60)
    private String newPassword;

    @NotEmpty
    private String passwordConfirm;

    private Long id;

    /**
     * Default constructor
     */
    public UserPasswordChange(){}

    /**
     * Returns user id.
     *
     * @return user id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
