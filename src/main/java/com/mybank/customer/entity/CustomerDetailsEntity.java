package com.mybank.customer.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name="MYBANK_CUSTOMERS")
public class CustomerDetailsEntity {

    @Id
    @Column(name="USERNAME")
    private String userName;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="FIRSTNAME")
    private String firstName;

    @Column(name="LASTNAME")
    private String lastName;

    @Column(name="EMAILID")
    private String emailId;

    @Column(name="MOBILENUMBER")
    private String mobileNumber;

    @Column(name="ENABLED")
    private int enabled;

    @Column(name="INSERTEDBY", updatable = false)
    private String insertedBy;

    @CreationTimestamp
    @Column(name="INSERTEDDATE", updatable = false)
    private OffsetDateTime insertedDate;

    @Column(name="UPDATEDBY")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name="UPDATEDDATE")
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
        CustomerDetailsEntity that = (CustomerDetailsEntity) o;
        return mobileNumber == that.mobileNumber &&
                enabled == that.enabled &&
                Objects.equals(userName, that.userName) &&
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
        return "CustomerDetailsEntity{" +
                "userName='" + userName + '\'' +
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
