import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;

public class SystemManager implements Serializable {
    private HashMap<String, Person> people;
    private HashMap<String, Flight> flights;

    SystemManager() {
        people = new HashMap<String, Person>();
        flights = new HashMap<String, Flight>();
    }

    public void createPerson(String name, int age, String email) throws Exception {
        try {
            if (age < 0) throw new AgeLessThanZeroException("Age is less than zero");
            if (age>99) throw new AgeMoreThan99Exception("Age more than 99");
            Person person = new Person(name, age, email);
            if (people.containsKey(person.getEmail())) {
                throw new EmailNotAvailableException("Person with this email already exist!");
            } else {
                people.put(person.getEmail(), person);
                System.out.println("Person successfully created!");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void createFlight(String flightNumber, String origin, String destination, String planeModel, int capacity, int businessCapacity, int dd, int mm, int yy) throws DateTimeException, Exception {
        try {
            LocalDate ld = LocalDate.of(yy, mm, dd);
            if (businessCapacity > capacity) throw new BusinessCapacityMoreThanCapacityExeption("Total capacity is less than business capacity!");
            if (flights.containsKey(flightNumber)) throw new FlightNumberAlreadyExistException("Flight with this flight number already exist!");
            Flight flight = new Flight(flightNumber, origin, destination, planeModel, capacity, businessCapacity, dd, mm, yy);
            flights.put(flight.getFlightNumber(), flight);
            System.out.println("Flight successfully created!");
        } catch (Exception e) {
            throw e;
        }
    }

    public void buyTicket(String email, String flightNum, String seat) throws Exception {
        try {
            isValidEmailAndFlightNum(email, flightNum);
            people.get(email).addTicket(flights.get(flightNum), seat);
            System.out.println("Ticket successfully purchased!");
        } catch (Exception e) {
            throw e;
        }
    }

    public void cancelTicket(String email, String flightNum) throws Exception {
        try {
            isValidEmailAndFlightNum(email, flightNum);
            people.get(email).removeTicket(flights.get(flightNum));
            System.out.println("Ticket successfully canceled!");
        } catch (Exception e) {
            throw e;
        }
    }

    public void isValidEmailAndFlightNum(String email, String flightNum) throws Exception {
        if (!people.containsKey(email)) throw new NoPersonWithEmailException("No person associated with this email");
        if (!flights.containsKey(flightNum)) throw new NoFlightWithThisFlightNumberException("No such flight number!");
    }

    //getters
    public HashMap<String, Person> getPeople() {
        return people;
    }
    public HashMap<String, Flight> getFlights() {
        return flights;
    }
}
