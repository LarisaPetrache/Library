package com.example.library.dto;

import com.example.library.models.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Book's author request", description = "Required details needed to create a new Author")
public class AuthorRequest {

    @NotBlank
    @ApiModelProperty(value = "firstName", required = true,
    notes = "Author's first name", example = "Eula", position = 1)
    private String firstName;
    @NotBlank
    @ApiModelProperty(value = "lastName", required = true,
            notes = "Author's last name", example = "Lawrence", position = 2)
    private String lastName;
    @NotBlank
    @ApiModelProperty(value = "nationality", required = true,
            notes = "Author's nationality", example = "american", position = 3)
    private String nationality;
    @NotNull
    @ApiModelProperty(value = "birthYear", required = true,
            notes = "Author's birth year", example = "1986", position = 4)
    private int birthYear;

    public AuthorRequest(){}

    public AuthorRequest(String firstName, String lastName, String nationality, int birthYear){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthYear = birthYear;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
