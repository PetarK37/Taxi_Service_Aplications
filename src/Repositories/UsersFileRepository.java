package Repositories;

import Entities.*;
import Enums.ReservationStatus;
import Exceptions.*;
import Utils.Constants;

import java.io.*;
import List.*;

public class UsersFileRepository {

    private static File file = new File(Constants.USERS_PATH);

    public static void writeAllUsers(List.LinkedList<Person> users)   {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(file)));
            for (Person p : users) {
                writer.write(p.Serialize());
            }
            writer.close();
        } catch (IOException e) {
            throw new SaveExcption(e);
        }

    }

    public static LinkedList<Person> loadAllUsers()  {
        try {
            LinkedList <Person> users = new LinkedList<>();
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line;

                while((line = input.readLine()) != null) {
                    String[] entities = line.split(",");
                    if (entities[1].equals("0")) {
                        users.add(new User().Deserialize(line));
                    } else if (entities[1].equals("1")) {
                        users.add(new Driver().Deserialize(line));
                    } else if (entities[1].equals("2")) {
                        users.add(new Dispatcher().Deserialize(line));
                    }
                }

            } else {
                throw new FileNotFoundException("File doesnt exist");
            }
            return users;
        } catch (FileNotFoundException e) {
            throw new LoadException(e);
        }catch (IOException e){
            throw new LoadException(e);
        }
    }


    public static void DeleteDriver(LinkedList<Person> users, Driver driver) throws DeleteException{
        for (Person d: users){
            if (d.equals(driver)) {
                if (driverIsFree(driver)) {
                    d.setDeleted(true);
                    ((Driver) d).getVehicle().setDriver(null);
                    return;
                }
            }
        }
        throw new DeleteException("Can not delete selected element");
    }

    public static boolean driverIsFree(Driver driver)  {
        if (driver != null){
            for (Ride r : driver.getDriverRides()) {
                if (r.getStatus() != ReservationStatus.FINISHED && r.getStatus() != ReservationStatus.REJECTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void deleteUser(LinkedList<Person> users, Person user){
        for (Person p: users){
            if (p.equals(user)) {
                p.setDeleted(true);
            }
        }
    }


}
