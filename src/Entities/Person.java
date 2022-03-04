package Entities;

import Enums.*;
import Interfaces.SerializationAndDeserialization;

public abstract class Person implements SerializationAndDeserialization<Person>{
    protected String name;
    protected String surname;
    protected long jmbg;
    protected Sex sex;
    protected String address;
    protected String phoneNumber;
    protected String username;
    protected String password;
    protected Role role;
    protected boolean isDeleted;


    public Person(String name, String surname, long jmbg, Sex sex, String address, String phoneNumber, String username, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.jmbg = jmbg;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isDeleted = false;
    }

    public Person() {
        this.name = "";
        this.surname = "";
        this.jmbg = 0;
        this.sex = null;
        this.address = "";
        this.phoneNumber = "";
        this.username = "";
        this.password = "";
        this.role = null;
        this.isDeleted = false;
    }

    public void printToConsole(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getJmbg() {
        return jmbg;
    }

    public void setJmbg(long jmbg) {
        this.jmbg = jmbg;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDeleted() {return isDeleted;}

    public void setDeleted(boolean deleted) {isDeleted = deleted;}


}