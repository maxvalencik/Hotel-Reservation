package model;

import java.util.Objects;

public class Room implements IRoom{

    public String roomNumber;
    private Double price;
    public RoomType enumeration;

    //Constructor
    public Room(String roomNumber, Double price, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    //implement Interface IRoom
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return  enumeration + " room " + roomNumber + " is $" + price;
    }

    //override equal and HashCode to avoid duplicate rooms
    @Override
    public boolean equals(final Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Room other = (Room) obj;
        if (roomNumber == null) {
            if (other.roomNumber != null)
                return false;
        } else if (!roomNumber.equals(other.roomNumber))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
