package Repositories;

import Entities.*;
import Exceptions.LoadException;
import Exceptions.SaveExcption;
import List.LinkedList;
import Utils.Constants;

import java.io.*;

public class TaxiServiceFileRepository {

    private static File file = new File(Constants.SERVICE_PATH);

    public static void saveService(TaxiService service) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(file)));
            writer.write(service.Serialize());
            writer.close();
        }catch (IOException e){
            throw new SaveExcption(e);
        }
    }

    public static TaxiService loadService() throws LoadException {
        try {
            TaxiService service = new TaxiService();
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line;
                while((line = input.readLine()) != null) {
                    service.Deserialize(line);
                }
            } else {
                throw new FileNotFoundException("File doesnt exist");
            }
            return service;
        } catch (FileNotFoundException e) {
            throw new LoadException(e);
        }catch (IOException e){
            throw new SaveExcption(e);
        }
    }

}
