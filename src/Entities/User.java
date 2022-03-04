package Entities;

import Enums.Role;
import Enums.Sex;

import Exceptions.FileErrorExeption;
import Exceptions.LoadException;
import List.*;

public class User extends Person {
    private LinkedList<Ride> userRides;

    public User(String name, String surname, long jmbg, Sex sex, String address, String phoneNumber, String username, String password, Role role, LinkedList<Ride> userRides) {
        super(name, surname, jmbg, sex, address, phoneNumber, username, password, role);
        this.userRides = userRides;
    }

    public User() {
        super();
        this.userRides = null;
    }


    @Override
    public String Serialize() {
        return this.isDeleted+ "," + this.role.ordinal() + "," + this.username + "," + this.password + "," + this.name + "," + this.surname + "," + this.jmbg + "," + this.sex.ordinal() + "," + this.address + "," + this.phoneNumber +"\n";
    }

    @Override
    public Person Deserialize(String loadedLine) {
        try {

            String[] entities = loadedLine.split(",");

            if (entities.length != 10) {
                throw new FileErrorExeption("Unable to  load User at line: " + loadedLine);

            }

            this.isDeleted = Boolean.parseBoolean(entities[0]);
            this.name = entities[4];
            this.surname = entities[5];
            this.jmbg = Long.parseLong(entities[6]);
            this.sex = Sex.values()[Integer.parseInt(entities[7])];
            this.address = entities[8];
            this.phoneNumber = entities[9];
            this.username = entities[2];
            this.password = entities[3];
            this.role = Role.values()[Integer.parseInt(entities[1])];
            this.userRides = new LinkedList<Ride>();

            return this;
        }catch (FileErrorExeption e){
            throw e;
        }
    }


    public void printToConsole() {
        System.out.println("Korisnicko ime: " + this.username + "\nIme i prezime: " + this.name + this.surname + "\nJMBG: " + this.jmbg + "\nPol: " + (this.sex == Sex.MALE ? "Muški" : "Ženski") + "\nAdresa: " + this.address + "\nBroj telefona: " + this.phoneNumber + "\n");
    }




    public LinkedList<Ride> getUserRides() {
        return userRides;
    }

    public void setUserRides(LinkedList<Ride> userRides) {
        this.userRides = userRides;
    }

    public void addUserRides(Ride ride){this.userRides.add(ride);}
}