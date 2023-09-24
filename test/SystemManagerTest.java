import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemManagerTest {
    SystemManager systemManager;

    @BeforeEach
    void setUp() {
        systemManager = new SystemManager();
    }

    @Test
    void createPerson() throws Exception {
        systemManager.createPerson("Elon Musk", 44, "elon@musk.com");
        assertFalse(systemManager.getPeople().isEmpty());
        assertTrue(systemManager.getPeople().containsKey("elon@musk.com"));
        assertEquals("Elon Musk", systemManager.getPeople().get("elon@musk.com").getName());
        assertEquals("elon@musk.com", systemManager.getPeople().get("elon@musk.com").getEmail());
        assertEquals(44, systemManager.getPeople().get("elon@musk.com").getAge());
    }

    @Test
    @DisplayName("Age is less than zero")
    void ageIsLessThanZero() {
        Assertions.assertThrows(AgeLessThanZeroException.class, ()-> {
            systemManager.createPerson("Steve Sigal", -10, "segal@fly.com");
        });
    }

    @Test
    @DisplayName("Age is more than 99")
    void ageIsMoreThan99() {
        Assertions.assertThrows(AgeMoreThan99Exception.class, ()-> {
            systemManager.createPerson("Steve Sigal", 100, "segal@fly.com");
        });
    }

    @Test
    @DisplayName("Email not available")
    void emailNotAvailable() {
        Assertions.assertThrows(EmailNotAvailableException.class, ()-> {
            systemManager.createPerson("Steve Sigal", 44, "segal@fly.com");
            systemManager.createPerson("Steve Pratt", 23, "segal@fly.com");
        });
    }


    @Test
    void createFlight() throws Exception {
        systemManager.createFlight("TK505", "Bishkek", "Budapest", "A320", 120, 20, 23, 4, 2023);
        assertFalse(systemManager.getFlights().isEmpty());
        assertTrue(systemManager.getFlights().containsKey("TK505"));
        assertEquals("TK505", systemManager.getFlights().get("TK505").getFlightNumber());
        assertEquals("Bishkek", systemManager.getFlights().get("TK505").getOrigin());
        assertEquals("Budapest", systemManager.getFlights().get("TK505").getDestination());
        assertEquals("A320", systemManager.getFlights().get("TK505").getPlaneModel());
        assertEquals(120, systemManager.getFlights().get("TK505").getCapacity());
        assertEquals(20, systemManager.getFlights().get("TK505").getBusinessCapacity());
        assertEquals(23, systemManager.getFlights().get("TK505").getDay());
        assertEquals(4, systemManager.getFlights().get("TK505").getMonth());
        assertEquals(2023, systemManager.getFlights().get("TK505").getYear());
    }

    @Test
    void businessCapacityMoreThanCapacity() {
        Assertions.assertThrows(BusinessCapacityMoreThanCapacityExeption.class, ()-> {
            systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 50,60, 2, 4, 2021);
        });
    }

    @Test
    void invalidDate() {
        //short month out of bound
        Assertions.assertThrows(DateTimeException.class, ()-> {
            systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 29, 2, 2022);
        });
        //mm out of bound
        Assertions.assertThrows(DateTimeException.class, ()-> {
            systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 28, 14, 2022);
        });
        //dd out of bound
        Assertions.assertThrows(DateTimeException.class, ()-> {
            systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 44, 4, 2021);
        });
    }

    @Test
    void flightNumberAlreadyExist() {
        Assertions.assertThrows(FlightNumberAlreadyExistException.class, ()-> {
            systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 12, 4, 2021);
            systemManager.createFlight("TK404", "Bishkek", "Budapest", "A310", 180,20, 12, 6, 2021);
        });
    }


    @Test
    void buyTicket() throws Exception {
        systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 12, 4, 2021);
        systemManager.createPerson("Steve Sigal", 44, "segal@fly.com");
        systemManager.buyTicket("segal@fly.com", "TK404", "Business");

        assertTrue(systemManager.getPeople().get("segal@fly.com").getFlights().containsKey("TK404"));
        assertTrue(systemManager.getFlights().get("TK404").getPeople().get("Business").containsKey("segal@fly.com"));
    }

    @Test
    void cancelTicket() throws Exception {
        systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 12, 4, 2021);
        systemManager.createFlight("TK707", "Berlin", "Nottingham", "A320", 100, 10, 23,3,2023);
        systemManager.createPerson("Steve Sigal", 44, "segal@fly.com");
        systemManager.buyTicket("segal@fly.com", "TK404", "Business");
        systemManager.buyTicket("segal@fly.com", "TK707", "Economy");

        assertTrue(systemManager.getFlights().get("TK404").getPeople().get("Business").containsKey("segal@fly.com"));
        assertTrue(systemManager.getFlights().get("TK707").getPeople().get("Economy").containsKey("segal@fly.com"));
    }

    @Test
    void isValidEmailAndFlightNum() {
        Assertions.assertThrows(NoPersonWithEmailException.class, ()-> {
            systemManager.isValidEmailAndFlightNum("elon@tesla.com", "TK505");
        });
        Assertions.assertThrows(NoFlightWithThisFlightNumberException.class, ()-> {
            systemManager.createPerson("Steve Sigal", 44, "segal@fly.com");
            systemManager.isValidEmailAndFlightNum("segal@fly.com", "TK505");
        });
    }

    @Test
    void getPeople() throws Exception {
        assertTrue(systemManager.getPeople().isEmpty());
        systemManager.createPerson("Steve Sigal", 44, "segal@fly.com");
        systemManager.createPerson("Elon Musk", 44, "elon@tesla.com");
        assertTrue(systemManager.getPeople().containsKey("segal@fly.com"));
        assertTrue(systemManager.getPeople().containsKey("elon@tesla.com"));
        assertEquals(systemManager.getPeople().size(), 2);
        assertEquals(systemManager.getPeople().get("segal@fly.com").getEmail(), "segal@fly.com");
        assertEquals(systemManager.getPeople().get("segal@fly.com").getName(), "Steve Sigal");
        assertEquals(systemManager.getPeople().get("segal@fly.com").getAge(), 44);
    }

    @Test
    void getFlights() throws Exception {
        assertTrue(systemManager.getFlights().isEmpty());
        systemManager.createFlight("TK404", "Bishkek", "Berlin", "747", 180,20, 12, 4, 2021);
        systemManager.createFlight("TK707", "Berlin", "Nottingham", "A320", 100, 10, 23,3,2023);
        assertTrue(systemManager.getFlights().containsKey("TK404"));
        assertTrue(systemManager.getFlights().containsKey("TK707"));
        assertEquals(systemManager.getFlights().size(), 2);
        assertEquals(systemManager.getFlights().get("TK404").getOrigin(), "Bishkek");
        assertEquals(systemManager.getFlights().get("TK404").getPlaneModel(), "747");
        assertEquals(systemManager.getFlights().get("TK404").getCapacity(), 180);
    }
}