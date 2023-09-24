```markdown
# Airline Ticket Management System

This project is an airline ticket management system, designed to manage users, buy tickets, cancel tickets, add flights, display all flights of a user, and list flight details. The program uses Java Swing for the GUI and serialization to store objects and read them back.

[Documentation PDF](https://github.com/almatn/airline-ticketing/blob/main/documentation.pdf)

## Features

- Create users
- Buy tickets for users
- Cancel tickets
- Add flights
- Display all flights of a user
- List flight details

## Classes

- Person: Represents a user with attributes such as name, age, email, and an attribute of type Flight to store flights in which the user has a ticket.
- Flight: Represents a flight with attributes such as flight number, date, destination, origin, plane type, seats, and an attribute to store the people who have tickets for this flight.
- Plane: Represents a plane model with attributes such as plane model and seats.

## Use Cases

The project contains several use cases:

1. Create user
2. Buy ticket
3. Cancel ticket
4. Add flight
5. List flight details
6. List tickets of a user

Please refer to the [documentation](https://github.com/almatn/airline-ticketing/blob/main/documentation.pdf) for more details on each use case, including main success scenarios and alternate scenarios.

## Installation

To install and run the project, follow these steps:

1. Clone the repository from GitHub

```sh
git clone https://github.com/almatn/airline-ticketing.git
```

2. Open the project in your favorite Java IDE

3. Compile and run the main class

## License

This project is released under the [MIT License](https://opensource.org/licenses/MIT).
