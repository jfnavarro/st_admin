package com.st.model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

/**
 * This bean class maps the Account data retrieved from the ST API to the
 * application data model. This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
 */
public class Account implements IAccount {

    String id;

    @NotBlank
    @Email(message = "Username must be a valid email address.")
    String username;

    @Length(min = 4, message = "Password must have at least 4 characters.")
    String password;

    @JsonIgnore
    String passwordRepeat;

    @NotBlank
    String role;

    boolean enabled;

    String institution;

    String first_name;

    String last_name;

    String street_address;

    String city;

    String postcode;

    String country;

    List<String> granted_datasets;

    DateTime created_at;

    DateTime last_modified;

    /**
     * Default Constructor is required by Jackson.
     */
    public Account() {
    }

    // id is set automatically by MongoDB
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    @JsonIgnore
    public void setPasswordRepeat(String password) {
        this.passwordRepeat = password;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getInstitution() {
        return institution;
    }

    @Override
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String getFirst_name() {
        return first_name;
    }

    @Override
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String getLast_name() {
        return last_name;
    }

    @Override
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String getStreet_address() {
        return street_address;
    }

    @Override
    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getPostcode() {
        return postcode;
    }

    @Override
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public List<String> getGranted_datasets() {
        return this.granted_datasets;
    }

    @Override
    public void setGranted_datasets(List<String> grantedDatasets) {
        this.granted_datasets = grantedDatasets;
    }

    @Override
    public DateTime getCreated_at() {
        return created_at;
    }

    @Override
    public void setCreated_at(DateTime created) {
        this.created_at = created;
    }

    @Override
    public DateTime getLast_modified() {
        return last_modified;
    }
}
