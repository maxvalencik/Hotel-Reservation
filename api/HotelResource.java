package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    public static Customer getCustomer (String email){
        return CustomerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName){
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public static void bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        ReservationService.reserveRoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail){
        return ReservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return ReservationService.findRooms(checkIn, checkOut);
    }
}
