package Controllers;

import Auction.*;
import Entities.AppReservation;
import Entities.Driver;
import Entities.Ride;
import List.LinkedList;
import Utils.Constants;

public class AuctionController {

    public static void CreateNewAuction(LinkedList<Auction> auctions, Auction auction){
        auctions.add(auction);
    }

    public static void addOffersToAuction(LinkedList<Offer> offers, LinkedList<Auction> auctions){
        for (Offer o: offers){
            for (Auction a:auctions)
            if (o.getAuction().equals(a)){
                a.getOffers().add(o);
            }
        }
    }


    public static LinkedList<Offer> getSortedOffers(Auction auction){
        LinkedList<Offer> retList = auction.getOffers();

        for (int i = 0; i < auction.getOffers().size(); i++) {

            for (int j = auction.getOffers().size() - 1; j > i; j--) {

                if (auction.getOffers().get(i).getPoints() < auction.getOffers().get(j).getPoints()) {

                    Offer tmp = auction.getOffers().get(i);
                    retList.set(i,auction.getOffers().get(j)) ;
                    retList.set(j,tmp);

                }if (auction.getOffers().get(i).getPoints() == auction.getOffers().get(j).getPoints()){
                    retList.set(i+1,auction.getOffers().get(j));
                }

            }

        }
        return retList;
    }

    public static void finishAuction(Auction auction, Driver driver){
        auction.setWinningDriver(driver);
    }

    public static int generateId(LinkedList<Auction> auctions){
        return auctions.size();
    }

}
