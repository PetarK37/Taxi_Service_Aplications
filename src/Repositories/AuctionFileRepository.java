package Repositories;

import Entities.AppReservation;
import Entities.PhoneReservation;
import Entities.Ride;
import Auction.*;
import Exceptions.LoadException;
import Exceptions.SaveExcption;
import List.LinkedList;
import Utils.Constants;

import java.io.*;

public class AuctionFileRepository {

    private static File auctionsFile = new File(Constants.AUCTIONS_PATH);
    private static File offersFile = new File(Constants.OFFERS_PATH);

    public static void writeAllAuctions(LinkedList<Auction> auctions)  {
        try {
            BufferedWriter writer  = new BufferedWriter(new FileWriter(String.valueOf(auctionsFile)));
            for (Auction a : auctions) {
                writer.write(a.Serialize());
            }
            writer.close();
        } catch (IOException e) {
            throw new SaveExcption(e);
        }
    }

    public static void writeAlloffers(LinkedList<Offer> offers) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(offersFile)));
        for (Offer o : offers) {
            writer.write(o.Serialize());
        }
        writer.close();
    }

    public static void writeNewOffer(Offer offer)  {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(offersFile),true));
            writer.write(offer.Serialize());
            writer.close();
        } catch (IOException e) {
            throw new SaveExcption(e);
        }
    }

    public static LinkedList<Auction> loadAllAuctions() {
        try {
            LinkedList<Auction> auctions = new LinkedList<>();
            if (auctionsFile.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(auctionsFile));
                String line;
                while ((line = input.readLine()) != null) {
                    auctions.add(new Auction().Deserialize(line));
                }
            } else {
                throw new FileNotFoundException("File doesnt exist");
            }
            return auctions;
        } catch (FileNotFoundException e) {
            throw new LoadException(e);
        } catch (IOException e) {
            throw new LoadException(e);
        }
    }

    public static LinkedList<Offer> loadAllOffers() {
        try {
            LinkedList<Offer> offers = new LinkedList<>();
            if (offersFile.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(offersFile));
                String line;
                while ((line = input.readLine()) != null) {
                    offers.add(new Offer().Deserialize(line));
                }
            } else {
                throw new FileNotFoundException("File doesnt exist");
            }
            return offers;
        } catch (FileNotFoundException e) {
            throw new LoadException(e);
        } catch (IOException e) {
            throw new LoadException(e);
        }
    }
}
