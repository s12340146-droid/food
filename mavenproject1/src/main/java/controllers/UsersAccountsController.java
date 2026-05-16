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
import models.Role;
import models.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersAccountsController {

    // ================== USER ACCOUNTS SECTION ==================

    @FXML
    private TableView<UserAccount> usersTable;

    @FXML
    private TableColumn<UserAccount, Integer> userIdColumn;

    @FXML
    private TableColumn<UserAccount, String> usernameColumn;

    @FXML
    private TableColumn<UserAccount, String> emailColumn;

    @FXML
    private TableColumn<UserAccount, String> passwordColumn;

    @FXML
    private TableColumn<UserAccount, Integer> roleIdColumn;

    @FXML
    private TableColumn<UserAccount, String> roleNameColumn;

    @FXML
    private TableColumn<UserAccount, Integer> customerIdColumn;

    @FXML
    private TableColumn<UserAccount, Integer> managerIdColumn;

    @FXML
    private TableColumn<UserAccount, Integer> driverIdColumn;

    @FXML
    private TextField userSearchField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField managerIdField;

    @FXML
    private TextField driverIdField;

    @FXML
    private Label userMessageLabel;

    private final ObservableList<UserAccount> usersList = FXCollections.observableArrayList();


    // ================== ROLES SECTION ==================

    @FXML
    private TableView<Role> rolesTable;

    @FXML
    private TableColumn<Role, Integer> roleTableIdColumn;

    @FXML
    private TableColumn<Role, String> roleTableNameColumn;

    @FXML
    private TextField roleSearchField;

    @FXML
    private TextField roleNameField;

    @FXML
    private Label roleMessageLabel;

    private final ObservableList<Role> rolesList = FXCollections.observableArrayList();


    // ================== INITIALIZE ==================

    @FXML
    public void initialize() {

        // Users table
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleIdColumn.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        roleNameColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));

        // Roles table
        roleTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        roleTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));

        loadRoles();
        loadUsers();

        usersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedUser) -> {
                    if (selectedUser != null) {
                        usernameField.setText(selectedUser.getUsername());
                        emailField.setText(selectedUser.getEmail());
                        passwordField.setText(selectedUser.getPassword());

                        selectRoleInCombo(selectedUser.getRoleId());

                        customerIdField.setText(selectedUser.getCustomerId() == null ? "" : String.valueOf(selectedUser.getCustomerId()));
                        managerIdField.setText(selectedUser.getManagerId() == null ? "" : String.valueOf(selectedUser.getManagerId()));
                        driverIdField.setText(selectedUser.getDriverId() == null ? "" : String.valueOf(selectedUser.getDriverId()));
                    }
                }
        );

        rolesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedRole) -> {
                    if (selectedRole != null) {
                        roleNameField.setText(selectedRole.getRoleName());
                    }
                }
        );
    }


    // ================== USERS CRUD ==================

    @FXML
    void loadUsers() {
        usersList.clear();

        String sql =
                "SELECT ua.user_id, ua.username, ua.email, ua.password, " +
                "ua.role_id, r.role_name, ua.customer_id, ua.manager_id, ua.driver_id " +
                "FROM user_account ua " +
                "JOIN role r ON ua.role_id = r.role_id " +
                "ORDER BY ua.user_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UserAccount user = new UserAccount(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name"),
                        getNullableInt(resultSet, "customer_id"),
                        getNullableInt(resultSet, "manager_id"),
                        getNullableInt(resultSet, "driver_id")
                );

                usersList.add(user);
            }

            usersTable.setItems(usersList);
            showUserSuccess("Users loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showUserError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchUsers(ActionEvent event) {
        String keyword = userSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadUsers();
            return;
        }

        usersList.clear();

        String sql =
                "SELECT ua.user_id, ua.username, ua.email, ua.password, " +
                "ua.role_id, r.role_name, ua.customer_id, ua.manager_id, ua.driver_id " +
                "FROM user_account ua " +
                "JOIN role r ON ua.role_id = r.role_id " +
                "WHERE ua.username LIKE ? " +
                "OR ua.email LIKE ? " +
                "OR r.role_name LIKE ? " +
                "OR CAST(ua.customer_id AS CHAR) LIKE ? " +
                "OR CAST(ua.manager_id AS CHAR) LIKE ? " +
                "OR CAST(ua.driver_id AS CHAR) LIKE ? " +
                "ORDER BY ua.user_id";

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
                UserAccount user = new UserAccount(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name"),
                        getNullableInt(resultSet, "customer_id"),
                        getNullableInt(resultSet, "manager_id"),
                        getNullableInt(resultSet, "driver_id")
                );

                usersList.add(user);
            }

            usersTable.setItems(usersList);
            resultSet.close();

            if (usersList.isEmpty()) {
                showUserError("No users found");
            } else {
                showUserSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showUserError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addUser(ActionEvent event) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        Role selectedRole = roleComboBox.getValue();

        String customerIdText = customerIdField.getText().trim();
        String managerIdText = managerIdField.getText().trim();
        String driverIdText = driverIdField.getText().trim();

        if (!validateUserFields(username, email, password, selectedRole, customerIdText, managerIdText, driverIdText)) {
            return;
        }

        Integer customerId = parseNullableInt(customerIdText);
        Integer managerId = parseNullableInt(managerIdText);
        Integer driverId = parseNullableInt(driverIdText);

        String sql =
                "INSERT INTO user_account (username, email, password, role_id, customer_id, manager_id, driver_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, selectedRole.getRoleId());

            setNullableInt(statement, 5, customerId);
            setNullableInt(statement, 6, managerId);
            setNullableInt(statement, 7, driverId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showUserSuccess("User added successfully");
                clearUserFields();
                loadUsers();
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                showUserError("Username or email already exists");
            } else {
                showUserError("Add failed: " + e.getMessage());
            }
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        UserAccount selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showUserError("Please select a user to update");
            return;
        }

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        Role selectedRole = roleComboBox.getValue();

        String customerIdText = customerIdField.getText().trim();
        String managerIdText = managerIdField.getText().trim();
        String driverIdText = driverIdField.getText().trim();

        if (!validateUserFields(username, email, password, selectedRole, customerIdText, managerIdText, driverIdText)) {
            return;
        }

        Integer customerId = parseNullableInt(customerIdText);
        Integer managerId = parseNullableInt(managerIdText);
        Integer driverId = parseNullableInt(driverIdText);

        String sql =
                "UPDATE user_account " +
                "SET username = ?, email = ?, password = ?, role_id = ?, customer_id = ?, manager_id = ?, driver_id = ? " +
                "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, selectedRole.getRoleId());

            setNullableInt(statement, 5, customerId);
            setNullableInt(statement, 6, managerId);
            setNullableInt(statement, 7, driverId);

            statement.setInt(8, selectedUser.getUserId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showUserSuccess("User updated successfully");
                clearUserFields();
                loadUsers();
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                showUserError("Username or email already exists");
            } else {
                showUserError("Update failed: " + e.getMessage());
            }
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        UserAccount selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showUserError("Please select a user to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete User");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this user account?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM user_account WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedUser.getUserId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showUserSuccess("User deleted successfully");
                clearUserFields();
                loadUsers();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showUserError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearUserFields() {
        usersTable.getSelectionModel().clearSelection();

        usernameField.clear();
        emailField.clear();
        passwordField.clear();

        roleComboBox.getSelectionModel().clearSelection();

        customerIdField.clear();
        managerIdField.clear();
        driverIdField.clear();
        userSearchField.clear();

        userMessageLabel.setText("");
    }


    // ================== ROLES CRUD ==================

    @FXML
    void loadRoles() {
        rolesList.clear();

        String sql = "SELECT role_id, role_name FROM role ORDER BY role_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                );

                rolesList.add(role);
            }

            rolesTable.setItems(rolesList);
            roleComboBox.setItems(rolesList);
            showRoleSuccess("Roles loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showRoleError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchRoles(ActionEvent event) {
        String keyword = roleSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadRoles();
            return;
        }

        rolesList.clear();

        String sql =
                "SELECT role_id, role_name " +
                "FROM role " +
                "WHERE role_name LIKE ? " +
                "ORDER BY role_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("role_name")
                );

                rolesList.add(role);
            }

            rolesTable.setItems(rolesList);
            roleComboBox.setItems(rolesList);
            resultSet.close();

            if (rolesList.isEmpty()) {
                showRoleError("No roles found");
            } else {
                showRoleSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showRoleError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addRole(ActionEvent event) {
        String roleName = roleNameField.getText().trim();

        if (roleName.isEmpty()) {
            showRoleError("Role name is required");
            return;
        }

        String sql = "INSERT INTO role (role_name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roleName);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showRoleSuccess("Role added successfully");
                clearRoleFields();
                loadRoles();
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                showRoleError("Role name already exists");
            } else {
                showRoleError("Add failed: " + e.getMessage());
            }
        }
    }

    @FXML
    void updateRole(ActionEvent event) {
        Role selectedRole = rolesTable.getSelectionModel().getSelectedItem();

        if (selectedRole == null) {
            showRoleError("Please select a role to update");
            return;
        }

        String roleName = roleNameField.getText().trim();

        if (roleName.isEmpty()) {
            showRoleError("Role name is required");
            return;
        }

        String sql = "UPDATE role SET role_name = ? WHERE role_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roleName);
            statement.setInt(2, selectedRole.getRoleId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showRoleSuccess("Role updated successfully");
                clearRoleFields();
                loadRoles();
                loadUsers();
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                showRoleError("Role name already exists");
            } else {
                showRoleError("Update failed: " + e.getMessage());
            }
        }
    }

    @FXML
    void deleteRole(ActionEvent event) {
        Role selectedRole = rolesTable.getSelectionModel().getSelectedItem();

        if (selectedRole == null) {
            showRoleError("Please select a role to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Role");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this role? Users connected to it may prevent deletion.");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM role WHERE role_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedRole.getRoleId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showRoleSuccess("Role deleted successfully");
                clearRoleFields();
                loadRoles();
                loadUsers();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showRoleError("Delete failed: this role may be used by user accounts");
        }
    }

    @FXML
    void clearRoleFields() {
        rolesTable.getSelectionModel().clearSelection();
        roleNameField.clear();
        roleSearchField.clear();
        roleMessageLabel.setText("");
    }


    // ================== HELPERS ==================

    private Integer getNullableInt(ResultSet resultSet, String columnName) throws Exception {
        int value = resultSet.getInt(columnName);

        if (resultSet.wasNull()) {
            return null;
        }

        return value;
    }

    private Integer parseNullableInt(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        return Integer.parseInt(text.trim());
    }

    private void setNullableInt(PreparedStatement statement, int index, Integer value) throws Exception {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }

    private void selectRoleInCombo(int roleId) {
        for (Role role : rolesList) {
            if (role.getRoleId() == roleId) {
                roleComboBox.setValue(role);
                return;
            }
        }
    }

    private boolean validateUserFields(String username,
                                       String email,
                                       String password,
                                       Role selectedRole,
                                       String customerIdText,
                                       String managerIdText,
                                       String driverIdText) {

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || selectedRole == null) {
            showUserError("Username, email, password, and role are required");
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showUserError("Please enter a valid email");
            return false;
        }

        if (!isIntegerOrEmpty(customerIdText)) {
            showUserError("Customer ID must be a number or empty");
            return false;
        }

        if (!isIntegerOrEmpty(managerIdText)) {
            showUserError("Manager ID must be a number or empty");
            return false;
        }

        if (!isIntegerOrEmpty(driverIdText)) {
            showUserError("Driver ID must be a number or empty");
            return false;
        }

        return true;
    }

    private boolean isIntegerOrEmpty(String text) {
        if (text == null || text.trim().isEmpty()) {
            return true;
        }

        try {
            Integer.parseInt(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showUserError(String message) {
        userMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        userMessageLabel.setText(message);
    }

    private void showUserSuccess(String message) {
        userMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        userMessageLabel.setText(message);
    }

    private void showRoleError(String message) {
        roleMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        roleMessageLabel.setText(message);
    }

    private void showRoleSuccess(String message) {
        roleMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        roleMessageLabel.setText(message);
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
            showUserError("Cannot open dashboard: " + e.getMessage());
        }
    }
}