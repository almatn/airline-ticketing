import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.DateTimeException;

public class App extends JFrame {
    private JFrame mainFrame;
    private JPanel panel, mainPanel, userPanel, flightPanel, ticketPanel, cancelPanel, listFlightsPanel, listPeoplePanel;
    private JButton createUserViewButton, backButton1, createUserButton;
    private JButton createFlightViewButton, backButton2, createFlightButton;
    private JButton buyTicketViewButton, backButton3, buyButton;
    private JButton cancelViewButton, backButton4, cancelButton;
    private JButton flightDetailsViewButton, backButton5, flightDetailsButton;
    private JButton personDetailsViewButton, backButton6, personDetailsButton;
    private JLabel flightNumLabel, originLabel, destinationLabel, planeModelLabel, capacityLabel, businessCapacityLabel, dateLabel, businessLabel, economyLabel;
    private JLabel nameLabel, ageLabel, emailLabel;
    private JScrollPane flightsPane, businessPane, economyPane;
    private JList flightsList, businessList, economyList;
    private JTextField tName, tAge, tEmail;
    private JTextField tFlightNum, tOrigin, tDestination, tPlaneModel, tCapacity, tBusinessCapacity, tDD, tMM, tYY, tBuyFlightNum, tBuyEmail;
    private JTextField tCancelFlightNum, tCancelEmail;
    private JComboBox<String> cb, cb2, cb3;
    private CardLayout cardLayout; //because we need to access it in action listener
    private GridBagConstraints c;

    private SystemManager sysManager = new SystemManager();

    //build the main frame and all the cards (other windows)
    App() {
        // Load data at startup:
        try {
            FileInputStream f = new FileInputStream("data.dat");
            ObjectInputStream in = new ObjectInputStream(f);
            sysManager = (SystemManager) in.readObject();
            in.close();
            f.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found\n");
            c.printStackTrace();
        }
        mainFrame = new JFrame("Airline Ticket Management System");
        panel = new JPanel(cardLayout = new CardLayout());


        setMainView();
        setUserView();
        setFlightView();
        setTicketView();
        setCancelView();
        setListFlightView();
        setListPersonView();

        panel.add(mainPanel, "main");
        panel.add(userPanel, "user");
        panel.add(flightPanel, "flight");
        panel.add(ticketPanel, "ticket");
        panel.add(cancelPanel, "cancel");
        panel.add(listFlightsPanel, "listFlights");
        panel.add(listPeoplePanel, "listPeople");
        cardLayout.show(panel, "main");

        mainFrame.add(panel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);//center
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //save data on close window
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("data.dat");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(sysManager);
                    out.close();
                    fileOut.close();
                    System.out.printf("Serialized data is saved\n");
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
    }

    //set main menu with buttons & action listener
    public void setMainView() {
        mainPanel = new JPanel();

        createUserViewButton = new JButton("Create User");
        createFlightViewButton = new JButton("Create Flight");
        buyTicketViewButton = new JButton("Buy Ticket");
        cancelViewButton = new JButton("Cancel Ticket");
        flightDetailsViewButton = new JButton("List Flights");
        personDetailsViewButton = new JButton("List People");

        createUserViewButton.setPreferredSize(new Dimension(150, 50));
        createFlightViewButton.setPreferredSize(new Dimension(150, 50));
        buyTicketViewButton.setPreferredSize(new Dimension(150, 50));
        cancelViewButton.setPreferredSize(new Dimension(150, 50));
        flightDetailsViewButton.setPreferredSize(new Dimension(150, 50));
        personDetailsViewButton.setPreferredSize(new Dimension(150, 50));

        createUserViewButton.setMaximumSize(new Dimension(150, 50));
        createFlightViewButton.setMaximumSize(new Dimension(150, 50));
        buyTicketViewButton.setMaximumSize(new Dimension(150, 50));
        cancelViewButton.setMaximumSize(new Dimension(150, 50));
        flightDetailsViewButton.setMaximumSize(new Dimension(150, 50));
        personDetailsViewButton.setMaximumSize(new Dimension(150, 50));

        createUserViewButton.setHorizontalAlignment(JButton.CENTER);
        createFlightViewButton.setHorizontalAlignment(JButton.CENTER);
        buyTicketViewButton.setHorizontalAlignment(JButton.CENTER);
        cancelViewButton.setHorizontalAlignment(JButton.CENTER);
        flightDetailsViewButton.setHorizontalAlignment(JButton.CENTER);
        personDetailsViewButton.setHorizontalAlignment(JButton.CENTER);

        createUserViewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        createFlightViewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        buyTicketViewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        cancelViewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        flightDetailsViewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        personDetailsViewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);


