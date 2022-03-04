package Repositories;

import Entities.Driver;
import Entities.Person;
import Entities.Vehicle;
import Exceptions.DeleteException;
import Exceptions.LoadException;
import Exceptions.SaveExcption;
import List.LinkedList;
import Utils.Constants;

import java.io.*;

public class VehiclesFileRepository {

    private static File file = new File(Constants.VEHICLES_PATH);

    public static LinkedList<Vehicle> loadAllVehicles() {
        try{
            LinkedList<Vehicle> vehicles = new LinkedList<>();
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line;
                while((line = input.readLine()) != null) {
                    vehicles.add(new Vehicle().Deserialize(line));
                }
            } else {
                throw new FileNotFoundException("File doesnt exist");
            }
            return vehicles;
        }catch (FileNotFoundException e){
            throw new LoadException(e);
        }catch (IOException e){
            throw new LoadException(e);
        }
    }

    public static void writeAllVehicles(LinkedList<Vehicle> vehicles)  {
        try {
            BufferedWriter  writer = new BufferedWriter(new FileWriter(String.valueOf(file)));
            for (Vehicle v : vehicles) {
                writer.write(v.Serialize());
            }
            writer.close();
        } catch (IOException e) {
            throw new SaveExcption(e);
        }

    }


    public static void deleteVehicle(LinkedList<Vehicle> vehicles, Vehicle vehicle) throws DeleteException {
        for (Vehicle v : vehicles){
            if (v.equals(vehicle)){
                if (v.getDriver() == null){
                    v.setDeleted(true);
                    return;
                }
            }
        }
           throw new DeleteException("Can not delete selected element");
    }

}


