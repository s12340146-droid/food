package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.Session;

public class CustomerDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        String username = Session.getUsername();

        if (username == null || username.isEmpty()) {
            welcomeLabel.setText("Welcome, Customer");
        } else {
            welcomeLabel.setText("Welcome, " + username);
        }
    }

   @FXML
void openBrowseRestaurants(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerBrowseRestaurants.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Browse Restaurants");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        showComingSoon("Cannot open Browse Restaurants: " + e.getMessage());
    }
}

   @FXML
void openMyCart(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerCart.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("My Cart");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        showComingSoon("Cannot open My Cart: " + e.getMessage());
    }
}

   @FXML
void openMyOrders(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerOrders.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("My Orders");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        showComingSoon("Cannot open My Orders: " + e.getMessage());
    }
}

   @FXML
void openMyAddresses(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerAddresses.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("My Addresses");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        showComingSoon("Cannot open My Addresses: " + e.getMessage());
    }
}

   @FXML
void openMyNotifications(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerNotifications.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("My Notifications");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        showComingSoon("Cannot open My Notifications: " + e.getMessage());
    }
}

    @FXML
void openMyReviews(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerReviews.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("My Reviews");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        showComingSoon("Cannot open My Reviews: " + e.getMessage());
    }
}

    @FXML
    void logout(ActionEvent event) {
        Session.clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 750);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot return to login: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void showComingSoon(String pageName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(pageName);
        alert.setHeaderText(null);
        alert.setContentText(pageName + " page will be implemented next.");
        alert.showAndWait();
    }
}