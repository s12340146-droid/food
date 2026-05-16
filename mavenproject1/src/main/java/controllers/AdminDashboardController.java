package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AdminDashboardController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    private Timeline clockTimeline;

    @FXML
    public void initialize() {
        updateDateTime();
        startClock();
    }

    private void startClock() {
        clockTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateDateTime())
        );
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }

    private void updateDateTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        dateLabel.setText(currentDate.format(dateFormatter));
        timeLabel.setText(currentTime.format(timeFormatter));
    }

    @FXML
    void showDashboard(ActionEvent event) {
        showInfo("Dashboard", "You are already on the Admin Dashboard.");
    }

    @FXML
    void manageCustomers(ActionEvent event) {
        openPage(event, "/views/CustomersModule.fxml", "Customers Management");
    }

    @FXML
    void manageRestaurants(ActionEvent event) {
        openPage(event, "/views/RestaurantsModule.fxml", "Restaurants Management");
    }

    @FXML
    void manageMenuItems(ActionEvent event) {
        showInfo("Manage Menu Items", "Menu Items module will be added after Restaurants setup.");
    }

    @FXML
void manageOrders(ActionEvent event) {
    openPage(event, "/views/OrdersModule.fxml", "Orders Management");
}


    @FXML
    void logout(ActionEvent event) {
        openPage(event, "/views/Login.fxml", "Food Delivery Login");
    }

    private void openPage(ActionEvent event, String fxmlPath, String title) {
        try {
            if (clockTimeline != null) {
                clockTimeline.stop();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 1200, 750);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Cannot open page: " + title);
        }
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}