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
import models.CustomerNotificationView;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerNotificationsController {

    @FXML
    private TableView<CustomerNotificationView> notificationsTable;

    @FXML
    private TableColumn<CustomerNotificationView, Integer> notificationIdColumn;

    @FXML
    private TableColumn<CustomerNotificationView, String> messageColumn;

    @FXML
    private TableColumn<CustomerNotificationView, String> dateColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea messageArea;

    @FXML
    private Label messageLabel;

    private final ObservableList<CustomerNotificationView> notificationsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        notificationIdColumn.setCellValueFactory(new PropertyValueFactory<>("notificationId"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadNotifications();

        notificationsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedNotification) -> {
                    if (selectedNotification != null) {
                        messageArea.setText(selectedNotification.getMessage());
                    }
                }
        );
    }

    @FXML
    void loadNotifications() {
        notificationsList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT notification_id, message, date " +
                "FROM notification " +
                "WHERE customer_id = ? " +
                "ORDER BY date DESC, notification_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerNotificationView notification = new CustomerNotificationView(
                        resultSet.getInt("notification_id"),
                        resultSet.getString("message"),
                        resultSet.getString("date")
                );

                notificationsList.add(notification);
            }

            notificationsTable.setItems(notificationsList);
            resultSet.close();

            if (notificationsList.isEmpty()) {
                showError("No notifications found");
            } else {
                showSuccess("Notifications loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchNotifications(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadNotifications();
            return;
        }

        notificationsList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT notification_id, message, date " +
                "FROM notification " +
                "WHERE customer_id = ? AND " +
                "(message LIKE ? OR CAST(date AS CHAR) LIKE ?) " +
                "ORDER BY date DESC, notification_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setInt(1, customerId);
            statement.setString(2, value);
            statement.setString(3, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerNotificationView notification = new CustomerNotificationView(
                        resultSet.getInt("notification_id"),
                        resultSet.getString("message"),
                        resultSet.getString("date")
                );

                notificationsList.add(notification);
            }

            notificationsTable.setItems(notificationsList);
            resultSet.close();

            if (notificationsList.isEmpty()) {
                showError("No notifications found");
            } else {
                showSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteNotification(ActionEvent event) {
        CustomerNotificationView selectedNotification =
                notificationsTable.getSelectionModel().getSelectedItem();

        if (selectedNotification == null) {
            showError("Please select a notification to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Notification");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this notification?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql =
                "DELETE FROM notification " +
                "WHERE notification_id = ? AND customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedNotification.getNotificationId());
            statement.setInt(2, Session.getCustomerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Notification deleted successfully");
                clearFields();
                loadNotifications();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearFields() {
        notificationsTable.getSelectionModel().clearSelection();
        messageArea.clear();
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