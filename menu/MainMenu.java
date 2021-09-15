package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;
import service.CustomerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static void mainMenu(){
        System.out.println("MAIN MENU");
        System.out.println("1- Find and Reserve a Room");
        System.out.println("2- See my Reservations");
        System.out.println("3- Create an Account");
        System.out.println("4- Admin Menu");
        System.out.println("5- Exit");
    }

    public static MenuChoice Choice(){

        System.out.println("Pick a menu option:");
        Scanner sc = new Scanner(System.in);
        String userChoice=sc.nextLine();
        MenuChoice menuChoice = null;

        switch(userChoice){
            case "1": menuChoice=MenuChoice.ONE;
            break;
            case "2": menuChoice=MenuChoice.TWO;
            break;
            case "3": menuChoice=MenuChoice.THREE;
            break;
            case "4": menuChoice=MenuChoice.FOUR;
            break;
            case "5": menuChoice=MenuChoice.FIVE;
            break;
        }
        return menuChoice;
    }

    public static void mainMenuAction() throws ParseException {
        Scanner sc;
        MenuChoice userChoice=Choice();

        if (userChoice==null){
            System.out.println("Enter a valid choice!");
            userChoice=Choice();
        }
        while(userChoice!=null) {

            //Find and Reserve a room
            if (userChoice == MenuChoice.ONE) {
                //Dates
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                System.out.println("Check In (mm/dd/yyyy): ");
                sc = new Scanner(System.in);
                Date dateIn=null;
                Date dateOut=null;
                boolean dateFormat=false;
                while(!dateFormat) {
                    try {
                        dateIn = formatter.parse(sc.nextLine());
                        dateFormat=true;
                    } catch (ParseException ex) {
                        System.out.println("Wrong date format!");
                        dateFormat=false;
                        System.out.println("Check In (mm/dd/yyyy): ");
                        sc = new Scanner(System.in);

                    }
                }

                System.out.println("Check Out (mm/dd/yyyy): ");
                sc = new Scanner(System.in);
                dateFormat=false;
                while(!dateFormat) {
                    try {
                        dateOut = formatter.parse(sc.nextLine());
                        dateFormat = true;

                    } catch (ParseException ex) {
                        System.out.println("Wrong date format!");
                        dateFormat = false;
                        System.out.println("Check Out (mm/dd/yyyy): ");
                        sc = new Scanner(System.in);
                    }
                }

                //find room
                Collection<IRoom> availableRooms = HotelResource.findARoom(dateIn, dateOut);

                if(availableRooms.isEmpty()){
                    System.out.println("No room available...check recommended dates");
                    //add 7 days
                    long inTime = dateIn.getTime() + 7 * 24 * 60 * 60 * 1000;
                    dateIn = new Date(inTime);
                    long outTime = dateOut.getTime() + 7 * 24 * 60 * 60 * 1000;
                    dateOut = new Date(outTime);
                    availableRooms = HotelResource.findARoom(dateIn, dateOut);
                }
                if(availableRooms.isEmpty()) {
                    System.out.println("No room available..");
                }
                else{
                    System.out.println("Rooms available: ");
                    System.out.println(availableRooms);
                    System.out.println("Check In date: "+dateIn);
                    System.out.println("Check Out date: "+dateOut);
                    //Proceed with reservation
                    //pick the room
                    System.out.println("Pick a room number: ");
                    sc = new Scanner(System.in);
                    while (sc == null) {
                        System.out.println("Enter a room number!");
                        System.out.println("Pick a room number: ");
                        sc = new Scanner(System.in);
                    }
                    String roomNumber = sc.nextLine();
                    IRoom reservedRoom = CustomerService.getRoom(roomNumber);

                    // ask for Customer email
                    System.out.println("Customer Email: ");
                    sc = new Scanner(System.in);
                    boolean emailFormat = false;

                    //Book room
                    while (!emailFormat) {
                        try {
                            String email = sc.nextLine();
                            HotelResource.bookARoom(email, reservedRoom, dateIn, dateOut);
                            emailFormat = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Wrong email format!");
                            emailFormat = false;
                            System.out.println("Enter email: ");
                            sc = new Scanner(System.in);
                        }
                    }
                }
                userChoice=Choice();
            }

            //Lookup for reservations
            else if (userChoice == MenuChoice.TWO) {
                boolean emailFormat=false;
                Collection<Reservation> reservations=new ArrayList<>();
                //Lookup for customer reservation with email
                System.out.println("Enter email: ");
                sc = new Scanner(System.in);

                while (!emailFormat) {
                    try {
                        String email = sc.nextLine();
                        reservations = HotelResource.getCustomersReservations(email);
                        emailFormat=true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Wrong email format!");
                        emailFormat=false;
                        System.out.println("Enter email: ");
                        sc = new Scanner(System.in);
                    }
                }

                if (!reservations.isEmpty()) {
                    for (Reservation resa : reservations) {
                        System.out.println(resa);
                    }
                }
                else{
                    System.out.println("No reservation for this customer!");
                }
                userChoice=Choice();
            }

            //Create new customer account
            else if (userChoice == MenuChoice.THREE) {
                boolean emailFormat=false;

                System.out.println("Enter email: ");
                sc = new Scanner(System.in);
                String email = sc.nextLine();
                System.out.println("First Name: ");
                sc = new Scanner(System.in);
                while(sc==null){
                    System.out.println("Enter a first name!");
                    System.out.println("First Name: ");
                    sc = new Scanner(System.in);
                }
                String firstName = sc.nextLine();

                System.out.println("Last Name: ");
                sc = new Scanner(System.in);
                while(sc==null){
                    System.out.println("Enter a last name!");
                    System.out.println("Last Name: ");
                    sc = new Scanner(System.in);
                }
                String lastName = sc.nextLine();

                //create new customer
                while(!emailFormat) {
                   try {
                       HotelResource.createACustomer(email, firstName, lastName);
                       emailFormat=true;
                   } catch(IllegalArgumentException e){
                       System.out.println("Wrong email format...j@domain.com");
                       emailFormat=false;
                       System.out.println("Enter email: ");
                       sc = new Scanner(System.in);
                       email = sc.nextLine();
                    }
                }
                userChoice=Choice();
            }

            //open admin menu
            else if (userChoice == MenuChoice.FOUR) {
                AdminMenu.adminMenu();
                AdminMenu.adminMenuAction();
            }

            //Exit program
            else if (userChoice == MenuChoice.FIVE) {
                System.exit(0);

            } else {
                System.out.println("Enter a valid choice!");
                userChoice=Choice();
            }
        }

    }
}

