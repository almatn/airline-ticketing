import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {
    Person p1, p2;
    Flight f1, f2, f3;

    @BeforeEach
    void setUp() {
        p1 = new Person("Jeff Bezos", 55, "jeffwho@amazon.com");
        p2 = new Person("Elon Musk", 44, "elon@tesla.com");
        f1 = new Flight("TK606", "Brussels", "Helsinki", "321NEO", 110, 1, 23,3,2023);
        f2 = new Flight("TK505", "London", "NY", "321NEO", 11, 10, 19,5,2024);
        f3 = new Flight("TK707", "Berlin", "Nottingham", "A320", 100, 10, 23,3,2023);
    }


    @Test
    void addPassenger() {
        assertTrue(f1.getPeople().get("Business").isEmpty());
        assertTrue(f1.getPeople().get("Economy").isEmpty());
        f1.addPassenger(p1, "Business");
        f1.addPassenger(p2, "Economy");
        assertEquals(f1.getPeople().get("Business").get("jeffwho@amazon.com"), p1);
        assertEquals(f1.getPeople().get("Economy").get("elon@tesla.com"), p2);
    }

    @Test
    void removePassenger() throws Exception {
        f1.addPassenger(p1, "Business");
        f1.addPassenger(p2, "Economy");
        assertFalse(f1.getPeople().get("Business").isEmpty());
        assertFalse(f1.getPeople().get("Economy").isEmpty());
        f1.removePassenger(p1);
        f1.removePassenger(p2);
        assertFalse(f1.getPeople().get("Business").containsKey(p1.getEmail()));
        assertFalse(f1.getPeople().get("Economy").containsKey(p2.getEmail()));
    }

    @DisplayName("Person has no ticket")
    @Test
    void personHasNoTicket() {
        Assertions.assertThrows(HasNoTicketException.class, ()-> {
            f1.removePassenger(p1);
        });
    }


    @Test
    void getPeople() {
        assertTrue(f1.getPeople().get("Business").isEmpty());
        assertTrue(f1.getPeople().get("Economy").isEmpty());
        f1.addPassenger(p1, "Business");
        f1.addPassenger(p2, "Economy");
        assertEquals(f1.getPeople().get("Business").get("jeffwho@amazon.com"), p1);
        assertEquals(f1.getPeople().get("Economy").get("elon@tesla.com"), p2);
    }

    @Test
    void getFlightNumber() {
        assertEquals(f1.getFlightNumber(), "TK606");
        assertEquals(f2.getFlightNumber(), "TK505");
    }

    @Test
    void getDay() {
        assertEquals(f1.getDay(), 23);
        assertEquals(f2.getDay(), 19);
    }

    @Test
    void getMonth() {
        assertEquals(f1.getMonth(), 3);
        assertEquals(f2.getMonth(), 5);
    }

    @Test
    void getYear() {
        assertEquals(f1.getYear(), 2023);
        assertEquals(f2.getYear(), 2024);
    }

    @Test
    void getOrigin() {
        assertEquals(f1.getOrigin(), "Brussels");
        assertEquals(f2.getOrigin(), "London");
    }

    @Test
    void getDestination() {
        assertEquals(f1.getDestination(), "Helsinki");
        assertEquals(f2.getDestination(), "NY");
    }

    @Test
    void getPlaneModel() {
        assertEquals(f1.getPlaneModel(), "321NEO");
        assertEquals(f2.getPlaneModel(), "321NEO");
    }

    @Test
    void getCapacity() {
        assertEquals(f1.getCapacity(), 110);
        assertEquals(f2.getCapacity(), 11);
    }

    @Test
    void getBusinessCapacity() {
        assertEquals(f1.getBusinessCapacity(), 1);
        assertEquals(f2.getBusinessCapacity(), 10);
    }
}