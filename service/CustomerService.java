package service;

import model.Customer;
import model.IRoom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;


public class CustomerService {
    public static Customer customer;
    //customer collection with no duplicate (set)
    private static final Set<Customer> customerCollection = new HashSet<>();
    //regex email verification (j@domain.com)
    private static final String email = "^(.+)@(.+).(.+)$";
    private static final Pattern pattern = Pattern.compile(email);


    public static IRoom getRoom(String roomID){return ReservationService.getARoom(roomID);}

    public static void addCustomer (String email, String firstName, String lastName){
       customer = new Customer(firstName, lastName, email);
       customerCollection.add(customer);
    }

    public static Customer getCustomer (String customerEmail){
        boolean existingClient = false;
        if (!pattern.matcher(customerEmail).matches()){
            throw new IllegalArgumentException("Error...invalid email");
        }
        for(Customer client: customerCollection) {
            if (Objects.equals(client.getEmail(), customerEmail)) {
                existingClient=true;
                customer=client;
                break;
            }
        }
        if (existingClient) {
            return customer;
        }else {
            System.out.println("Not a customer!");
            return customer;
        }
    }

    public static Set<Customer> getAllCustomers(){
        return customerCollection;
    }
}
