package de.htw.sdf.photoplatform.webservice.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Data transfer object to change user password.
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

    /**
     * Default constructor
     */
    public UserPasswordChange(){}

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmed new password
     */
    public String getPasswordConfirm() { return passwordConfirm; }

    /**
     * @param passwordConfirm the password to confirm
     */
    public void setPasswordConfirm(String passwordConfirm) {this.passwordConfirm = passwordConfirm;}

    /**
     * @return the new password
     */
    public String getNewPassword() {return newPassword;}

    /**
     * @param newPassword the new password
     */
    public void setNewPassword(String newPassword) { this.newPassword = newPassword;}
}
