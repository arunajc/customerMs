package com.mybank.customer.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.util.Objects;

public class CustomerDetails {

    @NotBlank(message = "userName is mandatory")
    @Pattern(regexp = "^[aA-zZ]\\w{5,29}$",
            message = "userName format validation failed")
    private String userName;

    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$",
            message = "password format validation failed")
    private String password;

    @Pattern(regexp = "^[\\p{L} .'-]+$",
            message = "firstName format validation failed")
    private String firstName;

    @Pattern(regexp = "^[\\p{L} .'-]+$",
            message = "lastName format validation failed")
    private String lastName;

    @Email(message = "emailId format validation failed")
    private String emailId;

    @Pattern(regexp = "\\d{10}",
            message = "mobileNumber format validation failed")
    private String mobileNumber;

    private int enabled;
    private String insertedBy;
    private OffsetDateTime insertedDate;
    private String updatedBy;
    private OffsetDateTime updatedDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public OffsetDateTime getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(OffsetDateTime insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OffsetDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(OffsetDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDetails that = (CustomerDetails) o;
        return mobileNumber == that.mobileNumber &&
                enabled == that.enabled &&
                userName.equals(that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(emailId, that.emailId) &&
                Objects.equals(insertedBy, that.insertedBy) &&
                Objects.equals(insertedDate, that.insertedDate) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, firstName, lastName, emailId, mobileNumber, enabled, insertedBy, insertedDate, updatedBy, updatedDate);
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "userName='" + userName + '\'' +
                ", password='" + password.replaceAll(".", "*") + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", enabled=" + enabled +
                ", insertedBy='" + insertedBy + '\'' +
                ", insertedDate=" + insertedDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
