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
import models.CustomerCartItemView;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerCartController {

    @FXML
    private TableView<CustomerCartItemView> cartItemsTable;

    @FXML
    private TableColumn<CustomerCartItemView, Integer> cartItemIdColumn;

    @FXML
    private TableColumn<CustomerCartItemView, Integer> itemIdColumn;

    @FXML
    private TableColumn<CustomerCartItemView, String> itemNameColumn;

    @FXML
    private TableColumn<CustomerCartItemView, String> restaurantColumn;

    @FXML
    private TableColumn<CustomerCartItemView, Double> priceColumn;

    @FXML
    private TableColumn<CustomerCartItemView, Integer> quantityColumn;

    @FXML
    private TableColumn<CustomerCartItemView, Double> subtotalColumn;

    @FXML
    private TextField selectedItemField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label totalLabel;

    @FXML
    private Label messageLabel;

    private final ObservableList<CustomerCartItemView> cartItemsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cartItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartItemId"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        loadCartItems();

        cartItemsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedItem) -> {
                    if (selectedItem != null) {
                        selectedItemField.setText(selectedItem.getItemName());
                        quantityField.setText(String.valueOf(selectedItem.getQuantity()));
                    }
                }
        );
    }

    @FXML
    void loadCartItems() {
        cartItemsList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT ci.cart_item_id, c.cart_id, mi.item_id, mi.name AS item_name, " +
                "r.name AS restaurant_name, mi.price, ci.quantity, (mi.price * ci.quantity) AS subtotal " +
                "FROM cart c " +
                "JOIN cart_item ci ON c.cart_id = ci.cart_id " +
                "JOIN menu_item mi ON ci.item_id = mi.item_id " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "WHERE c.customer_id = ? " +
                "ORDER BY ci.cart_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            double total = 0;

            while (resultSet.next()) {
                double subtotal = resultSet.getDouble("subtotal");
                total += subtotal;

                CustomerCartItemView item = new CustomerCartItemView(
                        resultSet.getInt("cart_item_id"),
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        subtotal
                );

                cartItemsList.add(item);
            }

            cartItemsTable.setItems(cartItemsList);
            totalLabel.setText("Total: " + total);
            resultSet.close();

            if (cartItemsList.isEmpty()) {
                showError("Your cart is empty");
            } else {
                showSuccess("Cart loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void updateQuantity(ActionEvent event) {
        CustomerCartItemView selectedItem = cartItemsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showError("Please select an item");
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

        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setInt(2, selectedItem.getCartItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Quantity updated successfully");
                clearFields();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void removeItem(ActionEvent event) {
        CustomerCartItemView selectedItem = cartItemsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showError("Please select an item");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Remove Item");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to remove this item from your cart?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedItem.getCartItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Item removed successfully");
                clearFields();
                loadCartItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Remove failed: " + e.getMessage());
        }
    }

    @FXML
    void clearCart(ActionEvent event) {
        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Clear Cart");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to clear your cart?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql =
                "DELETE ci FROM cart_item ci " +
                "JOIN cart c ON ci.cart_id = c.cart_id " +
                "WHERE c.customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            statement.executeUpdate();

            showSuccess("Cart cleared successfully");
            clearFields();
            loadCartItems();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Clear cart failed: " + e.getMessage());
        }
    }

    @FXML
    void placeOrder(ActionEvent event) {
        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        if (cartItemsList.isEmpty()) {
            showError("Your cart is empty");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                int restaurantId = getRestaurantIdFromCart(connection, customerId);
                double total = calculateCartTotal(connection, customerId);

                int orderId = createOrder(connection, customerId, restaurantId, total);
                copyCartItemsToOrderItems(connection, customerId, orderId);
                clearCartItemsOnly(connection, customerId);

                connection.commit();

                showSuccess("Order placed successfully. Order ID: " + orderId);
                clearFields();
                loadCartItems();

            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Place order failed: " + e.getMessage());
        }
    }

    private int getRestaurantIdFromCart(Connection connection, int customerId) throws Exception {
        String sql =
                "SELECT m.restaurant_id " +
                "FROM cart c " +
                "JOIN cart_item ci ON c.cart_id = ci.cart_id " +
                "JOIN menu_item mi ON ci.item_id = mi.item_id " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "WHERE c.customer_id = ? " +
                "LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int restaurantId = resultSet.getInt("restaurant_id");
                resultSet.close();
                return restaurantId;
            }

            resultSet.close();
        }

        throw new Exception("No restaurant found for cart items");
    }

    private double calculateCartTotal(Connection connection, int customerId) throws Exception {
        String sql =
                "SELECT IFNULL(SUM(mi.price * ci.quantity), 0) AS total " +
                "FROM cart c " +
                "JOIN cart_item ci ON c.cart_id = ci.cart_id " +
                "JOIN menu_item mi ON ci.item_id = mi.item_id " +
                "WHERE c.customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double total = resultSet.getDouble("total");
                resultSet.close();
                return total;
            }

            resultSet.close();
        }

        return 0;
    }

    private int createOrder(Connection connection, int customerId, int restaurantId, double total) throws Exception {
        String sql =
                "INSERT INTO `order` (customer_id, restaurant_id, order_date, status, total_price) " +
                "VALUES (?, ?, NOW(), 'Pending', ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, customerId);
            statement.setInt(2, restaurantId);
            statement.setDouble(3, total);

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                int orderId = keys.getInt(1);
                keys.close();
                return orderId;
            }

            keys.close();
        }

        throw new Exception("Could not create order");
    }

    private void copyCartItemsToOrderItems(Connection connection, int customerId, int orderId) throws Exception {
        String sql =
                "INSERT INTO order_item (order_id, item_id, quantity, subtotal) " +
                "SELECT ?, ci.item_id, ci.quantity, (mi.price * ci.quantity) " +
                "FROM cart c " +
                "JOIN cart_item ci ON c.cart_id = ci.cart_id " +
                "JOIN menu_item mi ON ci.item_id = mi.item_id " +
                "WHERE c.customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, customerId);
            statement.executeUpdate();
        }
    }

    private void clearCartItemsOnly(Connection connection, int customerId) throws Exception {
        String sql =
                "DELETE ci FROM cart_item ci " +
                "JOIN cart c ON ci.cart_id = c.cart_id " +
                "WHERE c.customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }

    @FXML
    void clearFields() {
        cartItemsTable.getSelectionModel().clearSelection();
        selectedItemField.clear();
        quantityField.clear();
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