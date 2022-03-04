package Entities;

import Enums.*;

public abstract class Employee extends Person  {
    protected double salary;

    public Employee(String name, String surname, long jmbg, Sex sex, String address, String phoneNumber, String username, String password, Role role, double salary) {
        super(name, surname, jmbg, sex, address, phoneNumber, username, password, role);
        this.salary = salary;
    }

    public Employee() {
        super();
        this.salary = 0;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


}