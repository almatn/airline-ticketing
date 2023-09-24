import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    Person p1, p2;
    Flight f1, f2, f3;

    @BeforeEach
    void setUp() {
        p1 = new Person("Jeff Bezos", 55, "jeffwho@amazon.com");
        p2 = new Person("Elon Musk", 44, "elon@tesla.com");
        f1 = new Flight("TK606", "Brussels", "Helsinki", "321NEO", 110, 1, 23,3,2023);
        f2 = new Flight("TK505", "London", "Helsinki", "321NEO", 11, 10, 19,5,2023);
        f3 = new Flight("TK707", "Berlin", "Nottingham", "A320", 100, 10, 23,3,2023);
    }

    @Test
    void addTicket() throws Exception {
        p1.addTicket(f1, "Business");
        assertFalse(p1.getFlights().isEmpty());
        assertTrue(p1.getFlights().containsKey("TK606"));
        assertTrue(p1.getFlights().get("TK606") == f1);
    }

    @Test
    void SeatNotAvailable() {
        Assertions.assertThrows(SeatNotAvailableException.class, ()-> {
            p1.addTicket(f1, "Business");
            p2.addTicket(f1, "Business");
        });
        Assertions.assertThrows(SeatNotAvailableException.class, ()-> {
            p1.addTicket(f2, "Economy");
            p2.addTicket(f2, "Economy");
        });
    }

    @Test
    void AlreadyHasTicket() {
        Assertions.assertThrows(AlreadyHasTicketException.class, ()-> {
            p1.addTicket(f3, "Business");
            p1.addTicket(f3, "Business");
        });
        Assertions.assertThrows(AlreadyHasTicketException.class, ()-> {
            p1.addTicket(f1, "Economy");
            p1.addTicket(f1, "Economy");
        });
        Assertions.assertThrows(AlreadyHasTicketException.class, ()-> {
            p1.addTicket(f1, "Economy");
            p1.addTicket(f1, "Business");
        });
    }

    @Test
    void removeTicket() throws Exception {
        p1.addTicket(f1, "Business");
        p1.addTicket(f2, "Economy");
        p1.removeTicket(f2);
        assertFalse(p1.getFlights().containsKey("TK505"));
        assertFalse(f2.getPeople().containsKey("jeffwho@amazon.com"));
    }

    @Test
    void HasNoTicket() {
        Assertions.assertThrows(HasNoTicketException.class, ()-> {
            p1.removeTicket(f1);
        });
    }

    @Test
    void getFlights() throws Exception {
        //should be empty in the beginning
        assertTrue(p1.getFlights().isEmpty());
        p1.addTicket(f1, "Business");
        assertEquals(p1.getFlights().get(f1.getFlightNumber()), f1);
    }

    @Test
    void getName() {
        assertEquals("Jeff Bezos", p1.getName());
        assertEquals("Jeff Bezos", p1.getName());
    }

    @Test
    void getEmail() {
        p1.getEmail();
        p2.getEmail();
    }

    @Test
    void getAge() {
        p1.getAge();
        p2.getAge();
    }
}