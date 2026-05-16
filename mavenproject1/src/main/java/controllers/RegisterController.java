package controllers;

import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

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
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    void register(ActionEvent event) {
        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || name.isEmpty() || phone.isEmpty()
                || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        if (username.length() < 3) {
            showError("Username must be at least 3 characters");
            return;
        }

        if (!phone.matches("[0-9]+")) {
            showError("Phone must contain numbers only");
            return;
        }

        if (phone.length() < 7) {
            showError("Phone number is too short");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showError("Please enter a valid email");
            return;
        }

        if (password.length() < 4) {
            showError("Password must be at least 4 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        }

        String sql = "INSERT INTO customer (username, name, phone, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (connection == null) {
                showError("Database connection failed");
                return;
            }

            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, password);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showSuccess("Account created successfully");
                clearFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Register failed: " + e.getMessage());
        }
    }

    @FXML
    void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 750);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Food Delivery Login");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Cannot open login page");
        }
    }

    private void clearFields() {
        usernameField.clear();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        messageLabel.setText(message);
    }

    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
        messageLabel.setText(message);
    }
}