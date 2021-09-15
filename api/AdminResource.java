package api;

import model.Customer;
import model.IRoom;
import model.Room;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AdminResource {

    public static void addRoom(List<IRoom> rooms){
        for(IRoom room: rooms) {
            ReservationService.addRoom(room);
        }
    }

    public static Collection<Customer> getAllCustomers(){
        return CustomerService.getAllCustomers();
    }

    public static void displayAllReservations(){
        ReservationService.printAllReservation();
    }

    public static Set<Room> getAllRooms(){
        return ReservationService.getAllRooms();
    }
}
