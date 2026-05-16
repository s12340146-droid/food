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
import models.CustomerMenuItemView;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerBrowseRestaurantsController {

    @FXML
    private TableView<CustomerMenuItemView> menuItemsTable;

    @FXML
    private TableColumn<CustomerMenuItemView, Integer> itemIdColumn;

    @FXML
    private TableColumn<CustomerMenuItemView, String> itemNameColumn;

    @FXML
    private TableColumn<CustomerMenuItemView, String> descriptionColumn;

    @FXML
    private TableColumn<CustomerMenuItemView, Double> priceColumn;

    @FXML
    private TableColumn<CustomerMenuItemView, String> restaurantColumn;

    @FXML
    private TableColumn<CustomerMenuItemView, String> categoryColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField selectedItemField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label messageLabel;

    private final ObservableList<CustomerMenuItemView> menuItemsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        loadMenuItems();

        menuItemsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedItem) -> {
                    if (selectedItem != null) {
                        selectedItemField.setText(
                                selectedItem.getItemName() + " - " +
                                        selectedItem.getRestaurantName() + " - " +
                                        selectedItem.getPrice()
                        );
                    }
                }
        );
    }

    @FXML
    void loadMenuItems() {
        menuItemsList.clear();

        String sql =
                "SELECT mi.item_id, mi.name AS item_name, mi.description, mi.price, mi.availability, " +
                        "r.name AS restaurant_name, c.category_name AS category_name " +
                        "FROM menu_item mi " +
                        "JOIN menu m ON mi.menu_id = m.menu_id " +
                        "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                        "JOIN category c ON mi.category_id = c.category_id " +
                        "WHERE mi.availability = 1 " +
                        "ORDER BY r.name, mi.name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CustomerMenuItemView item = new CustomerMenuItemView(
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("availability"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("category_name")
                );

                menuItemsList.add(item);
            }

            menuItemsTable.setItems(menuItemsList);

            if (menuItemsList.isEmpty()) {
                showError("No available menu items found");
            } else {
                showSuccess("Menu items loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchMenuItems(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadMenuItems();
            return;
        }

        menuItemsList.clear();

        String sql =
                "SELECT mi.item_id, mi.name AS item_name, mi.description, mi.price, mi.availability, " +
                        "r.name AS restaurant_name, c.category_name AS category_name " +
                        "FROM menu_item mi " +
                        "JOIN menu m ON mi.menu_id = m.menu_id " +
                        "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                        "JOIN category c ON mi.category_id = c.category_id " +
                        "WHERE mi.availability = 1 AND " +
                        "(mi.name LIKE ? OR mi.description LIKE ? OR r.name LIKE ? OR c.category_name LIKE ?) " +
                        "ORDER BY r.name, mi.name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerMenuItemView item = new CustomerMenuItemView(
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("availability"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("category_name")
                );

                menuItemsList.add(item);
            }

            menuItemsTable.setItems(menuItemsList);
            resultSet.close();

            if (menuItemsList.isEmpty()) {
                showError("No menu items found");
            } else {
                showSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addToCart(ActionEvent event) {
        CustomerMenuItemView selectedItem = menuItemsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showError("Please select a menu item");
            return;
        }

        String quantityText = quantityField.getText().trim();

        if (quantityText.isEmpty()) {
            showError("Please enter quantity");
            return;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showError("Quantity must be a number");
            return;
        }

        if (quantity <= 0) {
            showError("Quantity must be greater than zero");
            return;
        }

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {

            int cartId = getOrCreateCart(connection, customerId);

            if (cartItemExists(connection, cartId, selectedItem.getItemId())) {
                updateCartItemQuantity(connection, cartId, selectedItem.getItemId(), quantity);
            } else {
                insertCartItem(connection, cartId, selectedItem.getItemId(), quantity);
            }

            showSuccess("Item added to cart successfully");
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Add to cart failed: " + e.getMessage());
        }
    }

    private int getOrCreateCart(Connection connection, int customerId) throws Exception {
        String selectSql = "SELECT cart_id FROM cart WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int cartId = resultSet.getInt("cart_id");
                resultSet.close();
                return cartId;
            }

            resultSet.close();
        }

        String insertSql = "INSERT INTO cart (customer_id) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                int cartId = keys.getInt(1);
                keys.close();
                return cartId;
            }

            keys.close();
        }

        throw new Exception("Could not create cart");
    }

    private boolean cartItemExists(Connection connection, int cartId, int itemId) throws Exception {
        String sql = "SELECT cart_item_id FROM cart_item WHERE cart_id = ? AND item_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartId);
            statement.setInt(2, itemId);

            ResultSet resultSet = statement.executeQuery();
            boolean exists = resultSet.next();
            resultSet.close();

            return exists;
        }
    }

    private void updateCartItemQuantity(Connection connection, int cartId, int itemId, int quantityToAdd) throws Exception {
        String sql =
                "UPDATE cart_item " +
                        "SET quantity = quantity + ? " +
                        "WHERE cart_id = ? AND item_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantityToAdd);
            statement.setInt(2, cartId);
            statement.setInt(3, itemId);
            statement.executeUpdate();
        }
    }

    private void insertCartItem(Connection connection, int cartId, int itemId, int quantity) throws Exception {
        String sql =
                "INSERT INTO cart_item (cart_id, item_id, quantity) " +
                        "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartId);
            statement.setInt(2, itemId);
            statement.setInt(3, quantity);
            statement.executeUpdate();
        }
    }

    @FXML
    void clearFields() {
        menuItemsTable.getSelectionModel().clearSelection();
        selectedItemField.clear();
        quantityField.clear();
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

    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setText(message);
    }

    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setText(message);
    }
}