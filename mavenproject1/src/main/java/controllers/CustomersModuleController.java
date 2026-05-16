package controllers;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Customer;
import models.Location;
import models.Address;
import models.CartRecord;
import models.CartItemRecord;
import models.OrderMenuItem;
import models.NotificationRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomersModuleController {

    // ================== CUSTOMERS SECTION ==================

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> usernameColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> emailColumn;

    @FXML
    private TableColumn<Customer, String> passwordColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final ObservableList<Customer> customersList = FXCollections.observableArrayList();


    // ================== LOCATIONS SECTION ==================

    @FXML
    private TableView<Location> locationsTable;

    @FXML
    private TableColumn<Location, Integer> locationIdColumn;

    @FXML
    private TableColumn<Location, String> cityColumn;

    @FXML
    private TableColumn<Location, String> streetColumn;

    @FXML
    private TableColumn<Location, String> buildingColumn;

    @FXML
    private TextField locationSearchField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField buildingField;

    @FXML
    private Label locationMessageLabel;

    private final ObservableList<Location> locationsList = FXCollections.observableArrayList();


    // ================== ADDRESSES SECTION ==================

    @FXML
    private TableView<Address> addressesTable;

    @FXML
    private TableColumn<Address, Integer> addressIdColumn;

    @FXML
    private TableColumn<Address, Integer> addressCustomerIdColumn;

    @FXML
    private TableColumn<Address, String> addressCustomerNameColumn;

    @FXML
    private TableColumn<Address, Integer> addressLocationIdColumn;

    @FXML
    private TableColumn<Address, String> addressLocationDetailsColumn;

    @FXML
    private TextField addressSearchField;

    @FXML
    private ComboBox<Customer> addressCustomerComboBox;

    @FXML
    private ComboBox<Location> addressLocationComboBox;

    @FXML
    private Label addressMessageLabel;

    private final ObservableList<Address> addressesList = FXCollections.observableArrayList();


    // ================== CARTS SECTION ==================

    @FXML
    private TableView<CartRecord> cartsTable;

    @FXML
    private TableColumn<CartRecord, Integer> cartIdColumn;

    @FXML
    private TableColumn<CartRecord, Integer> cartCustomerIdColumn;

    @FXML
    private TableColumn<CartRecord, String> cartCustomerNameColumn;

    @FXML
    private TextField cartSearchField;

    @FXML
    private ComboBox<Customer> cartCustomerComboBox;

    @FXML
    private Label cartMessageLabel;

    private final ObservableList<CartRecord> cartsList = FXCollections.observableArrayList();


    // ================== CART ITEMS SECTION ==================

    @FXML
    private TableView<CartItemRecord> cartItemsTable;

    @FXML
    private TableColumn<CartItemRecord, Integer> cartItemIdColumn;

    @FXML
    private TableColumn<CartItemRecord, Integer> cartItemCartIdColumn;

    @FXML
    private TableColumn<CartItemRecord, Integer> cartItemItemIdColumn;

    @FXML
    private TableColumn<CartItemRecord, String> cartItemNameColumn;

    @FXML
    private TableColumn<CartItemRecord, String> cartItemRestaurantColumn;

    @FXML
    private TableColumn<CartItemRecord, Double> cartItemPriceColumn;

    @FXML
    private TableColumn<CartItemRecord, Integer> cartItemQuantityColumn;

    @FXML
    private TableColumn<CartItemRecord, Double> cartItemSubtotalColumn;

    @FXML
    private TextField cartItemSearchField;

    @FXML
    private ComboBox<CartRecord> cartItemCartComboBox;

    @FXML
    private ComboBox<OrderMenuItem> cartItemMenuItemComboBox;

    @FXML
    private TextField cartItemQuantityField;

    @FXML
    private TextField cartItemSubtotalField;

    @FXML
    private Label cartItemMessageLabel;

    private final ObservableList<CartItemRecord> cartItemsList = FXCollections.observableArrayList();
    private final ObservableList<OrderMenuItem> cartMenuItemsList = FXCollections.observableArrayList();



    // ================== NOTIFICATIONS SECTION ==================

    @FXML
    private TableView<NotificationRecord> notificationsTable;

    @FXML
    private TableColumn<NotificationRecord, Integer> notificationIdColumn;

    @FXML
    private TableColumn<NotificationRecord, Integer> notificationCustomerIdColumn;

    @FXML
    private TableColumn<NotificationRecord, String> notificationCustomerNameColumn;

    @FXML
    private TableColumn<NotificationRecord, String> notificationMessageColumn;

    @FXML
    private TableColumn<NotificationRecord, String> notificationDateColumn;

    @FXML
    private TextField notificationSearchField;

    @FXML
    private ComboBox<Customer> notificationCustomerComboBox;

    @FXML
    private TextArea notificationMessageArea;

    @FXML
    private TextField notificationDateField;

    @FXML
    private Label notificationMessageLabel;

    private final ObservableList<NotificationRecord> notificationsList = FXCollections.observableArrayList();


    // ================== INITIALIZE ==================

    @FXML
    public void initialize() {

        // ================== CUSTOMERS TABLE SETUP ==================

        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadCustomers();

        customersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedCustomer) -> {
                    if (selectedCustomer != null) {
                        usernameField.setText(selectedCustomer.getUsername());
                        nameField.setText(selectedCustomer.getName());
                        phoneField.setText(selectedCustomer.getPhone());
                        emailField.setText(selectedCustomer.getEmail());
                        passwordField.setText(selectedCustomer.getPassword());
                    }
                }
        );


        // ================== LOCATIONS TABLE SETUP ==================

        locationIdColumn.setCellValueFactory(new PropertyValueFactory<>("locationId"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("building"));

        loadLocations();

        locationsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedLocation) -> {
                    if (selectedLocation != null) {
                        cityField.setText(selectedLocation.getCity());
                        streetField.setText(selectedLocation.getStreet());
                        buildingField.setText(selectedLocation.getBuilding());
                    }
                }
        );


        // ================== ADDRESSES TABLE SETUP ==================

        addressIdColumn.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        addressCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        addressCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressLocationIdColumn.setCellValueFactory(new PropertyValueFactory<>("locationId"));
        addressLocationDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("locationDetails"));

        loadAddressComboBoxes();
        loadAddresses();

        addressesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedAddress) -> {
                    if (selectedAddress != null) {
                        selectCustomerInAddressCombo(selectedAddress.getCustomerId());
                        selectLocationInAddressCombo(selectedAddress.getLocationId());
                    }
                }
        );



        // ================== CARTS TABLE SETUP ==================

        cartIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartId"));
        cartCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cartCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        cartItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartItemId"));
        cartItemCartIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartId"));
        cartItemItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        cartItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        cartItemRestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        cartItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cartItemSubtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        loadCartMenuItemComboBox();
        loadCarts();
        loadCartItems();
        loadCartComboBoxes();

        cartsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedCart) -> {
                    if (selectedCart != null) {
                        selectCustomerInCartCombo(selectedCart.getCustomerId());
                    }
                }
        );

        cartItemsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedCartItem) -> {
                    if (selectedCartItem != null) {
                        selectCartInCartItemCombo(selectedCartItem.getCartId());
                        selectMenuItemInCartItemCombo(selectedCartItem.getItemId());
                        cartItemQuantityField.setText(String.valueOf(selectedCartItem.getQuantity()));
                        cartItemSubtotalField.setText(String.valueOf(selectedCartItem.getSubtotal()));
                    }
                }
        );


        // ================== NOTIFICATIONS TABLE SETUP ==================

        notificationIdColumn.setCellValueFactory(new PropertyValueFactory<>("notificationId"));
        notificationCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        notificationCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        notificationMessageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        notificationDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadNotificationComboBox();
        loadNotifications();

        notificationsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedNotification) -> {
                    if (selectedNotification != null) {
                        selectCustomerInNotificationCombo(selectedNotification.getCustomerId());
                        notificationMessageArea.setText(selectedNotification.getMessage());
                        notificationDateField.setText(selectedNotification.getDate());
                    }
                }
        );
    }


    // ================== CUSTOMERS CRUD ==================

    @FXML
    void loadCustomers() {
        customersList.clear();

        String sql = "SELECT customer_id, username, name, phone, email, password FROM customer ORDER BY customer_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );

                customersList.add(customer);
            }

            customersTable.setItems(customersList);
            showCustomerSuccess("Customers loaded successfully");

            loadAddressComboBoxes();
            loadCartComboBoxes();
            loadNotificationComboBox();

        } catch (Exception e) {
            e.printStackTrace();
            showCustomerError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchCustomers(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadCustomers();
            return;
        }

        customersList.clear();

        String sql =
                "SELECT customer_id, username, name, phone, email, password " +
                "FROM customer " +
                "WHERE username LIKE ? OR name LIKE ? OR phone LIKE ? OR email LIKE ? " +
                "ORDER BY customer_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );

                customersList.add(customer);
            }

            customersTable.setItems(customersList);
            resultSet.close();

            loadAddressComboBoxes();
            loadCartComboBoxes();

            if (customersList.isEmpty()) {
                showCustomerError("No customers found");
            } else {
                showCustomerSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCustomerError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addCustomer(ActionEvent event) {
        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (!validateCustomerFields(username, name, phone, email, password)) {
            return;
        }

        String sql = "INSERT INTO customer (username, name, phone, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, password);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCustomerSuccess("Customer added successfully");
                clearFields();
                loadCustomers();
                loadCarts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCustomerError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateCustomer(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showCustomerError("Please select a customer to update");
            return;
        }

        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (!validateCustomerFields(username, name, phone, email, password)) {
            return;
        }

        String sql =
                "UPDATE customer " +
                "SET username = ?, name = ?, phone = ?, email = ?, password = ? " +
                "WHERE customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setInt(6, selectedCustomer.getCustomerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCustomerSuccess("Customer updated successfully");
                clearFields();
                loadCustomers();
                loadAddresses();
                loadCarts();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCustomerError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteCustomer(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showCustomerError("Please select a customer to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Customer");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this customer? Related addresses, cart, notifications, and reviews may also be deleted.");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM customer WHERE customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCustomerSuccess("Customer deleted successfully");
                clearFields();
                loadCustomers();
                loadAddresses();
                loadCarts();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCustomerError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearFields() {
        customersTable.getSelectionModel().clearSelection();
        usernameField.clear();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        passwordField.clear();
        searchField.clear();
        messageLabel.setText("");
    }


    // ================== LOCATIONS CRUD ==================

    @FXML
    void loadLocations() {
        locationsList.clear();

        String sql = "SELECT location_id, city, street, building FROM location ORDER BY location_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Location location = new Location(
                        resultSet.getInt("location_id"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getString("building")
                );

                locationsList.add(location);
            }

            locationsTable.setItems(locationsList);
            showLocationSuccess("Locations loaded successfully");

            loadAddressComboBoxes();

        } catch (Exception e) {
            e.printStackTrace();
            showLocationError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchLocations(ActionEvent event) {
        String keyword = locationSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadLocations();
            return;
        }

        locationsList.clear();

        String sql =
                "SELECT location_id, city, street, building " +
                "FROM location " +
                "WHERE city LIKE ? OR street LIKE ? OR building LIKE ? " +
                "ORDER BY location_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(
                        resultSet.getInt("location_id"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getString("building")
                );

                locationsList.add(location);
            }

            locationsTable.setItems(locationsList);
            resultSet.close();

            loadAddressComboBoxes();

            if (locationsList.isEmpty()) {
                showLocationError("No locations found");
            } else {
                showLocationSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showLocationError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addLocation(ActionEvent event) {
        String city = cityField.getText().trim();
        String street = streetField.getText().trim();
        String building = buildingField.getText().trim();

        if (!validateLocationFields(city, street)) {
            return;
        }

        String sql = "INSERT INTO location (city, street, building) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city);
            statement.setString(2, street);

            if (building.isEmpty()) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, building);
            }

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showLocationSuccess("Location added successfully");
                clearLocationFields();
                loadLocations();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showLocationError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateLocation(ActionEvent event) {
        Location selectedLocation = locationsTable.getSelectionModel().getSelectedItem();

        if (selectedLocation == null) {
            showLocationError("Please select a location to update");
            return;
        }

        String city = cityField.getText().trim();
        String street = streetField.getText().trim();
        String building = buildingField.getText().trim();

        if (!validateLocationFields(city, street)) {
            return;
        }

        String sql =
                "UPDATE location " +
                "SET city = ?, street = ?, building = ? " +
                "WHERE location_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city);
            statement.setString(2, street);

            if (building.isEmpty()) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, building);
            }

            statement.setInt(4, selectedLocation.getLocationId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showLocationSuccess("Location updated successfully");
                clearLocationFields();
                loadLocations();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showLocationError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteLocation(ActionEvent event) {
        Location selectedLocation = locationsTable.getSelectionModel().getSelectedItem();

        if (selectedLocation == null) {
            showLocationError("Please select a location to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Location");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this location? Related addresses may also be deleted.");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM location WHERE location_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedLocation.getLocationId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showLocationSuccess("Location deleted successfully");
                clearLocationFields();
                loadLocations();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showLocationError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearLocationFields() {
        locationsTable.getSelectionModel().clearSelection();
        cityField.clear();
        streetField.clear();
        buildingField.clear();
        locationSearchField.clear();
        locationMessageLabel.setText("");
    }


    // ================== ADDRESSES CRUD ==================

    @FXML
    void loadAddresses() {
        addressesList.clear();

        String sql =
                "SELECT a.address_id, c.customer_id, c.name AS customer_name, " +
                "l.location_id, CONCAT(l.city, ' / ', l.street, IFNULL(CONCAT(' / ', l.building), '')) AS location_details " +
                "FROM address a " +
                "JOIN customer c ON a.customer_id = c.customer_id " +
                "JOIN location l ON a.location_id = l.location_id " +
                "ORDER BY a.address_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Address address = new Address(
                        resultSet.getInt("address_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("location_id"),
                        resultSet.getString("location_details")
                );

                addressesList.add(address);
            }

            addressesTable.setItems(addressesList);
            showAddressSuccess("Addresses loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showAddressError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchAddresses(ActionEvent event) {
        String keyword = addressSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadAddresses();
            return;
        }

        addressesList.clear();

        String sql =
                "SELECT a.address_id, c.customer_id, c.name AS customer_name, " +
                "l.location_id, CONCAT(l.city, ' / ', l.street, IFNULL(CONCAT(' / ', l.building), '')) AS location_details " +
                "FROM address a " +
                "JOIN customer c ON a.customer_id = c.customer_id " +
                "JOIN location l ON a.location_id = l.location_id " +
                "WHERE c.name LIKE ? OR l.city LIKE ? OR l.street LIKE ? OR l.building LIKE ? " +
                "ORDER BY a.address_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address(
                        resultSet.getInt("address_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("location_id"),
                        resultSet.getString("location_details")
                );

                addressesList.add(address);
            }

            addressesTable.setItems(addressesList);
            resultSet.close();

            if (addressesList.isEmpty()) {
                showAddressError("No addresses found");
            } else {
                showAddressSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAddressError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addAddress(ActionEvent event) {
        Customer selectedCustomer = addressCustomerComboBox.getValue();
        Location selectedLocation = addressLocationComboBox.getValue();

        if (selectedCustomer == null || selectedLocation == null) {
            showAddressError("Please select customer and location");
            return;
        }

        String sql = "INSERT INTO address (customer_id, location_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());
            statement.setInt(2, selectedLocation.getLocationId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showAddressSuccess("Address added successfully");
                clearAddressFields();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAddressError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateAddress(ActionEvent event) {
        Address selectedAddress = addressesTable.getSelectionModel().getSelectedItem();

        if (selectedAddress == null) {
            showAddressError("Please select an address to update");
            return;
        }

        Customer selectedCustomer = addressCustomerComboBox.getValue();
        Location selectedLocation = addressLocationComboBox.getValue();

        if (selectedCustomer == null || selectedLocation == null) {
            showAddressError("Please select customer and location");
            return;
        }

        String sql = "UPDATE address SET customer_id = ?, location_id = ? WHERE address_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());
            statement.setInt(2, selectedLocation.getLocationId());
            statement.setInt(3, selectedAddress.getAddressId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showAddressSuccess("Address updated successfully");
                clearAddressFields();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAddressError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteAddress(ActionEvent event) {
        Address selectedAddress = addressesTable.getSelectionModel().getSelectedItem();

        if (selectedAddress == null) {
            showAddressError("Please select an address to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Address");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this address?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM address WHERE address_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedAddress.getAddressId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showAddressSuccess("Address deleted successfully");
                clearAddressFields();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAddressError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearAddressFields() {
        addressesTable.getSelectionModel().clearSelection();
        addressCustomerComboBox.getSelectionModel().clearSelection();
        addressLocationComboBox.getSelectionModel().clearSelection();
        addressSearchField.clear();
        addressMessageLabel.setText("");
    }

    private void loadAddressComboBoxes() {
        addressCustomerComboBox.setItems(customersList);
        addressLocationComboBox.setItems(locationsList);
    }

    private void selectCustomerInAddressCombo(int customerId) {
        for (Customer customer : customersList) {
            if (customer.getCustomerId() == customerId) {
                addressCustomerComboBox.setValue(customer);
                return;
            }
        }
    }

    private void selectLocationInAddressCombo(int locationId) {
        for (Location location : locationsList) {
            if (location.getLocationId() == locationId) {
                addressLocationComboBox.setValue(location);
                return;
            }
        }
    }



    // ================== CARTS CRUD ==================

    @FXML
    void loadCarts() {
        cartsList.clear();

        String sql =
                "SELECT ca.cart_id, c.customer_id, c.name AS customer_name " +
                "FROM cart ca " +
                "JOIN customer c ON ca.customer_id = c.customer_id " +
                "ORDER BY ca.cart_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CartRecord cart = new CartRecord(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name")
                );

                cartsList.add(cart);
            }

            cartsTable.setItems(cartsList);
            loadCartComboBoxes();
            showCartSuccess("Carts loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showCartError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchCarts(ActionEvent event) {
        String keyword = cartSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadCarts();
            return;
        }

        cartsList.clear();

        String sql =
                "SELECT ca.cart_id, c.customer_id, c.name AS customer_name " +
                "FROM cart ca " +
                "JOIN customer c ON ca.customer_id = c.customer_id " +
                "WHERE CAST(ca.cart_id AS CHAR) LIKE ? " +
                "OR CAST(c.customer_id AS CHAR) LIKE ? " +
                "OR c.name LIKE ? " +
                "ORDER BY ca.cart_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartRecord cart = new CartRecord(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name")
                );

                cartsList.add(cart);
            }

            cartsTable.setItems(cartsList);
            loadCartComboBoxes();
            resultSet.close();

            if (cartsList.isEmpty()) {
                showCartError("No carts found");
            } else {
                showCartSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCartError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addCart(ActionEvent event) {
        Customer selectedCustomer = cartCustomerComboBox.getValue();

        if (selectedCustomer == null) {
            showCartError("Please select a customer");
            return;
        }

        String sql = "INSERT INTO cart (customer_id) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCartSuccess("Cart added successfully");
                clearCartFields();
                loadCarts();
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                showCartError("This customer already has a cart");
            } else {
                showCartError("Add failed: " + e.getMessage());
            }
        }
    }

    @FXML
    void deleteCart(ActionEvent event) {
        CartRecord selectedCart = cartsTable.getSelectionModel().getSelectedItem();

        if (selectedCart == null) {
            showCartError("Please select a cart to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Cart");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this cart and all its items?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteItems = connection.prepareStatement("DELETE FROM cart_item WHERE cart_id = ?");
                 PreparedStatement deleteCart = connection.prepareStatement("DELETE FROM cart WHERE cart_id = ?")) {

                deleteItems.setInt(1, selectedCart.getCartId());
                deleteItems.executeUpdate();

                deleteCart.setInt(1, selectedCart.getCartId());
                int rows = deleteCart.executeUpdate();

                connection.commit();

                if (rows > 0) {
                    showCartSuccess("Cart deleted successfully");
                    clearCartFields();
                    loadCarts();
                    loadCartItems();
                }
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCartError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearCartFields() {
        cartsTable.getSelectionModel().clearSelection();
        cartCustomerComboBox.getSelectionModel().clearSelection();
        cartSearchField.clear();
        cartMessageLabel.setText("");
    }


    // ================== CART ITEMS CRUD ==================

    @FXML
    void loadCartItems() {
        cartItemsList.clear();

        String sql =
                "SELECT ci.cart_item_id, ci.cart_id, mi.item_id, mi.name AS item_name, " +
                "r.name AS restaurant_name, mi.price, ci.quantity, (ci.quantity * mi.price) AS subtotal " +
                "FROM cart_item ci " +
                "JOIN menu_item mi ON ci.item_id = mi.item_id " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "ORDER BY ci.cart_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CartItemRecord item = new CartItemRecord(
                        resultSet.getInt("cart_item_id"),
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("subtotal")
                );

                cartItemsList.add(item);
            }

            cartItemsTable.setItems(cartItemsList);
            showCartItemSuccess("Cart items loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showCartItemError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchCartItems(ActionEvent event) {
        String keyword = cartItemSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadCartItems();
            return;
        }

        cartItemsList.clear();

        String sql =
                "SELECT ci.cart_item_id, ci.cart_id, mi.item_id, mi.name AS item_name, " +
                "r.name AS restaurant_name, mi.price, ci.quantity, (ci.quantity * mi.price) AS subtotal " +
                "FROM cart_item ci " +
                "JOIN menu_item mi ON ci.item_id = mi.item_id " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "WHERE CAST(ci.cart_id AS CHAR) LIKE ? " +
                "OR CAST(mi.item_id AS CHAR) LIKE ? " +
                "OR mi.name LIKE ? " +
                "OR r.name LIKE ? " +
                "OR CAST(ci.quantity AS CHAR) LIKE ? " +
                "ORDER BY ci.cart_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);
            statement.setString(5, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartItemRecord item = new CartItemRecord(
                        resultSet.getInt("cart_item_id"),
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("subtotal")
                );

                cartItemsList.add(item);
            }

            cartItemsTable.setItems(cartItemsList);
            resultSet.close();

            if (cartItemsList.isEmpty()) {
                showCartItemError("No cart items found");
            } else {
                showCartItemSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCartItemError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void calculateCartItemSubtotal(ActionEvent event) {
        OrderMenuItem selectedMenuItem = cartItemMenuItemComboBox.getValue();
        String quantityText = cartItemQuantityField.getText().trim();

        if (selectedMenuItem == null || quantityText.isEmpty()) {
            showCartItemError("Please select menu item and enter quantity");
            return;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showCartItemError("Quantity must be a number");
            return;
        }

        if (quantity <= 0) {
            showCartItemError("Quantity must be greater than zero");
            return;
        }

        double subtotal = quantity * selectedMenuItem.getPrice();
        cartItemSubtotalField.setText(String.valueOf(subtotal));
        showCartItemSuccess("Subtotal calculated");
    }

    @FXML
    void addCartItem(ActionEvent event) {
        CartRecord selectedCart = cartItemCartComboBox.getValue();
        OrderMenuItem selectedMenuItem = cartItemMenuItemComboBox.getValue();
        String quantityText = cartItemQuantityField.getText().trim();

        if (!validateCartItemFields(selectedCart, selectedMenuItem, quantityText)) {
            return;
        }

        int quantity = Integer.parseInt(quantityText);

        String sql = "INSERT INTO cart_item (cart_id, item_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCart.getCartId());
            statement.setInt(2, selectedMenuItem.getItemId());
            statement.setInt(3, quantity);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCartItemSuccess("Cart item added successfully");
                clearCartItemFields();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCartItemError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateCartItem(ActionEvent event) {
        CartItemRecord selectedCartItem = cartItemsTable.getSelectionModel().getSelectedItem();

        if (selectedCartItem == null) {
            showCartItemError("Please select a cart item to update");
            return;
        }

        CartRecord selectedCart = cartItemCartComboBox.getValue();
        OrderMenuItem selectedMenuItem = cartItemMenuItemComboBox.getValue();
        String quantityText = cartItemQuantityField.getText().trim();

        if (!validateCartItemFields(selectedCart, selectedMenuItem, quantityText)) {
            return;
        }

        int quantity = Integer.parseInt(quantityText);

        String sql =
                "UPDATE cart_item " +
                "SET cart_id = ?, item_id = ?, quantity = ? " +
                "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCart.getCartId());
            statement.setInt(2, selectedMenuItem.getItemId());
            statement.setInt(3, quantity);
            statement.setInt(4, selectedCartItem.getCartItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCartItemSuccess("Cart item updated successfully");
                clearCartItemFields();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCartItemError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteCartItem(ActionEvent event) {
        CartItemRecord selectedCartItem = cartItemsTable.getSelectionModel().getSelectedItem();

        if (selectedCartItem == null) {
            showCartItemError("Please select a cart item to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Cart Item");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this cart item?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCartItem.getCartItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCartItemSuccess("Cart item deleted successfully");
                clearCartItemFields();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCartItemError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearCartItemFields() {
        cartItemsTable.getSelectionModel().clearSelection();
        cartItemCartComboBox.getSelectionModel().clearSelection();
        cartItemMenuItemComboBox.getSelectionModel().clearSelection();
        cartItemQuantityField.clear();
        cartItemSubtotalField.clear();
        cartItemSearchField.clear();
        cartItemMessageLabel.setText("");
    }

    private void loadCartComboBoxes() {
        if (cartCustomerComboBox != null) {
            cartCustomerComboBox.setItems(customersList);
        }

        if (cartItemCartComboBox != null) {
            cartItemCartComboBox.setItems(cartsList);
        }

        if (cartItemMenuItemComboBox != null) {
            cartItemMenuItemComboBox.setItems(cartMenuItemsList);
        }
    }

    private void loadCartMenuItemComboBox() {
        cartMenuItemsList.clear();

        String sql =
                "SELECT mi.item_id, mi.name AS item_name, mi.price, r.name AS restaurant_name " +
                "FROM menu_item mi " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "WHERE mi.availability = 1 " +
                "ORDER BY mi.item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                OrderMenuItem item = new OrderMenuItem(
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("price")
                );

                cartMenuItemsList.add(item);
            }

            loadCartComboBoxes();

        } catch (Exception e) {
            e.printStackTrace();
            showCartItemError("Menu items load failed: " + e.getMessage());
        }
    }

    private void selectCustomerInCartCombo(int customerId) {
        for (Customer customer : customersList) {
            if (customer.getCustomerId() == customerId) {
                cartCustomerComboBox.setValue(customer);
                return;
            }
        }
    }

    private void selectCartInCartItemCombo(int cartId) {
        for (CartRecord cart : cartsList) {
            if (cart.getCartId() == cartId) {
                cartItemCartComboBox.setValue(cart);
                return;
            }
        }
    }

    private void selectMenuItemInCartItemCombo(int itemId) {
        for (OrderMenuItem item : cartMenuItemsList) {
            if (item.getItemId() == itemId) {
                cartItemMenuItemComboBox.setValue(item);
                return;
            }
        }
    }

    private boolean validateCartItemFields(CartRecord selectedCart,
                                           OrderMenuItem selectedMenuItem,
                                           String quantityText) {

        if (selectedCart == null || selectedMenuItem == null || quantityText.isEmpty()) {
            showCartItemError("Cart, menu item, and quantity are required");
            return false;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showCartItemError("Quantity must be a number");
            return false;
        }

        if (quantity <= 0) {
            showCartItemError("Quantity must be greater than zero");
            return false;
        }

        return true;
    }


    // ================== NOTIFICATIONS CRUD ==================

    @FXML
    void loadNotifications() {
        notificationsList.clear();

        String sql =
                "SELECT n.notification_id, c.customer_id, c.name AS customer_name, " +
                "n.message, n.`date` AS notification_date " +
                "FROM notification n " +
                "JOIN customer c ON n.customer_id = c.customer_id " +
                "ORDER BY n.notification_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                NotificationRecord notification = new NotificationRecord(
                        resultSet.getInt("notification_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("message"),
                        resultSet.getString("notification_date")
                );

                notificationsList.add(notification);
            }

            notificationsTable.setItems(notificationsList);
            showNotificationSuccess("Notifications loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showNotificationError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchNotifications(ActionEvent event) {
        String keyword = notificationSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadNotifications();
            return;
        }

        notificationsList.clear();

        String sql =
                "SELECT n.notification_id, c.customer_id, c.name AS customer_name, " +
                "n.message, n.`date` AS notification_date " +
                "FROM notification n " +
                "JOIN customer c ON n.customer_id = c.customer_id " +
                "WHERE CAST(n.notification_id AS CHAR) LIKE ? " +
                "OR CAST(c.customer_id AS CHAR) LIKE ? " +
                "OR c.name LIKE ? " +
                "OR n.message LIKE ? " +
                "OR CAST(n.`date` AS CHAR) LIKE ? " +
                "ORDER BY n.notification_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);
            statement.setString(5, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                NotificationRecord notification = new NotificationRecord(
                        resultSet.getInt("notification_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("message"),
                        resultSet.getString("notification_date")
                );

                notificationsList.add(notification);
            }

            notificationsTable.setItems(notificationsList);
            resultSet.close();

            if (notificationsList.isEmpty()) {
                showNotificationError("No notifications found");
            } else {
                showNotificationSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showNotificationError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addNotification(ActionEvent event) {
        Customer selectedCustomer = notificationCustomerComboBox.getValue();
        String message = notificationMessageArea.getText().trim();

        if (!validateNotificationFields(selectedCustomer, message)) {
            return;
        }

        String sql =
                "INSERT INTO notification (customer_id, message, `date`) " +
                "VALUES (?, ?, NOW())";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());
            statement.setString(2, message);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showNotificationSuccess("Notification added successfully");
                clearNotificationFields();
                loadNotifications();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showNotificationError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateNotification(ActionEvent event) {
        NotificationRecord selectedNotification = notificationsTable.getSelectionModel().getSelectedItem();

        if (selectedNotification == null) {
            showNotificationError("Please select a notification to update");
            return;
        }

        Customer selectedCustomer = notificationCustomerComboBox.getValue();
        String message = notificationMessageArea.getText().trim();

        if (!validateNotificationFields(selectedCustomer, message)) {
            return;
        }

        String sql =
                "UPDATE notification " +
                "SET customer_id = ?, message = ? " +
                "WHERE notification_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());
            statement.setString(2, message);
            statement.setInt(3, selectedNotification.getNotificationId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showNotificationSuccess("Notification updated successfully");
                clearNotificationFields();
                loadNotifications();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showNotificationError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteNotification(ActionEvent event) {
        NotificationRecord selectedNotification = notificationsTable.getSelectionModel().getSelectedItem();

        if (selectedNotification == null) {
            showNotificationError("Please select a notification to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Notification");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this notification?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM notification WHERE notification_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedNotification.getNotificationId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showNotificationSuccess("Notification deleted successfully");
                clearNotificationFields();
                loadNotifications();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showNotificationError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearNotificationFields() {
        notificationsTable.getSelectionModel().clearSelection();
        notificationCustomerComboBox.getSelectionModel().clearSelection();
        notificationMessageArea.clear();
        notificationDateField.clear();
        notificationSearchField.clear();
        notificationMessageLabel.setText("");
    }

    private void loadNotificationComboBox() {
        notificationCustomerComboBox.setItems(customersList);
    }

    private void selectCustomerInNotificationCombo(int customerId) {
        for (Customer customer : customersList) {
            if (customer.getCustomerId() == customerId) {
                notificationCustomerComboBox.setValue(customer);
                return;
            }
        }
    }


    // ================== NAVIGATION ==================

    @FXML
    void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminDashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 750);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin Dashboard");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showCustomerError("Cannot open dashboard: " + e.getMessage());
        }
    }


    // ================== VALIDATION ==================

    private boolean validateCustomerFields(String username, String name, String phone, String email, String password) {
        if (username.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showCustomerError("Please fill all customer fields");
            return false;
        }

        if (username.length() < 3) {
            showCustomerError("Username must be at least 3 characters");
            return false;
        }

        if (!phone.matches("[0-9]+")) {
            showCustomerError("Phone must contain numbers only");
            return false;
        }

        if (phone.length() < 7) {
            showCustomerError("Phone number is too short");
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showCustomerError("Please enter a valid email");
            return false;
        }

        if (password.length() < 4) {
            showCustomerError("Password must be at least 4 characters");
            return false;
        }

        return true;
    }

    private boolean validateNotificationFields(Customer selectedCustomer, String message) {
        if (selectedCustomer == null || message.isEmpty()) {
            showNotificationError("Customer and message are required");
            return false;
        }

        return true;
    }

    private boolean validateLocationFields(String city, String street) {
        if (city.isEmpty() || street.isEmpty()) {
            showLocationError("City and street are required");
            return false;
        }

        return true;
    }


    // ================== MESSAGES ==================

    private void showCustomerError(String message) {
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setText(message);
    }

    private void showCustomerSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setText(message);
    }

    private void showLocationError(String message) {
        locationMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        locationMessageLabel.setText(message);
    }

    private void showLocationSuccess(String message) {
        locationMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        locationMessageLabel.setText(message);
    }

    private void showAddressError(String message) {
        addressMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        addressMessageLabel.setText(message);
    }

    private void showAddressSuccess(String message) {
        addressMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        addressMessageLabel.setText(message);
    }

    private void showCartError(String message) {
        cartMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        cartMessageLabel.setText(message);
    }

    private void showCartSuccess(String message) {
        cartMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        cartMessageLabel.setText(message);
    }

    private void showCartItemError(String message) {
        cartItemMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        cartItemMessageLabel.setText(message);
    }

    private void showCartItemSuccess(String message) {
        cartItemMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        cartItemMessageLabel.setText(message);
    }

    private void showNotificationError(String message) {
        notificationMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        notificationMessageLabel.setText(message);
    }

    private void showNotificationSuccess(String message) {
        notificationMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        notificationMessageLabel.setText(message);
    }
}