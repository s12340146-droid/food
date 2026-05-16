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
import models.CustomerAddressView;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerAddressesController {

    @FXML
    private TableView<CustomerAddressView> addressesTable;

    @FXML
    private TableColumn<CustomerAddressView, Integer> addressIdColumn;

    @FXML
    private TableColumn<CustomerAddressView, Integer> locationIdColumn;

    @FXML
    private TableColumn<CustomerAddressView, String> cityColumn;

    @FXML
    private TableColumn<CustomerAddressView, String> streetColumn;

    @FXML
    private TableColumn<CustomerAddressView, String> buildingColumn;

    @FXML
    private TableColumn<CustomerAddressView, String> detailsColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField buildingField;

    @FXML
    private Label messageLabel;

    private final ObservableList<CustomerAddressView> addressesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        addressIdColumn.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        locationIdColumn.setCellValueFactory(new PropertyValueFactory<>("locationId"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("building"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("locationDetails"));

        loadAddresses();

        addressesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedAddress) -> {
                    if (selectedAddress != null) {
                        cityField.setText(selectedAddress.getCity());
                        streetField.setText(selectedAddress.getStreet());
                        buildingField.setText(selectedAddress.getBuilding());
                    }
                }
        );
    }

    @FXML
    void loadAddresses() {
        addressesList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT a.address_id, l.location_id, l.city, l.street, l.building, " +
                "CONCAT(l.city, ' / ', l.street, IFNULL(CONCAT(' / ', l.building), '')) AS location_details " +
                "FROM address a " +
                "JOIN location l ON a.location_id = l.location_id " +
                "WHERE a.customer_id = ? " +
                "ORDER BY a.address_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerAddressView address = new CustomerAddressView(
                        resultSet.getInt("address_id"),
                        resultSet.getInt("location_id"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getString("building"),
                        resultSet.getString("location_details")
                );

                addressesList.add(address);
            }

            addressesTable.setItems(addressesList);
            resultSet.close();

            if (addressesList.isEmpty()) {
                showError("No addresses found");
            } else {
                showSuccess("Addresses loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchAddresses(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadAddresses();
            return;
        }

        addressesList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT a.address_id, l.location_id, l.city, l.street, l.building, " +
                "CONCAT(l.city, ' / ', l.street, IFNULL(CONCAT(' / ', l.building), '')) AS location_details " +
                "FROM address a " +
                "JOIN location l ON a.location_id = l.location_id " +
                "WHERE a.customer_id = ? AND " +
                "(l.city LIKE ? OR l.street LIKE ? OR l.building LIKE ?) " +
                "ORDER BY a.address_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setInt(1, customerId);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerAddressView address = new CustomerAddressView(
                        resultSet.getInt("address_id"),
                        resultSet.getInt("location_id"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getString("building"),
                        resultSet.getString("location_details")
                );

                addressesList.add(address);
            }

            addressesTable.setItems(addressesList);
            resultSet.close();

            if (addressesList.isEmpty()) {
                showError("No addresses found");
            } else {
                showSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addAddress(ActionEvent event) {
        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String city = cityField.getText().trim();
        String street = streetField.getText().trim();
        String building = buildingField.getText().trim();

        if (!validateAddressFields(city, street)) {
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                int locationId = createLocation(connection, city, street, building);
                createAddress(connection, customerId, locationId);

                connection.commit();

                showSuccess("Address added successfully");
                clearFields();
                loadAddresses();

            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateAddress(ActionEvent event) {
        CustomerAddressView selectedAddress = addressesTable.getSelectionModel().getSelectedItem();

        if (selectedAddress == null) {
            showError("Please select an address to update");
            return;
        }

        String city = cityField.getText().trim();
        String street = streetField.getText().trim();
        String building = buildingField.getText().trim();

        if (!validateAddressFields(city, street)) {
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

            statement.setInt(4, selectedAddress.getLocationId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Address updated successfully");
                clearFields();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteAddress(ActionEvent event) {
        CustomerAddressView selectedAddress = addressesTable.getSelectionModel().getSelectedItem();

        if (selectedAddress == null) {
            showError("Please select an address to delete");
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
                showSuccess("Address deleted successfully");
                clearFields();
                loadAddresses();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Delete failed: " + e.getMessage());
        }
    }

    private int createLocation(Connection connection, String city, String street, String building) throws Exception {
        String sql =
                "INSERT INTO location (city, street, building) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, city);
            statement.setString(2, street);

            if (building.isEmpty()) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, building);
            }

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                int locationId = keys.getInt(1);
                keys.close();
                return locationId;
            }

            keys.close();
        }

        throw new Exception("Could not create location");
    }

    private void createAddress(Connection connection, int customerId, int locationId) throws Exception {
        String sql =
                "INSERT INTO address (customer_id, location_id) " +
                "VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.setInt(2, locationId);
            statement.executeUpdate();
        }
    }

    @FXML
    void clearFields() {
        addressesTable.getSelectionModel().clearSelection();
        cityField.clear();
        streetField.clear();
        buildingField.clear();
        searchField.clear();
        messageLabel.setText("");
    }

    @FXML
    void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerDashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 750);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Customer Dashboard");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Cannot return to dashboard: " + e.getMessage());
        }
    }

    private boolean validateAddressFields(String city, String street) {
        if (city.isEmpty() || street.isEmpty()) {
            showError("City and street are required");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setText(message);
    }

    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setText(message);
    }
}