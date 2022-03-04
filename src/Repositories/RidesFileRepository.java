package Repositories;


import Entities.*;
import Entities.Ride;
import Enums.ReservationStatus;
import Exceptions.*;
import List.LinkedList;
import Utils.Constants;
import java.io.*;

public class RidesFileRepository {

    private static File file = new File(Constants.RIDES_PATH);

    public static void writeAllRides(LinkedList<Ride> rides)  {
      try {
          BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(file)));
          for (Ride r : rides) {
              writer.write(r.Serialize());
          }
          writer.close();
      }catch (IOException e){
          throw new SaveExcption(e);
      }
    }

    public static LinkedList<Ride> loadAllRides() {
        try {
            LinkedList <Ride> rides = new LinkedList<>();
            if (file.exists()){
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line;
                while((line = input.readLine()) != null) {
                    String[] entities = line.split(",");
                    if (entities.length == 13){
                        rides.add(new AppReservation().Deserialize(line));
                    }else {
                        rides.add(new PhoneReservation().Deserialize(line));
                    }
                }
            }else {
                throw new FileNotFoundException("File doesnt exist");
            }
            return rides;
        }catch (FileNotFoundException e) {
            throw new LoadException(e);
        }catch (IOException e){
            throw new LoadException(e);
        }
    }

    public static void DeleteRide(LinkedList<Ride> rides, Ride reservation) throws DeleteException{
           for (Ride r: rides) {
               if (r.equals(reservation)) {
                   if (r.getStatus() == ReservationStatus.REJECTED || r.getStatus() == ReservationStatus.FINISHED) {
                       reservation.setDeleted(true);
                   }
               }
           }
           throw new DeleteException("Cannot delete selected element");
    }

}


