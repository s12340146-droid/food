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
import models.CustomerReviewRestaurant;
import models.CustomerReviewView;
import utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerReviewsController {

    @FXML
    private TableView<CustomerReviewView> reviewsTable;

    @FXML
    private TableColumn<CustomerReviewView, Integer> reviewIdColumn;

    @FXML
    private TableColumn<CustomerReviewView, Integer> restaurantIdColumn;

    @FXML
    private TableColumn<CustomerReviewView, String> restaurantNameColumn;

    @FXML
    private TableColumn<CustomerReviewView, Integer> ratingColumn;

    @FXML
    private TableColumn<CustomerReviewView, String> commentColumn;

    @FXML
    private TableColumn<CustomerReviewView, String> dateColumn;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<CustomerReviewRestaurant> restaurantComboBox;

    @FXML
    private ComboBox<Integer> ratingComboBox;

    @FXML
    private TextArea commentArea;

    @FXML
    private Label messageLabel;

    private final ObservableList<CustomerReviewView> reviewsList = FXCollections.observableArrayList();
    private final ObservableList<CustomerReviewRestaurant> restaurantsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        reviewIdColumn.setCellValueFactory(new PropertyValueFactory<>("reviewId"));
        restaurantIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        ratingComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        loadRestaurants();
        loadReviews();

        reviewsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedReview) -> {
                    if (selectedReview != null) {
                        selectRestaurantInCombo(selectedReview.getRestaurantId());
                        ratingComboBox.setValue(selectedReview.getRating());
                        commentArea.setText(selectedReview.getComment());
                    }
                }
        );
    }

    @FXML
    void loadReviews() {
        reviewsList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT rv.review_id, rv.restaurant_id, r.name AS restaurant_name, " +
                "rv.rating, rv.comment " +
                "FROM review rv " +
                "JOIN restaurant r ON rv.restaurant_id = r.restaurant_id " +
                "WHERE rv.customer_id = ? " +
                "ORDER BY rv.review_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerReviewView review = new CustomerReviewView(
                        resultSet.getInt("review_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment"),
                        ""
                );

                reviewsList.add(review);
            }

            reviewsTable.setItems(reviewsList);
            resultSet.close();

            if (reviewsList.isEmpty()) {
                showError("No reviews found");
            } else {
                showSuccess("Reviews loaded successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchReviews(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadReviews();
            return;
        }

        reviewsList.clear();

        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        String sql =
                "SELECT rv.review_id, rv.restaurant_id, r.name AS restaurant_name, " +
                "rv.rating, rv.comment " +
                "FROM review rv " +
                "JOIN restaurant r ON rv.restaurant_id = r.restaurant_id " +
                "WHERE rv.customer_id = ? AND " +
                "(r.name LIKE ? OR CAST(rv.rating AS CHAR) LIKE ? OR rv.comment LIKE ?) " +
                "ORDER BY rv.review_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setInt(1, customerId);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerReviewView review = new CustomerReviewView(
                        resultSet.getInt("review_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment"),
                        ""
                );

                reviewsList.add(review);
            }

            reviewsTable.setItems(reviewsList);
            resultSet.close();

            if (reviewsList.isEmpty()) {
                showError("No reviews found");
            } else {
                showSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addReview(ActionEvent event) {
        int customerId = Session.getCustomerId();

        if (customerId <= 0) {
            showError("No customer session found. Please login again.");
            return;
        }

        CustomerReviewRestaurant selectedRestaurant = restaurantComboBox.getValue();
        Integer rating = ratingComboBox.getValue();
        String comment = commentArea.getText().trim();

        if (!validateReviewFields(selectedRestaurant, rating, comment)) {
            return;
        }

        String sql =
                "INSERT INTO review (customer_id, restaurant_id, rating, comment) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.setInt(2, selectedRestaurant.getRestaurantId());
            statement.setInt(3, rating);
            statement.setString(4, comment);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Review added successfully");
                clearFields();
                loadReviews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateReview(ActionEvent event) {
        CustomerReviewView selectedReview = reviewsTable.getSelectionModel().getSelectedItem();

        if (selectedReview == null) {
            showError("Please select a review to update");
            return;
        }

        CustomerReviewRestaurant selectedRestaurant = restaurantComboBox.getValue();
        Integer rating = ratingComboBox.getValue();
        String comment = commentArea.getText().trim();

        if (!validateReviewFields(selectedRestaurant, rating, comment)) {
            return;
        }

        String sql =
                "UPDATE review " +
                "SET restaurant_id = ?, rating = ?, comment = ? " +
                "WHERE review_id = ? AND customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedRestaurant.getRestaurantId());
            statement.setInt(2, rating);
            statement.setString(3, comment);
            statement.setInt(4, selectedReview.getReviewId());
            statement.setInt(5, Session.getCustomerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Review updated successfully");
                clearFields();
                loadReviews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteReview(ActionEvent event) {
        CustomerReviewView selectedReview = reviewsTable.getSelectionModel().getSelectedItem();

        if (selectedReview == null) {
            showError("Please select a review to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Review");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this review?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql =
                "DELETE FROM review " +
                "WHERE review_id = ? AND customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedReview.getReviewId());
            statement.setInt(2, Session.getCustomerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Review deleted successfully");
                clearFields();
                loadReviews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Delete failed: " + e.getMessage());
        }
    }

    private void loadRestaurants() {
        restaurantsList.clear();

        String sql = "SELECT restaurant_id, name FROM restaurant ORDER BY restaurant_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CustomerReviewRestaurant restaurant = new CustomerReviewRestaurant(
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("name")
                );

                restaurantsList.add(restaurant);
            }

            restaurantComboBox.setItems(restaurantsList);

        } catch (Exception e) {
            e.printStackTrace();
            showError("Restaurants load failed: " + e.getMessage());
        }
    }

    private void selectRestaurantInCombo(int restaurantId) {
        for (CustomerReviewRestaurant restaurant : restaurantsList) {
            if (restaurant.getRestaurantId() == restaurantId) {
                restaurantComboBox.setValue(restaurant);
                return;
            }
        }
    }

    private boolean validateReviewFields(CustomerReviewRestaurant selectedRestaurant,
                                         Integer rating,
                                         String comment) {
        if (selectedRestaurant == null || rating == null || comment.isEmpty()) {
            showError("Restaurant, rating, and comment are required");
            return false;
        }

        if (rating < 1 || rating > 5) {
            showError("Rating must be between 1 and 5");
            return false;
        }

        return true;
    }

    @FXML
    void clearFields() {
        reviewsTable.getSelectionModel().clearSelection();
        restaurantComboBox.getSelectionModel().clearSelection();
        ratingComboBox.getSelectionModel().clearSelection();
        commentArea.clear();
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