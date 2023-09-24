import java.io.Serializable;
import java.util.HashMap;

public class Flight implements Serializable {
    private String flightNumber;
    private String origin;
    private String destination;
    private String planeModel;
    private int capacity;
    private int businessCapacity; // is first n seats at the plane
    private int day, month, year;
    private HashMap<String, HashMap<String, Person>> people;

    Flight(String flightNumber, String origin, String destination, String planeModel, int capacity, int bussinessCapacity, int day, int month, int year) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.planeModel = planeModel;
        this.capacity = capacity;
        this.businessCapacity = bussinessCapacity;
        this.day = day;
        this.month = month;
        this.year = year;
        this.people = new HashMap<String, HashMap<String, Person>>();
        this.people.put("Business", new HashMap<String, Person>());
        this.people.put("Economy", new HashMap<String, Person>());
    }

    //buying ticket for flight
    public void addPassenger(Person person, String seat) {
        this.people.get(seat).put(person.getEmail(), person);
    }
    //canceling ticket for flight
    public void removePassenger(Person person) throws Exception {
        if (this.people.get("Business").containsKey(person.getEmail())) {
            this.people.get("Business").remove(person.getEmail());
        } else if (this.people.get("Economy").containsKey(person.getEmail())) {
            this.people.get("Economy").remove(person.getEmail());
        } else {
            throw new HasNoTicketException("The flight does not have this person!");
        }
    }

    /*
    //overriding equals and hashCode to decide if the person objects are equal or not by their email
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightNumber.equals(flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber);
    }
    */

    //getters
    public HashMap<String, HashMap<String, Person>> getPeople() {
        return people;
    }
    public String getFlightNumber() {
        return flightNumber;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getOrigin() {
        return origin;
    }
    public String getDestination() {
        return destination;
    }
    public String getPlaneModel() {
        return planeModel;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getBusinessCapacity() {
        return businessCapacity;
    }
}
