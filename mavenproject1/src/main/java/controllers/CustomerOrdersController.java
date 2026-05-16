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
import models.CustomerOrderItemView;
import models.CustomerOrderView;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerOrdersController {

    @FXML
    private TableView<CustomerOrderView> ordersTable;

    @FXML
    private TableColumn<CustomerOrderView, Integer> orderIdColumn;

    @FXML
    private TableColumn<CustomerOrderView, String> restaurantColumn;

    @FXML
    private TableColumn<CustomerOrderView, String> orderDateColumn;

    @FXML
    private TableColumn<CustomerOrderView, String> statusColumn;

    @FXML
    private TableColumn<CustomerOrderView, Double> totalPriceColumn;

    @FXML
    private TableView<CustomerOrderItemView> orderItemsTable;

    @FXML
    private TableColumn<CustomerOrderItemView, Integer> orderItemIdColumn;

    @FXML
    private TableColumn<CustomerOrderItemView, Integer> itemIdColumn;

    @FXML
    private TableColumn<CustomerOrderItemView, String> itemNameColumn;

    @FXML
    private TableColumn<CustomerOrderItemView, Double> itemPriceColumn;

    @FXML
    private TableColumn<CustomerOrderItemView, Integer> quantityColumn;

    @FXML
    private TableColumn<CustomerOrderItemView, Double> subtotalColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Label messageLabel;

    private final ObservableList<CustomerOrderView> ordersList = FXCollections.observableArrayList();
    private final ObservableList<CustomerOrderItemView> orderItemsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        loadOrders();

        ordersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedOrder) -> {
                    if (selectedOrder != null) {
                        loadOrderItems(selectedOrder.getOrderId());
                    }
                }
        );
    }

    @FXML
    void loadOrders() {
        ordersList.clear();
        orderItemsList.clear();
        orderItemsTable.setItems(orderItemsList);

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT o.order_id, r.name AS restaurant_name, o.order_date, o.status, o.total_price " +
                "FROM `order` o " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "WHERE o.customer_id = ? " +
                "ORDER BY o.order_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerOrderView order = new CustomerOrderView(
                        resultSet.getInt("order_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("order_date"),
                        resultSet.getString("status"),
                        resultSet.getDouble("total_price")
                );

                ordersList.add(order);
            }

            ordersTable.setItems(ordersList);
            resultSet.close();

            if (ordersList.isEmpty()) {
                showError("You do not have any orders yet");
            } else {
                showSuccess("Orders loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchOrders(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadOrders();
            return;
        }

        ordersList.clear();
        orderItemsList.clear();
        orderItemsTable.setItems(orderItemsList);

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT o.order_id, r.name AS restaurant_name, o.order_date, o.status, o.total_price " +
                "FROM `order` o " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "WHERE o.customer_id = ? AND " +
                "(r.name LIKE ? OR o.status LIKE ? OR CAST(o.order_date AS CHAR) LIKE ? OR CAST(o.total_price AS CHAR) LIKE ?) " +
                "ORDER BY o.order_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setInt(1, customerId);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);
            statement.setString(5, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerOrderView order = new CustomerOrderView(
                        resultSet.getInt("order_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("order_date"),
                        resultSet.getString("status"),
                        resultSet.getDouble("total_price")
                );

                ordersList.add(order);
            }

            ordersTable.setItems(ordersList);
            resultSet.close();

            if (ordersList.isEmpty()) {
                showError("No orders found");
            } else {
                showSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Search failed: " + e.getMessage());
        }
    }

    private void loadOrderItems(int orderId) {
        orderItemsList.clear();

        String sql =
                "SELECT oi.order_item_id, mi.item_id, mi.name AS item_name, " +
                "mi.price AS item_price, oi.quantity, oi.subtotal " +
                "FROM order_item oi " +
                "JOIN menu_item mi ON oi.item_id = mi.item_id " +
                "WHERE oi.order_id = ? " +
                "ORDER BY oi.order_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerOrderItemView item = new CustomerOrderItemView(
                        resultSet.getInt("order_item_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getDouble("item_price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("subtotal")
                );

                orderItemsList.add(item);
            }

            orderItemsTable.setItems(orderItemsList);
            resultSet.close();

            if (orderItemsList.isEmpty()) {
                showError("No items found for selected order");
            } else {
                showSuccess("Order items loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Order items load failed: " + e.getMessage());
        }
    }

    @FXML
    void clearFields() {
        ordersTable.getSelectionModel().clearSelection();
        orderItemsList.clear();
        orderItemsTable.setItems(orderItemsList);
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