        createUserViewButton.setActionCommand("user");
        createFlightViewButton.setActionCommand("flight");
        buyTicketViewButton.setActionCommand("ticket");
        cancelViewButton.setActionCommand("cancel");
        flightDetailsViewButton.setActionCommand("listFlights");
        personDetailsViewButton.setActionCommand("listPeople");

        createUserViewButton.addActionListener(new myActiveListener());
        createFlightViewButton.addActionListener(new myActiveListener());
        buyTicketViewButton.addActionListener(new myActiveListener());
        cancelViewButton.addActionListener(new myActiveListener());
        flightDetailsViewButton.addActionListener(new myActiveListener());
        personDetailsViewButton.addActionListener(new myActiveListener());

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(new Insets(60, 100, 60, 100)));
        mainPanel.add(createUserViewButton);
        mainPanel.add(Box.createVerticalStrut(25));//space between buttons
        mainPanel.add(createFlightViewButton);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(buyTicketViewButton);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(cancelViewButton);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(flightDetailsViewButton);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(personDetailsViewButton);
    }

    //set user menu with buttons, labels, text fields & action listener (creation of the new user)
    public void setUserView() {
        userPanel = new JPanel();
        tName = new JTextField(20);
        tAge = new JTextField(2);
        tEmail = new JTextField(25);

        backButton1 = new JButton("Back");
        backButton1.setActionCommand("back");
        backButton1.addActionListener(new myActiveListener());

        createUserButton = new JButton("Create");
        createUserButton.setActionCommand("createUser");
        createUserButton.addActionListener(new myActiveListener());

        userPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        userPanel.add(new JLabel("name:"), c);
        c.gridx = 1;
        c.gridy = 1;
        userPanel.add(tName, c);
        c.gridx = 0;
        c.gridy = 2;
        userPanel.add(new JLabel("age:"), c);
        c.gridx = 1;
        c.gridy = 2;
        userPanel.add(tAge, c);
        c.gridx = 0;
        c.gridy = 3;
        userPanel.add(new JLabel("email:"), c);
        c.gridx = 1;
        c.gridy = 3;
        userPanel.add(tEmail, c);

        c.gridx = 0;
        c.gridy = 4;
        userPanel.add(backButton1, c);
        c.gridx = 1;
        c.gridy = 4;
        userPanel.add(createUserButton, c);


        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        userPanel.add(new JLabel("Create User", SwingConstants.CENTER), c);

    }

    //set flight menu with buttons, labels, text fields & action listener (creation of the new flight)
    public void setFlightView() {
        flightPanel = new JPanel();
        tFlightNum = new JTextField(20);
        tOrigin = new JTextField(20);
        tDestination = new JTextField(20);
        tPlaneModel = new JTextField(20);
        tCapacity = new JTextField(20);
        tBusinessCapacity = new JTextField(20);
        tDD = new JTextField(2);
        tMM = new JTextField(2);
        tYY = new JTextField(4);

        backButton2 = new JButton("Back");
        backButton2.setActionCommand("back");
        backButton2.addActionListener(new myActiveListener());

        createFlightButton = new JButton("Create");
        createFlightButton.setActionCommand("createFlight");
        createFlightButton.addActionListener(new myActiveListener());

        flightPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        flightPanel.add(new JLabel("Flight Number:"), c);
        c.gridx = 1;
        c.gridy = 1;
        flightPanel.add(tFlightNum, c);
        c.gridx = 0;
        c.gridy = 2;
        flightPanel.add(new JLabel("Origin:"), c);
        c.gridx = 1;
        c.gridy = 2;
        flightPanel.add(tOrigin, c);
        c.gridx = 0;
        c.gridy = 3;
        flightPanel.add(new JLabel("Destination:"), c);
        c.gridx = 1;
        c.gridy = 3;
        flightPanel.add(tDestination, c);
        c.gridx = 0;
        c.gridy = 4;
        flightPanel.add(new JLabel("Plane Model:"), c);
        c.gridx = 1;
        c.gridy = 4;
        flightPanel.add(tPlaneModel, c);
        c.gridx = 0;
        c.gridy = 5;
        flightPanel.add(new JLabel("Capacity:"), c);
        c.gridx = 1;
        c.gridy = 5;
        flightPanel.add(tCapacity, c);
        c.gridx = 0;
        c.gridy = 6;
        flightPanel.add(new JLabel("Business Capacity:"), c);
        c.gridx = 1;
        c.gridy = 6;
        flightPanel.add(tBusinessCapacity, c);
        c.gridx = 0;
        c.gridy = 7;
        flightPanel.add(new JLabel("Day:"), c);
        c.gridx = 1;
        c.gridy = 7;
        flightPanel.add(tDD, c);
        c.gridx = 0;
        c.gridy = 8;
        flightPanel.add(new JLabel("Month:"), c);
        c.gridx = 1;
        c.gridy = 8;
        flightPanel.add(tMM, c);
        c.gridx = 0;
        c.gridy = 9;
        flightPanel.add(new JLabel("Year:"), c);
        c.gridx = 1;
        c.gridy = 9;
        flightPanel.add(tYY, c);

        c.gridx = 0;
        c.gridy = 10;
        flightPanel.add(backButton2, c);
        c.gridx = 1;
        c.gridy = 10;
        flightPanel.add(createFlightButton, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        flightPanel.add(new JLabel("Create Flight", SwingConstants.CENTER), c);
    }

    //set ticket reservation menu with buttons, labels, text fields & action listener (buying a ticket for a flight for a user)
    public void setTicketView() {
        ticketPanel = new JPanel();
        tBuyFlightNum = new JTextField(20);
        tBuyEmail = new JTextField(25);
        String cbItems[] = {"Business", "Economy"};
        cb = new JComboBox<>(cbItems);

        backButton3 = new JButton("Back");
        backButton3.setActionCommand("back");
        backButton3.addActionListener(new myActiveListener());

        buyButton = new JButton("Buy");
        buyButton.setActionCommand("buyTicket");
        buyButton.addActionListener(new myActiveListener());

        ticketPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        ticketPanel.add(new JLabel("Flight Number:"), c);
        c.gridx = 1;
        c.gridy = 1;
        ticketPanel.add(tBuyFlightNum, c);
        c.gridx = 0;
        c.gridy = 2;
        ticketPanel.add(new JLabel("Email:"), c);
        c.gridx = 1;
        c.gridy = 2;
        ticketPanel.add(tBuyEmail, c);
        c.gridx = 0;
        c.gridy = 3;
        ticketPanel.add(new JLabel("Seat:"), c);
        c.gridx = 1;
        c.gridy = 3;
        ticketPanel.add(cb, c);

        c.gridx = 0;
        c.gridy = 4;
        ticketPanel.add(backButton3, c);
        c.gridx = 1;
        c.gridy = 4;
        ticketPanel.add(buyButton, c);


        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        ticketPanel.add(new JLabel("Buy Ticket", SwingConstants.CENTER), c);
    }

    public void setCancelView() {
        cancelPanel = new JPanel();
        tCancelFlightNum = new JTextField(20);
        tCancelEmail = new JTextField(25);

        backButton4 = new JButton("Back");
        backButton4.setActionCommand("back");
        backButton4.addActionListener(new myActiveListener());

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setActionCommand("cancelTicket");
        cancelButton.addActionListener(new myActiveListener());

        cancelPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        cancelPanel.add(new JLabel("Flight Number:"), c);
        c.gridx = 1;
        c.gridy = 1;
        cancelPanel.add(tCancelFlightNum, c);
        c.gridx = 0;
        c.gridy = 2;
        cancelPanel.add(new JLabel("Email:"), c);
        c.gridx = 1;
        c.gridy = 2;
        cancelPanel.add(tCancelEmail, c);

        c.gridx = 0;
        c.gridy = 3;
        cancelPanel.add(backButton4, c);
        c.gridx = 1;
        c.gridy = 3;
        cancelPanel.add(cancelButton, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        cancelPanel.add(new JLabel("Cancel Ticket", SwingConstants.CENTER), c);
    }

    public void setListFlightView() {
        listFlightsPanel = new JPanel();
        //set combo box a list of existing flight numbers
        cb2 = new JComboBox<>(sysManager.getFlights().keySet().toArray(new String[sysManager.getFlights().keySet().size()]));

        backButton5 = new JButton("Back");
        backButton5.setActionCommand("back");
        backButton5.addActionListener(new myActiveListener());

        flightNumLabel = new JLabel();
        originLabel = new JLabel();
        destinationLabel = new JLabel();
        planeModelLabel = new JLabel();
        capacityLabel = new JLabel();
        businessCapacityLabel = new JLabel();
        dateLabel = new JLabel();
        businessLabel = new JLabel();
        economyLabel = new JLabel();

        businessList = new JList();
        businessList.setPrototypeCellValue("steve@apple.com");//width of the jlist
        businessList.setLayoutOrientation(JList.VERTICAL);
        businessList.setVisibleRowCount(2);//height of the jlist
        businessPane = new JScrollPane(businessList);
        businessPane.setViewportView(businessList);

        economyList = new JList();
        economyList.setPrototypeCellValue("steve@apple.com");//width of the jlist
        economyList.setLayoutOrientation(JList.VERTICAL);
        economyList.setVisibleRowCount(2);//height of the jlist
        economyPane = new JScrollPane(economyList);
        economyPane.setViewportView(economyList);

        flightDetailsButton = new JButton("List Details");
        flightDetailsButton.setActionCommand("flightDetails");
        flightDetailsButton.addActionListener(new myActiveListener());

        listFlightsPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        listFlightsPanel.add(cb2, c);
        c.gridx = 1;
        c.gridy = 1;
        listFlightsPanel.add(flightDetailsButton, c);

        c.gridx = 0;
        c.gridy = 11;
        listFlightsPanel.add(backButton5, c);

        c.gridx = 0;
        c.gridy = 2;
        listFlightsPanel.add(new JLabel("Flight number:"), c);
        c.gridx = 0;
        c.gridy = 3;
        listFlightsPanel.add(new JLabel("Origin:"), c);
        c.gridx = 0;
        c.gridy = 4;
        listFlightsPanel.add(new JLabel("Destination:"), c);
        c.gridx = 0;
        c.gridy = 5;
        listFlightsPanel.add(new JLabel("Plane model:"), c);
        c.gridx = 0;
        c.gridy = 6;
        listFlightsPanel.add(new JLabel("Capacity:"), c);
        c.gridx = 0;
        c.gridy = 7;
        listFlightsPanel.add(new JLabel("Business capacity:"), c);
        c.gridx = 0;
        c.gridy = 8;
        listFlightsPanel.add(new JLabel("Date:"), c);
        c.gridx = 0;
        c.gridy = 9;
        listFlightsPanel.add(new JLabel("Business:"), c);
        c.gridx = 0;
        c.gridy = 10;
        listFlightsPanel.add(new JLabel("Economy:"), c);

        c.gridx = 1;
        c.gridy = 2;
        listFlightsPanel.add(flightNumLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        listFlightsPanel.add(originLabel, c);
        c.gridx = 1;
        c.gridy = 4;
        listFlightsPanel.add(destinationLabel, c);
        c.gridx = 1;
        c.gridy = 5;
        listFlightsPanel.add(planeModelLabel, c);
        c.gridx = 1;
        c.gridy = 6;
        listFlightsPanel.add(capacityLabel, c);
        c.gridx = 1;
        c.gridy = 7;
        listFlightsPanel.add(businessCapacityLabel, c);
        c.gridx = 1;
        c.gridy = 8;
        listFlightsPanel.add(dateLabel, c);
        c.gridx = 1;
        c.gridy = 9;
        listFlightsPanel.add(businessPane, c);
        c.gridx = 1;
        c.gridy = 10;
        listFlightsPanel.add(economyPane, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        listFlightsPanel.add(new JLabel("List Flight Details", SwingConstants.CENTER), c);
    }

    public void setListPersonView() {
        listPeoplePanel = new JPanel();

        //set combo box a list of existing flight numbers
        cb3 = new JComboBox<>(sysManager.getPeople().keySet().toArray(new String[sysManager.getFlights().keySet().size()]));

        backButton6 = new JButton("Back");
        backButton6.setActionCommand("back");
        backButton6.addActionListener(new myActiveListener());

        nameLabel = new JLabel();
        ageLabel = new JLabel();
        emailLabel = new JLabel();

        flightsList = new JList();
        flightsList.setPrototypeCellValue("steve@apple.com");//width of the jlist
        flightsList.setLayoutOrientation(JList.VERTICAL);
        flightsList.setVisibleRowCount(2);//height of the jlist
        flightsPane = new JScrollPane(flightsList);
        flightsPane.setViewportView(flightsList);

        personDetailsButton = new JButton("List Details");
        personDetailsButton.setActionCommand("personDetails");
        personDetailsButton.addActionListener(new myActiveListener());

        listPeoplePanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        listPeoplePanel.add(cb3, c);
        c.gridx = 1;
        c.gridy = 1;
        listPeoplePanel.add(personDetailsButton, c);

        c.gridx = 0;
        c.gridy = 11;
        listPeoplePanel.add(backButton6, c);

        c.gridx = 0;
        c.gridy = 2;
        listPeoplePanel.add(new JLabel("Name:"), c);
        c.gridx = 0;
        c.gridy = 3;
        listPeoplePanel.add(new JLabel("Age:"), c);
        c.gridx = 0;
        c.gridy = 4;
        listPeoplePanel.add(new JLabel("Email:"), c);
        c.gridx = 0;
        c.gridy = 5;
        listPeoplePanel.add(new JLabel("Flights:"), c);

        c.gridx = 1;
        c.gridy = 2;
        listPeoplePanel.add(nameLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        listPeoplePanel.add(ageLabel, c);
        c.gridx = 1;
        c.gridy = 4;
        listPeoplePanel.add(emailLabel, c);
        c.gridx = 1;
        c.gridy = 5;
        listPeoplePanel.add(flightsPane, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        listPeoplePanel.add(new JLabel("List Person Details", SwingConstants.CENTER), c);
    }

    public void updateJlist(String[] arr, JList list) {
        DefaultListModel defaultList = new DefaultListModel();
        for (String item : arr) {
            defaultList.addElement(item);
        }
        list.setModel(defaultList);
    }

    class myActiveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            if (a.getActionCommand().equals("user")) {
                cardLayout.show(panel, "user");
            } else if (a.getActionCommand().equals("flight")) {
                cardLayout.show(panel, "flight");
            } else if (a.getActionCommand().equals("ticket")) {
                cardLayout.show(panel, "ticket");
            } else if (a.getActionCommand().equals("cancel")) {
                cardLayout.show(panel, "cancel");
            } else if (a.getActionCommand().equals("listFlights")) {
                //cb's are dynamic so it updates on each click
                cb2.setModel(new DefaultComboBoxModel<>(sysManager.getFlights().keySet().toArray(new String[sysManager.getFlights().keySet().size()])));
                cardLayout.show(panel, "listFlights");
            } else if (a.getActionCommand().equals("listPeople")) {
                //cb's are dynamic so it updates on each click
                cb3.setModel(new DefaultComboBoxModel<>(sysManager.getPeople().keySet().toArray(new String[sysManager.getFlights().keySet().size()])));
                cardLayout.show(panel, "listPeople");
            } else if (a.getActionCommand().equals("back")) {
                cardLayout.show(panel, "main");
            } else if (a.getActionCommand().equals("createUser")) {
                try {
                    tAge.setBorder(tName.getBorder()); //put border color to default
                    tEmail.setBorder(tName.getBorder());
                    sysManager.createPerson(tName.getText(), Integer.parseInt(tAge.getText()), tEmail.getText());
                    JOptionPane.showMessageDialog(userPanel, "Person successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(userPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    if (e.getClass() == AgeLessThanZeroException.class || e.getClass() == AgeMoreThan99Exception.class) {
                        tAge.setBorder(new LineBorder(Color.red, 1));
                    } else if (e.getClass() == EmailNotAvailableException.class) {
                        tEmail.setBorder(new LineBorder(Color.red, 1));
                    }
                }
            } else if (a.getActionCommand().equals("createFlight")) {
                try {
                    tDD.setBorder(tName.getBorder());//reset red border
                    tMM.setBorder(tName.getBorder());
                    tYY.setBorder(tName.getBorder());
                    tCapacity.setBorder(tName.getBorder());
                    tBusinessCapacity.setBorder(tName.getBorder());
                    sysManager.createFlight(tFlightNum.getText(), tOrigin.getText(), tDestination.getText(), tPlaneModel.getText(), Integer.parseInt(tCapacity.getText()), Integer.parseInt(tBusinessCapacity.getText()), Integer.parseInt(tDD.getText()), Integer.parseInt(tMM.getText()), Integer.parseInt(tYY.getText()));
                    JOptionPane.showMessageDialog(flightPanel, "Flight successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (DateTimeException t) {
                    JOptionPane.showMessageDialog(flightPanel, t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    tDD.setBorder(new LineBorder(Color.red, 1));//set to red the error field
                    tMM.setBorder(new LineBorder(Color.red, 1));
                    tYY.setBorder(new LineBorder(Color.red, 1));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(flightPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    if (e.getClass() == FlightNumberAlreadyExistException.class) {
                        tFlightNum.setBorder(new LineBorder(Color.red, 1));
                    } else if (e.getClass() == BusinessCapacityMoreThanCapacityExeption.class) {
                        tCapacity.setBorder(new LineBorder(Color.red, 1));
                        tBusinessCapacity.setBorder(new LineBorder(Color.red, 1));
                    }
                }
            } else if (a.getActionCommand().equals("buyTicket")) {
                try {
                    sysManager.buyTicket(tBuyEmail.getText(), tBuyFlightNum.getText(), cb.getSelectedItem().toString());
                    tFlightNum.setBorder(tName.getBorder());
                    tBuyEmail.setBorder(tName.getBorder());
                    JOptionPane.showMessageDialog(ticketPanel, "Ticket successfully purchased!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ticketPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    if (e.getMessage().equals("No such flight number!")) tBuyFlightNum.setBorder(new LineBorder(Color.red, 1));
                    if (e.getMessage().equals("No person associated with this email")) tBuyEmail.setBorder(new LineBorder(Color.red, 1));
                }
            } else if (a.getActionCommand().equals("cancelTicket")) {
                try {
                    tCancelFlightNum.setBorder(tName.getBorder());
                    tCancelEmail.setBorder(tName.getBorder());
                    sysManager.cancelTicket(tCancelEmail.getText(), tCancelFlightNum.getText());
                    JOptionPane.showMessageDialog(cancelPanel, "Ticket successfully canceled!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(cancelPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    if (e.getClass() == NoFlightWithThisFlightNumberException.class) tCancelFlightNum.setBorder(new LineBorder(Color.red, 1));
                    if (e.getClass() == NoPersonWithEmailException.class) tCancelEmail.setBorder(new LineBorder(Color.red, 1));
                }
            } else if (a.getActionCommand().equals("flightDetails")) {
                String selection = cb2.getSelectedItem().toString();
                Flight selectedFlight = sysManager.getFlights().get(selection);
                flightNumLabel.setText(selection);
                originLabel.setText(selectedFlight.getOrigin());
                destinationLabel.setText(selectedFlight.getDestination());
                planeModelLabel.setText(selectedFlight.getPlaneModel());
                capacityLabel.setText("" + selectedFlight.getCapacity());
                businessCapacityLabel.setText("" + selectedFlight.getBusinessCapacity());
                dateLabel.setText(selectedFlight.getDay() + "/" + selectedFlight.getMonth() + "/" + selectedFlight.getYear());
                businessLabel.setText(selectedFlight.getPeople().get("Business").keySet().toString());
                economyLabel.setText(selectedFlight.getPeople().get("Economy").keySet().toString());

                //jlist's are dynamic so it updates on each click
                updateJlist(selectedFlight.getPeople().get("Business").keySet().toArray(new String[sysManager.getFlights().keySet().size()]), businessList);
                updateJlist(selectedFlight.getPeople().get("Economy").keySet().toArray(new String[sysManager.getFlights().keySet().size()]), economyList);

            } else if (a.getActionCommand().equals("personDetails")) {
                String selection = cb3.getSelectedItem().toString();
                nameLabel.setText(selection);
                ageLabel.setText("" + sysManager.getPeople().get(selection).getAge());
                emailLabel.setText(sysManager.getPeople().get(selection).getEmail());

                //jlist's are dynamic so it updates on each click
                updateJlist(sysManager.getPeople().get(selection).getFlights().keySet().toArray(new String[sysManager.getPeople().get(selection).getFlights().keySet().size()]), flightsList);
            }
        }
    }

    public static void main(String[] args) {
        App app = new App();
    }
}