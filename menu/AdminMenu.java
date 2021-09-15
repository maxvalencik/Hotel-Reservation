package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void adminMenu(){

        System.out.println("ADMIN MENU");
        System.out.println("1- See all Customers");
        System.out.println("2- See all Rooms");
        System.out.println("3- See all Reservations");
        System.out.println("4- Add a Room");
        System.out.println("5- Back to Main Menu");
    }

    public static void adminMenuAction() throws ParseException {
        MenuChoice userChoice = MainMenu.Choice();

        if (userChoice==null) {
            System.out.println("Enter a valid choice!");
            userChoice = MainMenu.Choice();
        }

        while(userChoice!=null) {

            //See all customers
            if (userChoice == MenuChoice.ONE) {
                if (AdminResource.getAllCustomers().isEmpty()){
                    System.out.println("No customer yet!");
                }
                else {
                    for (Customer customer : AdminResource.getAllCustomers()) {
                        System.out.println(customer);
                    }
                }
                userChoice = MainMenu.Choice();
            }

            //See all rooms
            else if (userChoice == MenuChoice.TWO) {
                if (AdminResource.getAllRooms().isEmpty()){
                    System.out.println("No room yet!");
                }
                else {
                    for (Room room : AdminResource.getAllRooms()) {
                        System.out.println(room);
                    }
                }
                userChoice = MainMenu.Choice();
            }

            //See all reservations
            else if (userChoice == MenuChoice.THREE) {
                AdminResource.displayAllReservations();
                userChoice = MainMenu.Choice();
            }

            //add a room
            else if (userChoice == MenuChoice.FOUR) {
                List<IRoom> roomList = new ArrayList<>();
                RoomType roomType=null;
                Scanner sc;
                Double roomPrice=null;
                boolean priceType=false;
                boolean type=false;

                System.out.println("Room Number: ");
                sc = new Scanner(System.in);

                while (sc==null){
                    System.out.println("Enter a room number:");
                    sc = new Scanner(System.in);
                }
                String roomNumber=sc.nextLine();

                System.out.println("Room Price: ");
                sc = new Scanner(System.in);
                while(!priceType) {
                    try {
                        roomPrice = Double.parseDouble(sc.nextLine());
                        priceType=true;
                    } catch (IllegalArgumentException e){
                        System.out.println("Enter a valid number!");
                        priceType=false;
                        System.out.println("Room Price: ");
                        sc = new Scanner(System.in);
                    }
                }
                System.out.println("Room Type (SINGLE or DOUBLE): ");
                sc = new Scanner(System.in);
                while(!type) {
                    try {
                        roomType = RoomType.valueOf(sc.nextLine());
                        type=true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Wrong Room Type!");
                        type=false;
                        System.out.println("Room Type (SINGLE or DOUBLE): ");
                        sc = new Scanner(System.in);
                    }
                }

                Room room = new Room(roomNumber, roomPrice, roomType);
                roomList.add(room);

                AdminResource.addRoom(roomList);
                userChoice = MainMenu.Choice();
            }

            //back to main menu
            else if (userChoice == MenuChoice.FIVE) {
                MainMenu.mainMenu();
                MainMenu.mainMenuAction();

            } else {
                System.out.println("Enter a valid choice!");
                userChoice = MainMenu.Choice();
            }
        }
    }
}
