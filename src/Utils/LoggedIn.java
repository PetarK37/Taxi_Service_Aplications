package Utils;

import Entities.Person;
import Entities.TaxiService;

public class LoggedIn {
    private static Person LoggedUser;
    private static TaxiService  service;

    public static Person getLoggedUser() {
        return LoggedUser;
    }

    public static void setLoggedUser(Person loggedUser) {
        LoggedUser = loggedUser;
    }

    public static TaxiService getService() {
        return service;
    }

    public static void setService(TaxiService service) {
        LoggedIn.service = service;
    }
}
