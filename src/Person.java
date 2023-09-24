import java.io.Serializable;
import java.util.HashMap;

public class Person implements Serializable {
    private String name;
    private int age;
    private String email;
    private HashMap<String, Flight> flights;

    Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.flights = new HashMap<String, Flight>();
    }

    //buying  ticket for flight
    public void addTicket(Flight flight, String seat) throws Exception {
            if (flight.getPeople().get("Business").size() >= flight.getBusinessCapacity() || flight.getPeople().get("Economy").size() >= flight.getCapacity()-flight.getBusinessCapacity()) {
                throw new SeatNotAvailableException("This type of seat is not available!");
            } else if (this.flights.containsKey(flight.getFlightNumber())) {
                throw new AlreadyHasTicketException("This person already has a ticket for this flight!");
            } else {
                this.flights.put(flight.getFlightNumber(), flight);
                flight.addPassenger(this, seat);
            }
    }

    //canceling ticket for flight
    public void removeTicket(Flight flight) throws Exception {
            if (this.flights.containsKey(flight.getFlightNumber())) {
                this.flights.remove(flight.getFlightNumber());
                flight.removePassenger(this);
            } else {
                throw new HasNoTicketException("User does not have this flight!");
            }
    }

    /*//overriding equals and hashCode to decide if the person objects are equal or not by their email
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return email.equals(person.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    */

    //getters
    public HashMap<String, Flight> getFlights() {
        return flights;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
}
