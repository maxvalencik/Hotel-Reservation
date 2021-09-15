package service;

import api.HotelResource;
import model.*;

import java.util.*;

public class ReservationService {
    private static Room newRoom;
    //Set for no duplicates
    private static final Set<Room> roomCollection = new HashSet<>();
    //Duplicates allowed in this collection
    private static final Collection<Reservation> reservationCollection = new ArrayList<>();

    public static void addRoom(IRoom room) {
        if (room.getRoomPrice()==0.0){
            newRoom=new FreeRoom(room.getRoomNumber(), room.getRoomType());
        }else {
            newRoom = new Room(room.getRoomNumber(), room.getRoomPrice(), room.getRoomType());
        }
        roomCollection.add(newRoom);
    }

      static IRoom getARoom(String roomID) {
        boolean existingRoom = false;
        for (Room room : roomCollection) {
            if (Objects.equals(room.roomNumber, roomID)) {
                newRoom = room;
                existingRoom = true;
            }
        }
        if (!existingRoom) {
            System.out.println("Room does not exist!");
        }
        return newRoom;
    }

    public static void reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationCollection.add(newReservation);
    }

    public static Set<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        int numberDays=0;
        boolean availability=false;
        IRoom reservedRoom=null;
        Set<IRoom> availableRoom = new HashSet<>();
        if (reservationCollection.isEmpty()){
            availableRoom.addAll(roomCollection);
        }
        else {
            for (IRoom room : roomCollection) {
                availability = true;
                for (Reservation reservation : reservationCollection) {
                    Date dateOut = reservation.getCheckOutDate();
                    Date dateIn = reservation.getCheckInDate();
                    reservedRoom= reservation.getRoom();
                    if (room.equals(reservedRoom)) {
                        if (!checkInDate.after(dateOut) && !checkOutDate.before(dateIn)) {
                            availability = false;
                        }
                    }
                }
                if (availability){availableRoom.add(room);}
            }
        }
        return availableRoom;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservation = new ArrayList<>();
        for (Reservation existingReservation : reservationCollection) {
            if (Objects.equals(existingReservation.getCustomer().getEmail(), customer.getEmail())) {
                customerReservation.add(existingReservation);
            }
        }
        return customerReservation;
    }

    public static void printAllReservation() {
        if(reservationCollection.isEmpty()){
            System.out.println("No reservation yet!");
        }
        else {
            for (Reservation reservation : reservationCollection) {
                System.out.println(reservation);
            }
        }
    }

    public static Set<Room> getAllRooms(){
        return roomCollection;
    }

}
