/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

/**
 * Data transfer object to get and update user profile data.
 * Represents the domain object UserProfile and UserBank.
 *
 * @author Sergej Meister
 */
public class UserProfileData extends UserData {

    protected Long profileId;

    protected Long bankId;

    protected String firstname;

    protected String surname;

    protected String birthday;

    protected String address;

    protected String phone;

    protected String company;

    protected String homepage;

    protected Boolean showBankData;

    protected String receiver;

    protected String iban;

    protected String bic;

    /**
     * Returns profile id.
     * @return profile id.
     */
    public Long getProfileId() {
        return profileId;
    }

    /**
     * Sets profile id.
     * @param profileId profile id.
     */
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    /**
     * Returns bank id.
     * @return bank id.
     */
    public Long getBankId() {
        return bankId;
    }

    /**
     * Sets bank id.
     * @param bankId bank id.
     */
    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    /**
     * Returns first name.
     * @return first name.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets first name.
     * @param firstname first name.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Retruns user surname.
     * @return surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets user surname.
     * @param surname surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns birthday.
     * @return birthday.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     * @param birthday birthday.
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns address.
     * @return address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     * @param address address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns phone.
     *
     * @return phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone phone.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns company.
     *
     * @return company.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets company.
     *
     * @param company company.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Returns homepage.
     *
     * @return homepage.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Sets homepage.
     *
     * @param homepage homepage.
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * Show bank data
     * @return true, if role photograph.
     */
    public Boolean isShowBankData() {
        return showBankData;
    }

    /**
     * Sets value to show bank data.
     *
     * @param showBankData value.
     */
    public void setShowBankData(Boolean showBankData) {
        this.showBankData = showBankData;
    }

    /**
     * Returns receiver.
     *
     * @return receiver.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets receiver.
     *
     * @param receiver receiver data.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Returns bank data IBAN.
     *
     * @return IBAN.
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets bank data IBAN.
     *
     * @param iban IBAN.
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Returns bank data BIC.
     *
     * @return BIC.
     */
    public String getBic() {
        return bic;
    }

    /**
     * Sets bank data BIC.
     * @param bic BIC.
     */
    public void setBic(String bic) {
        this.bic = bic;
    }
}
