//
// Copyright 2020- IBM Inc. All rights reserved
// SPDX-License-Identifier: Apache2.0
//
package org.service.addressbook.model;

import io.swagger.annotations.ApiModelProperty;

public class ModelPerson {

    @ApiModelProperty(notes = "The firstname of the person", example = "Donald")
    private String firstname;
    @ApiModelProperty(notes = "The lastname of the person")
    private String lastname;
    @ApiModelProperty(notes = "The telephone of the person")
    private String telephone;
    @ApiModelProperty(notes = "The city name of the person")
    private String city;
    @ApiModelProperty(notes = "The street name of the person")
    private String street;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}