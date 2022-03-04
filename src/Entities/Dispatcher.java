package Entities;

import Enums.*;
import Exceptions.FileErrorExeption;
import Exceptions.LoadException;

public class Dispatcher extends Employee {
    private int lineNumber;
    private Department department;

    public Dispatcher(String name, String surname, long jmbg, Sex sex, String address, String phoneNumber, String username, String password, Role role, double salary, int lineNumber, Department department) {
        super(name, surname, jmbg, sex, address, phoneNumber, username, password, role, salary);
        this.lineNumber = lineNumber;
        this.department = department;
    }

    public Dispatcher() {
        super();
        this.lineNumber = 0;
        this.department = null;
    }


    public void printToConsole() {
        System.out.println("Korisnicko ime: " + this.username + "\nIme i prezime: " + this.name + this.surname + "\nJMBG: " + this.jmbg + "\nPol: " + (this.sex == Sex.MALE ? "Muški" : "Ženski") + "\nAdresa: " + this.address + "\nBroj telefona: " + this.phoneNumber + "\n");
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String Serialize() {
        return this.isDeleted + "," + this.role.ordinal() + "," + this.username + "," + this.password + "," + this.name + "," + this.surname + "," + this.jmbg + "," + this.sex.ordinal() + "," + this.address + "," + this.phoneNumber + "," + this.salary + "," + this.department.ordinal() + "," + this.lineNumber + "\n";
    }

    @Override
    public Person Deserialize(String loadedLine)  {
       try {
           String[] entities = loadedLine.split(",");
           if (entities.length != 13) {
               throw new FileErrorExeption("Unable to  load Dispatcher at line: " + loadedLine);
           }

           this.isDeleted= Boolean.parseBoolean(entities[0]);
           this.name = entities[4];
           this.surname = entities[5];
           this.jmbg = Long.parseLong(entities[6]);
           this.sex = Sex.values()[Integer.parseInt(entities[7])];
           this.address = entities[8];
           this.phoneNumber = entities[9];
           this.username = entities[2];
           this.password = entities[3];
           this.role = Role.values()[Integer.parseInt(entities[1])];
           this.salary = Double.parseDouble(entities[10]);
           this.lineNumber = Integer.parseInt(entities[12]);
           this.department = Department.values()[Integer.parseInt(entities[11])];

           return this;
       }catch (FileErrorExeption e){
           throw e;
       }
    }
}