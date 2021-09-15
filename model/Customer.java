package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email = "^(.+)@(.+).(.+)$";

    public Customer(String firstName, String lastName, String email){
        Pattern pattern = Pattern.compile(email);
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error..invalid email");
        }
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    //getter
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }

    //setter
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString(){
        return "Customer: " + firstName +" "+ lastName +" "+ email;
    }
}
