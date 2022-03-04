package Controllers;

import Entities.TaxiService;

public class ServiceController {

    public static void UpdateService(TaxiService target, String name, String adress, int priceKm, int startingPrice){
        target.setName(name);
        target.setAddress(adress);
        target.setPriceForKm(priceKm);
        target.setStartingPrice(startingPrice);
    }
}
