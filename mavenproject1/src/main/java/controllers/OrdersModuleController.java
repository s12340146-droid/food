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
import models.Order;
import models.OrderCustomer;
import models.Restaurant;
import models.OrderItem;
import models.OrderMenuItem;
import models.Payment;
import models.Driver;
import models.Delivery;
import models.Discount;
import models.OrderDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrdersModuleController {

    // ================== ORDERS SECTION ==================

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, Integer> orderCustomerIdColumn;

    @FXML
    private TableColumn<Order, String> orderCustomerNameColumn;

    @FXML
    private TableColumn<Order, Integer> orderRestaurantIdColumn;

    @FXML
    private TableColumn<Order, String> orderRestaurantNameColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, Double> orderTotalPriceColumn;

    @FXML
    private TextField orderSearchField;

    @FXML
    private ComboBox<OrderCustomer> orderCustomerComboBox;

    @FXML
    private ComboBox<Restaurant> orderRestaurantComboBox;

    @FXML
    private ComboBox<String> orderStatusComboBox;

    @FXML
    private TextField orderTotalPriceField;

    @FXML
    private Label orderMessageLabel;

    private final ObservableList<Order> ordersList = FXCollections.observableArrayList();
    private final ObservableList<OrderCustomer> customersList = FXCollections.observableArrayList();
    private final ObservableList<Restaurant> restaurantsList = FXCollections.observableArrayList();


    // ================== ORDER ITEMS SECTION ==================

    @FXML
    private TableView<OrderItem> orderItemsTable;

    @FXML
    private TableColumn<OrderItem, Integer> orderItemIdColumn;

    @FXML
    private TableColumn<OrderItem, Integer> orderItemOrderIdColumn;

    @FXML
    private TableColumn<OrderItem, Integer> orderItemItemIdColumn;

    @FXML
    private TableColumn<OrderItem, String> orderItemNameColumn;

    @FXML
    private TableColumn<OrderItem, String> orderItemRestaurantColumn;

    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantityColumn;

    @FXML
    private TableColumn<OrderItem, Double> orderItemPriceColumn;

    @FXML
    private TableColumn<OrderItem, Double> orderItemSubtotalColumn;

    @FXML
    private TextField orderItemSearchField;

    @FXML
    private ComboBox<Order> orderItemOrderComboBox;

    @FXML
    private ComboBox<OrderMenuItem> orderItemMenuItemComboBox;

    @FXML
    private TextField orderItemQuantityField;

    @FXML
    private TextField orderItemSubtotalField;

    @FXML
    private Label orderItemMessageLabel;

    private final ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
    private final ObservableList<OrderMenuItem> orderMenuItemsList = FXCollections.observableArrayList();


    // ================== PAYMENTS SECTION ==================

    @FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private TableColumn<Payment, Integer> paymentIdColumn;

    @FXML
    private TableColumn<Payment, Integer> paymentOrderIdColumn;

    @FXML
    private TableColumn<Payment, String> paymentCustomerNameColumn;

    @FXML
    private TableColumn<Payment, String> paymentRestaurantNameColumn;

    @FXML
    private TableColumn<Payment, Double> paymentTotalPriceColumn;

    @FXML
    private TableColumn<Payment, String> paymentMethodColumn;

    @FXML
    private TableColumn<Payment, String> paymentStatusColumn;

    @FXML
    private TableColumn<Payment, String> paymentDateColumn;

    @FXML
    private TextField paymentSearchField;

    @FXML
    private ComboBox<Order> paymentOrderComboBox;

    @FXML
    private ComboBox<String> paymentMethodComboBox;

    @FXML
    private ComboBox<String> paymentStatusComboBox;

    @FXML
    private TextField paymentDateField;

    @FXML
    private Label paymentMessageLabel;

    private final ObservableList<Payment> paymentsList = FXCollections.observableArrayList();


    // ================== DRIVERS SECTION ==================

    @FXML
    private TableView<Driver> driversTable;

    @FXML
    private TableColumn<Driver, Integer> driverIdColumn;

    @FXML
    private TableColumn<Driver, String> driverNameColumn;

    @FXML
    private TableColumn<Driver, String> driverPhoneColumn;

    @FXML
    private TableColumn<Driver, String> driverVehicleTypeColumn;

    @FXML
    private TableColumn<Driver, String> driverAvailabilityColumn;

    @FXML
    private TextField driverSearchField;

    @FXML
    private TextField driverNameField;

    @FXML
    private TextField driverPhoneField;

    @FXML
    private ComboBox<String> driverVehicleTypeComboBox;

    @FXML
    private ComboBox<String> driverAvailabilityComboBox;

    @FXML
    private Label driverMessageLabel;

    private final ObservableList<Driver> driversList = FXCollections.observableArrayList();


    // ================== DELIVERIES SECTION ==================

    @FXML
    private TableView<Delivery> deliveriesTable;

    @FXML
    private TableColumn<Delivery, Integer> deliveryIdColumn;

    @FXML
    private TableColumn<Delivery, Integer> deliveryOrderIdColumn;

    @FXML
    private TableColumn<Delivery, Integer> deliveryDriverIdColumn;

    @FXML
    private TableColumn<Delivery, String> deliveryDriverNameColumn;

    @FXML
    private TableColumn<Delivery, String> deliveryCustomerNameColumn;

    @FXML
    private TableColumn<Delivery, String> deliveryRestaurantNameColumn;

    @FXML
    private TableColumn<Delivery, String> deliveryStatusColumn;

    @FXML
    private TableColumn<Delivery, String> deliveryEstimatedTimeColumn;

    @FXML
    private TableColumn<Delivery, String> deliveryActualTimeColumn;

    @FXML
    private TextField deliverySearchField;

    @FXML
    private ComboBox<Order> deliveryOrderComboBox;

    @FXML
    private ComboBox<Driver> deliveryDriverComboBox;

    @FXML
    private ComboBox<String> deliveryStatusComboBox;

    @FXML
    private TextField deliveryEstimatedTimeField;

    @FXML
    private TextField deliveryActualTimeField;

    @FXML
    private Label deliveryMessageLabel;

    private final ObservableList<Delivery> deliveriesList = FXCollections.observableArrayList();


    // ================== DISCOUNTS SECTION ==================

    @FXML
    private TableView<Discount> discountsTable;

    @FXML
    private TableColumn<Discount, Integer> discountIdColumn;

    @FXML
    private TableColumn<Discount, String> discountCodeColumn;

    @FXML
    private TableColumn<Discount, String> discountDescriptionColumn;

    @FXML
    private TableColumn<Discount, Double> discountPercentageColumn;

    @FXML
    private TableColumn<Discount, String> discountStartDateColumn;

    @FXML
    private TableColumn<Discount, String> discountEndDateColumn;

    @FXML
    private TableView<OrderDiscount> orderDiscountsTable;

    @FXML
    private TableColumn<OrderDiscount, Integer> orderDiscountOrderIdColumn;

    @FXML
    private TableColumn<OrderDiscount, Integer> orderDiscountDiscountIdColumn;

    @FXML
    private TableColumn<OrderDiscount, String> orderDiscountCodeColumn;

    @FXML
    private TableColumn<OrderDiscount, Double> orderDiscountPercentageColumn;

    @FXML
    private TableColumn<OrderDiscount, String> orderDiscountCustomerColumn;

    @FXML
    private TableColumn<OrderDiscount, String> orderDiscountRestaurantColumn;

    @FXML
    private TableColumn<OrderDiscount, Double> orderDiscountTotalColumn;

    @FXML
    private TextField discountSearchField;

    @FXML
    private TextField discountCodeField;

    @FXML
    private TextField discountDescriptionField;

    @FXML
    private TextField discountPercentageField;

    @FXML
    private DatePicker discountStartDateField;

    @FXML
    private DatePicker discountEndDateField;

    @FXML
    private ComboBox<Order> orderDiscountOrderComboBox;

    @FXML
    private ComboBox<Discount> orderDiscountDiscountComboBox;

    @FXML
    private Label discountMessageLabel;

    private final ObservableList<Discount> discountsList = FXCollections.observableArrayList();
    private final ObservableList<OrderDiscount> orderDiscountsList = FXCollections.observableArrayList();


    // ================== INITIALIZE ==================

    @FXML
    public void initialize() {

        // Orders table setup
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        orderCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderRestaurantIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        orderRestaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderStatusComboBox.setItems(
                FXCollections.observableArrayList(
                        "Pending",
                        "Preparing",
                        "On The Way",
                        "Delivered",
                        "Cancelled"
                )
        );

        loadCustomersComboBox();
        loadRestaurantsComboBox();
        loadOrders();

        ordersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedOrder) -> {
                    if (selectedOrder != null) {
                        selectCustomerInCombo(selectedOrder.getCustomerId());
                        selectRestaurantInCombo(selectedOrder.getRestaurantId());
                        orderStatusComboBox.setValue(selectedOrder.getStatus());
                        orderTotalPriceField.setText(String.valueOf(selectedOrder.getTotalPrice()));
                    }
                }
        );


        // Order Items table setup
        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));
        orderItemOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderItemItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        orderItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        orderItemRestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        orderItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        orderItemSubtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        loadOrderItemComboBoxes();
        loadOrderItems();

        orderItemsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedOrderItem) -> {
                    if (selectedOrderItem != null) {
                        selectOrderInOrderItemCombo(selectedOrderItem.getOrderId());
                        selectMenuItemInOrderItemCombo(selectedOrderItem.getItemId());
                        orderItemQuantityField.setText(String.valueOf(selectedOrderItem.getQuantity()));
                        orderItemSubtotalField.setText(String.valueOf(selectedOrderItem.getSubtotal()));
                    }
                }
        );


        // Payments table setup
        paymentIdColumn.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        paymentOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        paymentCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        paymentRestaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        paymentTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        paymentMethodComboBox.setItems(
                FXCollections.observableArrayList(
                        "Cash",
                        "Credit Card",
                        "Debit Card",
                        "PayPal"
                )
        );

        paymentStatusComboBox.setItems(
                FXCollections.observableArrayList(
                        "Pending",
                        "Paid",
                        "Failed",
                        "Refunded"
                )
        );

        loadPaymentComboBox();
        loadPayments();

        paymentsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedPayment) -> {
                    if (selectedPayment != null) {
                        selectOrderInPaymentCombo(selectedPayment.getOrderId());
                        paymentMethodComboBox.setValue(selectedPayment.getPaymentMethod());
                        paymentStatusComboBox.setValue(selectedPayment.getPaymentStatus());
                        paymentDateField.setText(selectedPayment.getPaymentDate());
                    }
                }
        );


        // Drivers table setup
        driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        driverNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        driverPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        driverVehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        driverAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));

        driverVehicleTypeComboBox.setItems(
                FXCollections.observableArrayList(
                        "Motorcycle",
                        "Car",
                        "Bicycle"
                )
        );

        driverAvailabilityComboBox.setItems(
                FXCollections.observableArrayList(
                        "Available",
                        "Unavailable",
                        "Busy"
                )
        );

        loadDrivers();

        driversTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedDriver) -> {
                    if (selectedDriver != null) {
                        driverNameField.setText(selectedDriver.getName());
                        driverPhoneField.setText(selectedDriver.getPhone());
                        driverVehicleTypeComboBox.setValue(selectedDriver.getVehicleType());
                        driverAvailabilityComboBox.setValue(selectedDriver.getAvailabilityStatus());
                    }
                }
        );


        // Deliveries table setup
        deliveryIdColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        deliveryOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        deliveryDriverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        deliveryDriverNameColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        deliveryCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        deliveryRestaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        deliveryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        deliveryEstimatedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));
        deliveryActualTimeColumn.setCellValueFactory(new PropertyValueFactory<>("actualTime"));

        deliveryStatusComboBox.setItems(
                FXCollections.observableArrayList(
                        "Pending",
                        "Assigned",
                        "On The Way",
                        "Delivered",
                        "Cancelled"
                )
        );

        loadDeliveryComboBoxes();
        loadDeliveries();

        deliveriesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedDelivery) -> {
                    if (selectedDelivery != null) {
                        selectOrderInDeliveryCombo(selectedDelivery.getOrderId());
                        selectDriverInDeliveryCombo(selectedDelivery.getDriverId());
                        deliveryStatusComboBox.setValue(selectedDelivery.getDeliveryStatus());
                        deliveryEstimatedTimeField.setText(selectedDelivery.getEstimatedTime());
                        deliveryActualTimeField.setText(selectedDelivery.getActualTime());
                    }
                }
        );


        // Discounts table setup
        discountIdColumn.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        discountCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        discountDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        discountPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        discountStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        discountEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        orderDiscountOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderDiscountDiscountIdColumn.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        orderDiscountCodeColumn.setCellValueFactory(new PropertyValueFactory<>("discountCode"));
        orderDiscountPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        orderDiscountCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderDiscountRestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        orderDiscountTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        loadDiscounts();
        loadOrderDiscounts();
        loadOrderDiscountComboBoxes();

        discountsTable.getSelectionModel().selectedItemProperty().addListener(
                (discountObservable, discountOldValue, selectedDiscount) -> {
                    if (selectedDiscount != null) {
                        discountCodeField.setText(selectedDiscount.getCode());
                        discountDescriptionField.setText(selectedDiscount.getDescription());
                        discountPercentageField.setText(String.valueOf(selectedDiscount.getPercentage()));
                        discountStartDateField.setValue(parseDate(selectedDiscount.getStartDate()));
                        discountEndDateField.setValue(parseDate(selectedDiscount.getEndDate()));
                    }
                }
        );

        orderDiscountsTable.getSelectionModel().selectedItemProperty().addListener(
                (orderDiscountObservable, orderDiscountOldValue, selectedOrderDiscount) -> {
                    if (selectedOrderDiscount != null) {
                        selectOrderInOrderDiscountCombo(selectedOrderDiscount.getOrderId());
                        selectDiscountInOrderDiscountCombo(selectedOrderDiscount.getDiscountId());
                    }
                }
        );
    }


    // ================== LOAD COMBOBOX DATA ==================

    private void loadCustomersComboBox() {
        customersList.clear();

        String sql = "SELECT customer_id, name FROM customer ORDER BY customer_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                OrderCustomer customer = new OrderCustomer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("name")
                );

                customersList.add(customer);
            }

            orderCustomerComboBox.setItems(customersList);

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Customers load failed: " + e.getMessage());
        }
    }

    private void loadRestaurantsComboBox() {
        restaurantsList.clear();

        String sql = "SELECT restaurant_id, name, phone, location, rating FROM restaurant ORDER BY restaurant_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant(
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("location"),
                        resultSet.getDouble("rating")
                );

                restaurantsList.add(restaurant);
            }

            orderRestaurantComboBox.setItems(restaurantsList);

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Restaurants load failed: " + e.getMessage());
        }
    }

    private void loadOrderItemComboBoxes() {
        loadOrderComboBox();
        loadOrderMenuItemComboBox();
    }

    private void loadOrderComboBox() {
        orderItemOrderComboBox.setItems(ordersList);
    }

    private void loadPaymentComboBox() {
        paymentOrderComboBox.setItems(ordersList);
    }

    private void loadDeliveryComboBoxes() {
        deliveryOrderComboBox.setItems(ordersList);
        deliveryDriverComboBox.setItems(driversList);
    }

    private void loadOrderDiscountComboBoxes() {
        orderDiscountOrderComboBox.setItems(ordersList);
        orderDiscountDiscountComboBox.setItems(discountsList);
    }

    private void loadOrderMenuItemComboBox() {
        orderMenuItemsList.clear();

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

                orderMenuItemsList.add(item);
            }

            orderItemMenuItemComboBox.setItems(orderMenuItemsList);

        } catch (Exception e) {
            e.printStackTrace();
            showOrderItemError("Menu items load failed: " + e.getMessage());
        }
    }


    // ================== ORDERS CRUD ==================

    @FXML
    void loadOrders() {
        ordersList.clear();

        String sql =
                "SELECT o.order_id, c.customer_id, c.name AS customer_name, " +
                "r.restaurant_id, r.name AS restaurant_name, " +
                "o.order_date, o.status, o.total_price " +
                "FROM `order` o " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "ORDER BY o.order_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("order_date"),
                        resultSet.getString("status"),
                        resultSet.getDouble("total_price")
                );

                ordersList.add(order);
            }

            ordersTable.setItems(ordersList);
            loadOrderComboBox();
            loadPaymentComboBox();
            loadDeliveryComboBoxes();
            loadOrderDiscountComboBoxes();
            showOrderSuccess("Orders loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchOrders(ActionEvent event) {
        String keyword = orderSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadOrders();
            return;
        }

        ordersList.clear();

        String sql =
                "SELECT o.order_id, c.customer_id, c.name AS customer_name, " +
                "r.restaurant_id, r.name AS restaurant_name, " +
                "o.order_date, o.status, o.total_price " +
                "FROM `order` o " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "WHERE c.name LIKE ? " +
                "OR r.name LIKE ? " +
                "OR o.status LIKE ? " +
                "OR CAST(o.order_date AS CHAR) LIKE ? " +
                "OR CAST(o.total_price AS CHAR) LIKE ? " +
                "ORDER BY o.order_id";

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
                Order order = new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("order_date"),
                        resultSet.getString("status"),
                        resultSet.getDouble("total_price")
                );

                ordersList.add(order);
            }

            ordersTable.setItems(ordersList);
            loadOrderComboBox();
            loadPaymentComboBox();
            loadDeliveryComboBoxes();
            loadOrderDiscountComboBoxes();
            resultSet.close();

            if (ordersList.isEmpty()) {
                showOrderError("No orders found");
            } else {
                showOrderSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addOrder(ActionEvent event) {
        OrderCustomer selectedCustomer = orderCustomerComboBox.getValue();
        Restaurant selectedRestaurant = orderRestaurantComboBox.getValue();
        String status = orderStatusComboBox.getValue();
        String totalPriceText = orderTotalPriceField.getText().trim();

        if (!validateOrderFields(selectedCustomer, selectedRestaurant, status, totalPriceText)) {
            return;
        }

        double totalPrice = Double.parseDouble(totalPriceText);

        String sql =
                "INSERT INTO `order` (customer_id, restaurant_id, order_date, status, total_price) " +
                "VALUES (?, ?, NOW(), ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());
            statement.setInt(2, selectedRestaurant.getRestaurantId());
            statement.setString(3, status);
            statement.setDouble(4, totalPrice);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showOrderSuccess("Order added successfully");
                clearOrderFields();
                loadOrders();
                loadOrderItemComboBoxes();
                loadPaymentComboBox();
                loadDeliveryComboBoxes();
                loadOrderDiscountComboBoxes();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateOrder(ActionEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            showOrderError("Please select an order to update");
            return;
        }

        OrderCustomer selectedCustomer = orderCustomerComboBox.getValue();
        Restaurant selectedRestaurant = orderRestaurantComboBox.getValue();
        String status = orderStatusComboBox.getValue();
        String totalPriceText = orderTotalPriceField.getText().trim();

        if (!validateOrderFields(selectedCustomer, selectedRestaurant, status, totalPriceText)) {
            return;
        }

        double totalPrice = Double.parseDouble(totalPriceText);

        String sql =
                "UPDATE `order` " +
                "SET customer_id = ?, restaurant_id = ?, status = ?, total_price = ? " +
                "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCustomer.getCustomerId());
            statement.setInt(2, selectedRestaurant.getRestaurantId());
            statement.setString(3, status);
            statement.setDouble(4, totalPrice);
            statement.setInt(5, selectedOrder.getOrderId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showOrderSuccess("Order updated successfully");
                clearOrderFields();
                loadOrders();
                loadOrderItems();
                loadPayments();
                loadDeliveries();
                loadOrderDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            showOrderError("Please select an order to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Order");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(
                "Are you sure you want to delete this order? Related order items, payments, deliveries, discounts, and status history may also be deleted."
        );

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM `order` WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showOrderSuccess("Order deleted successfully");
                clearOrderFields();
                loadOrders();
                loadOrderItems();
                loadPayments();
                loadDeliveries();
                loadOrderDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearOrderFields() {
        ordersTable.getSelectionModel().clearSelection();
        orderCustomerComboBox.getSelectionModel().clearSelection();
        orderRestaurantComboBox.getSelectionModel().clearSelection();
        orderStatusComboBox.getSelectionModel().clearSelection();
        orderTotalPriceField.clear();
        orderSearchField.clear();
        orderMessageLabel.setText("");
    }


    // ================== ORDER ITEMS CRUD ==================

    @FXML
    void loadOrderItems() {
        orderItemsList.clear();

        String sql =
                "SELECT oi.order_item_id, oi.order_id, mi.item_id, mi.name AS item_name, " +
                "r.name AS restaurant_name, oi.quantity, mi.price AS item_price, oi.subtotal " +
                "FROM order_item oi " +
                "JOIN menu_item mi ON oi.item_id = mi.item_id " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "ORDER BY oi.order_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem(
                        resultSet.getInt("order_item_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("item_price"),
                        resultSet.getDouble("subtotal")
                );

                orderItemsList.add(orderItem);
            }

            orderItemsTable.setItems(orderItemsList);
            showOrderItemSuccess("Order items loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showOrderItemError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchOrderItems(ActionEvent event) {
        String keyword = orderItemSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadOrderItems();
            return;
        }

        orderItemsList.clear();

        String sql =
                "SELECT oi.order_item_id, oi.order_id, mi.item_id, mi.name AS item_name, " +
                "r.name AS restaurant_name, oi.quantity, mi.price AS item_price, oi.subtotal " +
                "FROM order_item oi " +
                "JOIN menu_item mi ON oi.item_id = mi.item_id " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "WHERE CAST(oi.order_id AS CHAR) LIKE ? " +
                "OR mi.name LIKE ? " +
                "OR r.name LIKE ? " +
                "ORDER BY oi.order_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem(
                        resultSet.getInt("order_item_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("item_price"),
                        resultSet.getDouble("subtotal")
                );

                orderItemsList.add(orderItem);
            }

            orderItemsTable.setItems(orderItemsList);
            resultSet.close();

            if (orderItemsList.isEmpty()) {
                showOrderItemError("No order items found");
            } else {
                showOrderItemSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderItemError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void calculateOrderItemSubtotal(ActionEvent event) {
        OrderMenuItem selectedMenuItem = orderItemMenuItemComboBox.getValue();
        String quantityText = orderItemQuantityField.getText().trim();

        if (selectedMenuItem == null || quantityText.isEmpty()) {
            showOrderItemError("Please select menu item and enter quantity");
            return;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showOrderItemError("Quantity must be a number");
            return;
        }

        if (quantity <= 0) {
            showOrderItemError("Quantity must be greater than zero");
            return;
        }

        double subtotal = quantity * selectedMenuItem.getPrice();
        orderItemSubtotalField.setText(String.valueOf(subtotal));
        showOrderItemSuccess("Subtotal calculated");
    }

    @FXML
    void addOrderItem(ActionEvent event) {
        Order selectedOrder = orderItemOrderComboBox.getValue();
        OrderMenuItem selectedMenuItem = orderItemMenuItemComboBox.getValue();
        String quantityText = orderItemQuantityField.getText().trim();

        if (!validateOrderItemFields(selectedOrder, selectedMenuItem, quantityText)) {
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        double subtotal = quantity * selectedMenuItem.getPrice();

        String sql =
                "INSERT INTO order_item (order_id, item_id, quantity, subtotal) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());
            statement.setInt(2, selectedMenuItem.getItemId());
            statement.setInt(3, quantity);
            statement.setDouble(4, subtotal);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                updateOrderTotal(selectedOrder.getOrderId());
                showOrderItemSuccess("Order item added successfully");
                clearOrderItemFields();
                loadOrderItems();
                loadOrders();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderItemError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateOrderItem(ActionEvent event) {
        OrderItem selectedOrderItem = orderItemsTable.getSelectionModel().getSelectedItem();

        if (selectedOrderItem == null) {
            showOrderItemError("Please select an order item to update");
            return;
        }

        Order selectedOrder = orderItemOrderComboBox.getValue();
        OrderMenuItem selectedMenuItem = orderItemMenuItemComboBox.getValue();
        String quantityText = orderItemQuantityField.getText().trim();

        if (!validateOrderItemFields(selectedOrder, selectedMenuItem, quantityText)) {
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        double subtotal = quantity * selectedMenuItem.getPrice();

        int oldOrderId = selectedOrderItem.getOrderId();
        int newOrderId = selectedOrder.getOrderId();

        String sql =
                "UPDATE order_item " +
                "SET order_id = ?, item_id = ?, quantity = ?, subtotal = ? " +
                "WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newOrderId);
            statement.setInt(2, selectedMenuItem.getItemId());
            statement.setInt(3, quantity);
            statement.setDouble(4, subtotal);
            statement.setInt(5, selectedOrderItem.getOrderItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                updateOrderTotal(oldOrderId);
                updateOrderTotal(newOrderId);
                showOrderItemSuccess("Order item updated successfully");
                clearOrderItemFields();
                loadOrderItems();
                loadOrders();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderItemError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteOrderItem(ActionEvent event) {
        OrderItem selectedOrderItem = orderItemsTable.getSelectionModel().getSelectedItem();

        if (selectedOrderItem == null) {
            showOrderItemError("Please select an order item to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Order Item");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this order item?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        int orderId = selectedOrderItem.getOrderId();

        String sql = "DELETE FROM order_item WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrderItem.getOrderItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                updateOrderTotal(orderId);
                showOrderItemSuccess("Order item deleted successfully");
                clearOrderItemFields();
                loadOrderItems();
                loadOrders();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showOrderItemError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearOrderItemFields() {
        orderItemsTable.getSelectionModel().clearSelection();
        orderItemOrderComboBox.getSelectionModel().clearSelection();
        orderItemMenuItemComboBox.getSelectionModel().clearSelection();
        orderItemQuantityField.clear();
        orderItemSubtotalField.clear();
        orderItemSearchField.clear();
        orderItemMessageLabel.setText("");
    }


    // ================== PAYMENTS CRUD ==================

    @FXML
    void loadPayments() {
        paymentsList.clear();

        String sql =
                "SELECT p.payment_id, p.order_id, c.name AS customer_name, " +
                "r.name AS restaurant_name, o.total_price, " +
                "p.payment_method, p.payment_status, p.payment_date " +
                "FROM payment p " +
                "JOIN `order` o ON p.order_id = o.order_id " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "ORDER BY p.payment_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Payment payment = new Payment(
                        resultSet.getInt("payment_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("total_price"),
                        resultSet.getString("payment_method"),
                        resultSet.getString("payment_status"),
                        resultSet.getString("payment_date")
                );

                paymentsList.add(payment);
            }

            paymentsTable.setItems(paymentsList);
            showPaymentSuccess("Payments loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showPaymentError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchPayments(ActionEvent event) {
        String keyword = paymentSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadPayments();
            return;
        }

        paymentsList.clear();

        String sql =
                "SELECT p.payment_id, p.order_id, c.name AS customer_name, " +
                "r.name AS restaurant_name, o.total_price, " +
                "p.payment_method, p.payment_status, p.payment_date " +
                "FROM payment p " +
                "JOIN `order` o ON p.order_id = o.order_id " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "WHERE CAST(p.order_id AS CHAR) LIKE ? " +
                "OR c.name LIKE ? " +
                "OR r.name LIKE ? " +
                "OR p.payment_method LIKE ? " +
                "OR p.payment_status LIKE ? " +
                "OR CAST(p.payment_date AS CHAR) LIKE ? " +
                "ORDER BY p.payment_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);
            statement.setString(5, value);
            statement.setString(6, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment(
                        resultSet.getInt("payment_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("total_price"),
                        resultSet.getString("payment_method"),
                        resultSet.getString("payment_status"),
                        resultSet.getString("payment_date")
                );

                paymentsList.add(payment);
            }

            paymentsTable.setItems(paymentsList);
            resultSet.close();

            if (paymentsList.isEmpty()) {
                showPaymentError("No payments found");
            } else {
                showPaymentSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showPaymentError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addPayment(ActionEvent event) {
        Order selectedOrder = paymentOrderComboBox.getValue();
        String method = paymentMethodComboBox.getValue();
        String status = paymentStatusComboBox.getValue();

        if (!validatePaymentFields(selectedOrder, method, status)) {
            return;
        }

        String sql =
                "INSERT INTO payment (order_id, payment_method, payment_status, payment_date) " +
                "VALUES (?, ?, ?, NOW())";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());
            statement.setString(2, method);
            statement.setString(3, status);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showPaymentSuccess("Payment added successfully");
                clearPaymentFields();
                loadPayments();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showPaymentError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updatePayment(ActionEvent event) {
        Payment selectedPayment = paymentsTable.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            showPaymentError("Please select a payment to update");
            return;
        }

        Order selectedOrder = paymentOrderComboBox.getValue();
        String method = paymentMethodComboBox.getValue();
        String status = paymentStatusComboBox.getValue();

        if (!validatePaymentFields(selectedOrder, method, status)) {
            return;
        }

        String sql =
                "UPDATE payment " +
                "SET order_id = ?, payment_method = ?, payment_status = ? " +
                "WHERE payment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());
            statement.setString(2, method);
            statement.setString(3, status);
            statement.setInt(4, selectedPayment.getPaymentId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showPaymentSuccess("Payment updated successfully");
                clearPaymentFields();
                loadPayments();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showPaymentError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deletePayment(ActionEvent event) {
        Payment selectedPayment = paymentsTable.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            showPaymentError("Please select a payment to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Payment");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this payment?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM payment WHERE payment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedPayment.getPaymentId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showPaymentSuccess("Payment deleted successfully");
                clearPaymentFields();
                loadPayments();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showPaymentError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearPaymentFields() {
        paymentsTable.getSelectionModel().clearSelection();
        paymentOrderComboBox.getSelectionModel().clearSelection();
        paymentMethodComboBox.getSelectionModel().clearSelection();
        paymentStatusComboBox.getSelectionModel().clearSelection();
        paymentDateField.clear();
        paymentSearchField.clear();
        paymentMessageLabel.setText("");
    }


    // ================== DRIVERS CRUD ==================

    @FXML
    void loadDrivers() {
        driversList.clear();

        String sql =
                "SELECT driver_id, name, phone, vehicle_type, availability AS availability_status " +
                "FROM driver " +
                "ORDER BY driver_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Driver driver = new Driver(
                        resultSet.getInt("driver_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("vehicle_type"),
                        resultSet.getString("availability_status")
                );

                driversList.add(driver);
            }

            driversTable.setItems(driversList);
            loadDeliveryComboBoxes();
            showDriverSuccess("Drivers loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showDriverError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchDrivers(ActionEvent event) {
        String keyword = driverSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadDrivers();
            return;
        }

        driversList.clear();

        String sql =
                "SELECT driver_id, name, phone, vehicle_type, availability AS availability_status " +
                "FROM driver " +
                "WHERE name LIKE ? " +
                "OR phone LIKE ? " +
                "OR vehicle_type LIKE ? " +
                "OR availability LIKE ? " +
                "ORDER BY driver_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Driver driver = new Driver(
                        resultSet.getInt("driver_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("vehicle_type"),
                        resultSet.getString("availability_status")
                );

                driversList.add(driver);
            }

            driversTable.setItems(driversList);
            loadDeliveryComboBoxes();
            loadOrderDiscountComboBoxes();
            resultSet.close();

            if (driversList.isEmpty()) {
                showDriverError("No drivers found");
            } else {
                showDriverSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDriverError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addDriver(ActionEvent event) {
        String name = driverNameField.getText().trim();
        String phone = driverPhoneField.getText().trim();
        String vehicleType = driverVehicleTypeComboBox.getValue();
        String availability = driverAvailabilityComboBox.getValue();

        if (!validateDriverFields(name, phone, vehicleType, availability)) {
            return;
        }

        String sql =
                "INSERT INTO driver (name, phone, vehicle_type, availability) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, vehicleType);
            statement.setString(4, availability);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDriverSuccess("Driver added successfully");
                clearDriverFields();
                loadDrivers();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDriverError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateDriver(ActionEvent event) {
        Driver selectedDriver = driversTable.getSelectionModel().getSelectedItem();

        if (selectedDriver == null) {
            showDriverError("Please select a driver to update");
            return;
        }

        String name = driverNameField.getText().trim();
        String phone = driverPhoneField.getText().trim();
        String vehicleType = driverVehicleTypeComboBox.getValue();
        String availability = driverAvailabilityComboBox.getValue();

        if (!validateDriverFields(name, phone, vehicleType, availability)) {
            return;
        }

        String sql =
                "UPDATE driver " +
                "SET name = ?, phone = ?, vehicle_type = ?, availability = ? " +
                "WHERE driver_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, vehicleType);
            statement.setString(4, availability);
            statement.setInt(5, selectedDriver.getDriverId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDriverSuccess("Driver updated successfully");
                clearDriverFields();
                loadDrivers();
                loadDeliveries();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDriverError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteDriver(ActionEvent event) {
        Driver selectedDriver = driversTable.getSelectionModel().getSelectedItem();

        if (selectedDriver == null) {
            showDriverError("Please select a driver to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Driver");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this driver? Related deliveries may be affected.");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM driver WHERE driver_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedDriver.getDriverId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDriverSuccess("Driver deleted successfully");
                clearDriverFields();
                loadDrivers();
                loadDeliveries();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDriverError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearDriverFields() {
        driversTable.getSelectionModel().clearSelection();
        driverNameField.clear();
        driverPhoneField.clear();
        driverSearchField.clear();
        driverVehicleTypeComboBox.getSelectionModel().clearSelection();
        driverAvailabilityComboBox.getSelectionModel().clearSelection();
        driverMessageLabel.setText("");
    }


    // ================== DELIVERIES CRUD ==================

    @FXML
    void loadDeliveries() {
        deliveriesList.clear();

        String sql =
                "SELECT d.delivery_id, d.order_id, d.driver_id, " +
                "dr.name AS driver_name, c.name AS customer_name, r.name AS restaurant_name, " +
                "d.delivery_status, d.estimated_time, d.actual_time " +
                "FROM delivery d " +
                "JOIN driver dr ON d.driver_id = dr.driver_id " +
                "JOIN `order` o ON d.order_id = o.order_id " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "ORDER BY d.delivery_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Delivery delivery = new Delivery(
                        resultSet.getInt("delivery_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("driver_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("delivery_status"),
                        resultSet.getString("estimated_time"),
                        resultSet.getString("actual_time")
                );

                deliveriesList.add(delivery);
            }

            deliveriesTable.setItems(deliveriesList);
            showDeliverySuccess("Deliveries loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showDeliveryError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchDeliveries(ActionEvent event) {
        String keyword = deliverySearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadDeliveries();
            return;
        }

        deliveriesList.clear();

        String sql =
                "SELECT d.delivery_id, d.order_id, d.driver_id, " +
                "dr.name AS driver_name, c.name AS customer_name, r.name AS restaurant_name, " +
                "d.delivery_status, d.estimated_time, d.actual_time " +
                "FROM delivery d " +
                "JOIN driver dr ON d.driver_id = dr.driver_id " +
                "JOIN `order` o ON d.order_id = o.order_id " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "WHERE CAST(d.order_id AS CHAR) LIKE ? " +
                "OR dr.name LIKE ? " +
                "OR c.name LIKE ? " +
                "OR r.name LIKE ? " +
                "OR d.delivery_status LIKE ? " +
                "ORDER BY d.delivery_id";

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
                Delivery delivery = new Delivery(
                        resultSet.getInt("delivery_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("driver_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("delivery_status"),
                        resultSet.getString("estimated_time"),
                        resultSet.getString("actual_time")
                );

                deliveriesList.add(delivery);
            }

            deliveriesTable.setItems(deliveriesList);
            resultSet.close();

            if (deliveriesList.isEmpty()) {
                showDeliveryError("No deliveries found");
            } else {
                showDeliverySuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDeliveryError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addDelivery(ActionEvent event) {
        Order selectedOrder = deliveryOrderComboBox.getValue();
        Driver selectedDriver = deliveryDriverComboBox.getValue();
        String status = deliveryStatusComboBox.getValue();
        String estimatedTime = deliveryEstimatedTimeField.getText().trim();
        String actualTime = deliveryActualTimeField.getText().trim();

        if (!validateDeliveryFields(selectedOrder, selectedDriver, status, estimatedTime)) {
            return;
        }

        String sql =
                "INSERT INTO delivery (order_id, driver_id, delivery_status, estimated_time, actual_time) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());
            statement.setInt(2, selectedDriver.getDriverId());
            statement.setString(3, status);
            statement.setString(4, estimatedTime);

            if (actualTime.isEmpty()) {
                statement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                statement.setString(5, actualTime);
            }

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDeliverySuccess("Delivery added successfully");
                clearDeliveryFields();
                loadDeliveries();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDeliveryError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateDelivery(ActionEvent event) {
        Delivery selectedDelivery = deliveriesTable.getSelectionModel().getSelectedItem();

        if (selectedDelivery == null) {
            showDeliveryError("Please select a delivery to update");
            return;
        }

        Order selectedOrder = deliveryOrderComboBox.getValue();
        Driver selectedDriver = deliveryDriverComboBox.getValue();
        String status = deliveryStatusComboBox.getValue();
        String estimatedTime = deliveryEstimatedTimeField.getText().trim();
        String actualTime = deliveryActualTimeField.getText().trim();

        if (!validateDeliveryFields(selectedOrder, selectedDriver, status, estimatedTime)) {
            return;
        }

        String sql =
                "UPDATE delivery " +
                "SET order_id = ?, driver_id = ?, delivery_status = ?, estimated_time = ?, actual_time = ? " +
                "WHERE delivery_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());
            statement.setInt(2, selectedDriver.getDriverId());
            statement.setString(3, status);
            statement.setString(4, estimatedTime);

            if (actualTime.isEmpty()) {
                statement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                statement.setString(5, actualTime);
            }

            statement.setInt(6, selectedDelivery.getDeliveryId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDeliverySuccess("Delivery updated successfully");
                clearDeliveryFields();
                loadDeliveries();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDeliveryError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteDelivery(ActionEvent event) {
        Delivery selectedDelivery = deliveriesTable.getSelectionModel().getSelectedItem();

        if (selectedDelivery == null) {
            showDeliveryError("Please select a delivery to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Delivery");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this delivery?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM delivery WHERE delivery_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedDelivery.getDeliveryId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDeliverySuccess("Delivery deleted successfully");
                clearDeliveryFields();
                loadDeliveries();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDeliveryError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearDeliveryFields() {
        deliveriesTable.getSelectionModel().clearSelection();
        deliveryOrderComboBox.getSelectionModel().clearSelection();
        deliveryDriverComboBox.getSelectionModel().clearSelection();
        deliveryStatusComboBox.getSelectionModel().clearSelection();
        deliveryEstimatedTimeField.clear();
        deliveryActualTimeField.clear();
        deliverySearchField.clear();
        deliveryMessageLabel.setText("");
    }


    // ================== DISCOUNTS CRUD ==================

    @FXML
    void loadDiscounts() {
        discountsList.clear();

        String sql =
                "SELECT discount_id, code, description, percentage, start_date, expiration_date AS end_date " +
                "FROM discount " +
                "ORDER BY discount_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Discount discount = new Discount(
                        resultSet.getInt("discount_id"),
                        resultSet.getString("code"),
                        resultSet.getString("description"),
                        resultSet.getDouble("percentage"),
                        resultSet.getString("start_date"),
                        resultSet.getString("end_date")
                );

                discountsList.add(discount);
            }

            discountsTable.setItems(discountsList);
            loadOrderDiscountComboBoxes();
            showDiscountSuccess("Discounts loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchDiscounts(ActionEvent event) {
        String keyword = discountSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadDiscounts();
            return;
        }

        discountsList.clear();

        String sql =
                "SELECT discount_id, code, description, percentage, start_date, expiration_date AS end_date " +
                "FROM discount " +
                "WHERE code LIKE ? " +
                "OR description LIKE ? " +
                "OR CAST(percentage AS CHAR) LIKE ? " +
                "OR CAST(start_date AS CHAR) LIKE ? " +
                "OR CAST(expiration_date AS CHAR) LIKE ? " +
                "ORDER BY discount_id";

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
                Discount discount = new Discount(
                        resultSet.getInt("discount_id"),
                        resultSet.getString("code"),
                        resultSet.getString("description"),
                        resultSet.getDouble("percentage"),
                        resultSet.getString("start_date"),
                        resultSet.getString("end_date")
                );

                discountsList.add(discount);
            }

            discountsTable.setItems(discountsList);
            loadOrderDiscountComboBoxes();
            resultSet.close();

            if (discountsList.isEmpty()) {
                showDiscountError("No discounts found");
            } else {
                showDiscountSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addDiscount(ActionEvent event) {
        String code = discountCodeField.getText().trim();
        String description = discountDescriptionField.getText().trim();
        String percentageText = discountPercentageField.getText().trim();
        String startDate = getDatePickerValue(discountStartDateField);
        String endDate = getDatePickerValue(discountEndDateField);

        if (!validateDiscountFields(code, percentageText, startDate, endDate)) {
            return;
        }

        double percentage = Double.parseDouble(percentageText);

        String sql =
                "INSERT INTO discount (code, description, percentage, start_date, expiration_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code);

            if (description.isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, description);
            }

            statement.setDouble(3, percentage);
            statement.setString(4, startDate);
            statement.setString(5, endDate);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDiscountSuccess("Discount added successfully");
                clearDiscountFields();
                loadDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateDiscount(ActionEvent event) {
        Discount selectedDiscount = discountsTable.getSelectionModel().getSelectedItem();

        if (selectedDiscount == null) {
            showDiscountError("Please select a discount to update");
            return;
        }

        String code = discountCodeField.getText().trim();
        String description = discountDescriptionField.getText().trim();
        String percentageText = discountPercentageField.getText().trim();
        String startDate = getDatePickerValue(discountStartDateField);
        String endDate = getDatePickerValue(discountEndDateField);

        if (!validateDiscountFields(code, percentageText, startDate, endDate)) {
            return;
        }

        double percentage = Double.parseDouble(percentageText);

        String sql =
                "UPDATE discount " +
                "SET code = ?, description = ?, percentage = ?, start_date = ?, expiration_date = ? " +
                "WHERE discount_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code);

            if (description.isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, description);
            }

            statement.setDouble(3, percentage);
            statement.setString(4, startDate);
            statement.setString(5, endDate);
            statement.setInt(6, selectedDiscount.getDiscountId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDiscountSuccess("Discount updated successfully");
                clearDiscountFields();
                loadDiscounts();
                loadOrderDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteDiscount(ActionEvent event) {
        Discount selectedDiscount = discountsTable.getSelectionModel().getSelectedItem();

        if (selectedDiscount == null) {
            showDiscountError("Please select a discount to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Discount");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this discount? Related order assignments may be affected.");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM discount WHERE discount_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedDiscount.getDiscountId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDiscountSuccess("Discount deleted successfully");
                clearDiscountFields();
                loadDiscounts();
                loadOrderDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void loadOrderDiscounts() {
        orderDiscountsList.clear();

        String sql =
                "SELECT od.order_id, d.discount_id, d.code, d.percentage, " +
                "c.name AS customer_name, r.name AS restaurant_name, o.total_price " +
                "FROM order_discount od " +
                "JOIN discount d ON od.discount_id = d.discount_id " +
                "JOIN `order` o ON od.order_id = o.order_id " +
                "JOIN customer c ON o.customer_id = c.customer_id " +
                "JOIN restaurant r ON o.restaurant_id = r.restaurant_id " +
                "ORDER BY od.order_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                OrderDiscount orderDiscount = new OrderDiscount(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("discount_id"),
                        resultSet.getString("code"),
                        resultSet.getDouble("percentage"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getDouble("total_price")
                );

                orderDiscountsList.add(orderDiscount);
            }

            orderDiscountsTable.setItems(orderDiscountsList);

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Order discounts load failed: " + e.getMessage());
        }
    }

    @FXML
    void assignDiscountToOrder(ActionEvent event) {
        Order selectedOrder = orderDiscountOrderComboBox.getValue();
        Discount selectedDiscount = orderDiscountDiscountComboBox.getValue();

        if (selectedOrder == null || selectedDiscount == null) {
            showDiscountError("Order and discount are required");
            return;
        }

        String sql =
                "INSERT INTO order_discount (order_id, discount_id) " +
                "VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getOrderId());
            statement.setInt(2, selectedDiscount.getDiscountId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDiscountSuccess("Discount assigned to order successfully");
                clearDiscountFields();
                loadOrderDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Assign failed: " + e.getMessage());
        }
    }

    @FXML
    void removeOrderDiscount(ActionEvent event) {
        OrderDiscount selectedOrderDiscount = orderDiscountsTable.getSelectionModel().getSelectedItem();

        if (selectedOrderDiscount == null) {
            showDiscountError("Please select an order discount assignment to remove");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Remove Assignment");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to remove this discount from the order?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql =
                "DELETE FROM order_discount " +
                "WHERE order_id = ? AND discount_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrderDiscount.getOrderId());
            statement.setInt(2, selectedOrderDiscount.getDiscountId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showDiscountSuccess("Assignment removed successfully");
                clearDiscountFields();
                loadOrderDiscounts();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showDiscountError("Remove failed: " + e.getMessage());
        }
    }

    @FXML
    void clearDiscountFields() {
        discountsTable.getSelectionModel().clearSelection();
        orderDiscountsTable.getSelectionModel().clearSelection();

        discountCodeField.clear();
        discountDescriptionField.clear();
        discountPercentageField.clear();
        discountStartDateField.setValue(null);
        discountEndDateField.setValue(null);
        discountSearchField.clear();

        orderDiscountOrderComboBox.getSelectionModel().clearSelection();
        orderDiscountDiscountComboBox.getSelectionModel().clearSelection();

        discountMessageLabel.setText("");
    }


    // ================== HELPERS ==================

    private void selectCustomerInCombo(int customerId) {
        for (OrderCustomer customer : customersList) {
            if (customer.getCustomerId() == customerId) {
                orderCustomerComboBox.setValue(customer);
                return;
            }
        }
    }

    private void selectRestaurantInCombo(int restaurantId) {
        for (Restaurant restaurant : restaurantsList) {
            if (restaurant.getRestaurantId() == restaurantId) {
                orderRestaurantComboBox.setValue(restaurant);
                return;
            }
        }
    }

    private void selectOrderInOrderItemCombo(int orderId) {
        for (Order order : ordersList) {
            if (order.getOrderId() == orderId) {
                orderItemOrderComboBox.setValue(order);
                return;
            }
        }
    }

    private void selectMenuItemInOrderItemCombo(int itemId) {
        for (OrderMenuItem item : orderMenuItemsList) {
            if (item.getItemId() == itemId) {
                orderItemMenuItemComboBox.setValue(item);
                return;
            }
        }
    }

    private void selectOrderInPaymentCombo(int orderId) {
        for (Order order : ordersList) {
            if (order.getOrderId() == orderId) {
                paymentOrderComboBox.setValue(order);
                return;
            }
        }
    }

    private void selectOrderInDeliveryCombo(int orderId) {
        for (Order order : ordersList) {
            if (order.getOrderId() == orderId) {
                deliveryOrderComboBox.setValue(order);
                return;
            }
        }
    }

    private void selectDriverInDeliveryCombo(int driverId) {
        for (Driver driver : driversList) {
            if (driver.getDriverId() == driverId) {
                deliveryDriverComboBox.setValue(driver);
                return;
            }
        }
    }



    private void selectOrderInOrderDiscountCombo(int orderId) {
        for (Order order : ordersList) {
            if (order.getOrderId() == orderId) {
                orderDiscountOrderComboBox.setValue(order);
                return;
            }
        }
    }

    private void selectDiscountInOrderDiscountCombo(int discountId) {
        for (Discount discount : discountsList) {
            if (discount.getDiscountId() == discountId) {
                orderDiscountDiscountComboBox.setValue(discount);
                return;
            }
        }
    }

    private void updateOrderTotal(int orderId) {
        String sql =
                "UPDATE `order` " +
                "SET total_price = IFNULL((SELECT SUM(subtotal) FROM order_item WHERE order_id = ?), 0) " +
                "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            statement.setInt(2, orderId);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateOrderFields(OrderCustomer selectedCustomer,
                                        Restaurant selectedRestaurant,
                                        String status,
                                        String totalPriceText) {

        if (selectedCustomer == null || selectedRestaurant == null || status == null || totalPriceText.isEmpty()) {
            showOrderError("Customer, restaurant, status, and total price are required");
            return false;
        }

        double totalPrice;

        try {
            totalPrice = Double.parseDouble(totalPriceText);
        } catch (NumberFormatException e) {
            showOrderError("Total price must be a number");
            return false;
        }

        if (totalPrice < 0) {
            showOrderError("Total price cannot be negative");
            return false;
        }

        return true;
    }

    private boolean validateOrderItemFields(Order selectedOrder,
                                            OrderMenuItem selectedMenuItem,
                                            String quantityText) {

        if (selectedOrder == null || selectedMenuItem == null || quantityText.isEmpty()) {
            showOrderItemError("Order, menu item, and quantity are required");
            return false;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showOrderItemError("Quantity must be a number");
            return false;
        }

        if (quantity <= 0) {
            showOrderItemError("Quantity must be greater than zero");
            return false;
        }

        return true;
    }

    private boolean validatePaymentFields(Order selectedOrder, String method, String status) {
        if (selectedOrder == null || method == null || status == null) {
            showPaymentError("Order, payment method, and payment status are required");
            return false;
        }

        return true;
    }

    private boolean validateDriverFields(String name, String phone, String vehicleType, String availability) {
        if (name.isEmpty() || phone.isEmpty() || vehicleType == null || availability == null) {
            showDriverError("Name, phone, vehicle type, and availability are required");
            return false;
        }

        if (!phone.matches("[0-9]+")) {
            showDriverError("Phone must contain numbers only");
            return false;
        }

        if (phone.length() < 7) {
            showDriverError("Phone number is too short");
            return false;
        }

        return true;
    }

    private boolean validateDeliveryFields(Order selectedOrder, Driver selectedDriver,
                                           String status, String estimatedTime) {
        if (selectedOrder == null || selectedDriver == null || status == null || estimatedTime.isEmpty()) {
            showDeliveryError("Order, driver, status, and estimated time are required");
            return false;
        }

        return true;
    }



    private String getDatePickerValue(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            return "";
        }
        return datePicker.getValue().toString();
    }

    private java.time.LocalDate parseDate(String dateText) {
        if (dateText == null || dateText.trim().isEmpty()) {
            return null;
        }
        try {
            return java.time.LocalDate.parse(dateText);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean validateDiscountFields(String code, String percentageText, String startDate, String endDate) {
        if (code.isEmpty() || percentageText.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            showDiscountError("Code, percentage, start date, and end date are required");
            return false;
        }

        double percentage;

        try {
            percentage = Double.parseDouble(percentageText);
        } catch (NumberFormatException e) {
            showDiscountError("Percentage must be a number");
            return false;
        }

        if (percentage <= 0 || percentage > 100) {
            showDiscountError("Percentage must be between 1 and 100");
            return false;
        }

        return true;
    }

    private void showOrderError(String message) {
        orderMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        orderMessageLabel.setText(message);
    }

    private void showOrderSuccess(String message) {
        orderMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        orderMessageLabel.setText(message);
    }

    private void showOrderItemError(String message) {
        orderItemMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        orderItemMessageLabel.setText(message);
    }

    private void showOrderItemSuccess(String message) {
        orderItemMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        orderItemMessageLabel.setText(message);
    }

    private void showPaymentError(String message) {
        paymentMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        paymentMessageLabel.setText(message);
    }

    private void showPaymentSuccess(String message) {
        paymentMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        paymentMessageLabel.setText(message);
    }

    private void showDriverError(String message) {
        driverMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        driverMessageLabel.setText(message);
    }

    private void showDriverSuccess(String message) {
        driverMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        driverMessageLabel.setText(message);
    }

    private void showDeliveryError(String message) {
        deliveryMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        deliveryMessageLabel.setText(message);
    }

    private void showDeliverySuccess(String message) {
        deliveryMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        deliveryMessageLabel.setText(message);
    }


    private void showDiscountError(String message) {
        discountMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        discountMessageLabel.setText(message);
    }

    private void showDiscountSuccess(String message) {
        discountMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        discountMessageLabel.setText(message);
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
            showOrderError("Cannot open dashboard: " + e.getMessage());
        }
    }
}