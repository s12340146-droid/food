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
import models.Restaurant;
import models.Category;
import models.Menu;
import models.MenuItem;
import models.RestaurantManager;
import models.RestaurantReview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RestaurantsModuleController {

    // ================== RESTAURANTS SECTION ==================

    @FXML
    private TableView<Restaurant> restaurantsTable;

    @FXML
    private TableColumn<Restaurant, Integer> restaurantIdColumn;

    @FXML
    private TableColumn<Restaurant, String> restaurantNameColumn;

    @FXML
    private TableColumn<Restaurant, String> restaurantPhoneColumn;

    @FXML
    private TableColumn<Restaurant, String> restaurantLocationColumn;

    @FXML
    private TableColumn<Restaurant, Double> restaurantRatingColumn;

    @FXML
    private TextField restaurantSearchField;

    @FXML
    private TextField restaurantNameField;

    @FXML
    private TextField restaurantPhoneField;

    @FXML
    private TextField restaurantLocationField;

    @FXML
    private TextField restaurantRatingField;

    @FXML
    private Label restaurantMessageLabel;

    private final ObservableList<Restaurant> restaurantsList = FXCollections.observableArrayList();


    // ================== RESTAURANT MANAGERS SECTION ==================

    @FXML
    private TableView<RestaurantManager> managersTable;

    @FXML
    private TableColumn<RestaurantManager, Integer> managerIdColumn;

    @FXML
    private TableColumn<RestaurantManager, String> managerNameColumn;

    @FXML
    private TableColumn<RestaurantManager, String> managerPhoneColumn;

    @FXML
    private TableColumn<RestaurantManager, Integer> managerRestaurantIdColumn;

    @FXML
    private TableColumn<RestaurantManager, String> managerRestaurantNameColumn;

    @FXML
    private TextField managerSearchField;

    @FXML
    private TextField managerNameField;

    @FXML
    private TextField managerPhoneField;

    @FXML
    private ComboBox<Restaurant> managerRestaurantComboBox;

    @FXML
    private Label managerMessageLabel;

    private final ObservableList<RestaurantManager> managersList = FXCollections.observableArrayList();


    // ================== CATEGORIES SECTION ==================

    @FXML
    private TableView<Category> categoriesTable;

    @FXML
    private TableColumn<Category, Integer> categoryIdColumn;

    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    private TextField categorySearchField;

    @FXML
    private TextField categoryNameField;

    @FXML
    private Label categoryMessageLabel;

    private final ObservableList<Category> categoriesList = FXCollections.observableArrayList();


    // ================== MENUS SECTION ==================

    @FXML
    private TableView<Menu> menusTable;

    @FXML
    private TableColumn<Menu, Integer> menuIdColumn;

    @FXML
    private TableColumn<Menu, Integer> menuRestaurantIdColumn;

    @FXML
    private TableColumn<Menu, String> menuRestaurantNameColumn;

    @FXML
    private TextField menuSearchField;

    @FXML
    private ComboBox<Restaurant> menuRestaurantComboBox;

    @FXML
    private Label menuMessageLabel;

    private final ObservableList<Menu> menusList = FXCollections.observableArrayList();


    // ================== MENU ITEMS SECTION ==================

    @FXML
    private TableView<MenuItem> menuItemsTable;

    @FXML
    private TableColumn<MenuItem, Integer> menuItemIdColumn;

    @FXML
    private TableColumn<MenuItem, String> menuItemNameColumn;

    @FXML
    private TableColumn<MenuItem, String> menuItemDescriptionColumn;

    @FXML
    private TableColumn<MenuItem, Double> menuItemPriceColumn;

    @FXML
    private TableColumn<MenuItem, String> menuItemAvailabilityColumn;

    @FXML
    private TableColumn<MenuItem, String> menuItemRestaurantColumn;

    @FXML
    private TableColumn<MenuItem, String> menuItemCategoryColumn;

    @FXML
    private TextField menuItemSearchField;

    @FXML
    private TextField menuItemNameField;

    @FXML
    private TextField menuItemDescriptionField;

    @FXML
    private TextField menuItemPriceField;

    @FXML
    private ComboBox<String> menuItemAvailabilityComboBox;

    @FXML
    private ComboBox<Menu> menuItemMenuComboBox;

    @FXML
    private ComboBox<Category> menuItemCategoryComboBox;

    @FXML
    private Label menuItemMessageLabel;

    private final ObservableList<MenuItem> menuItemsList = FXCollections.observableArrayList();


    // ================== RESTAURANT REVIEWS SECTION ==================

    @FXML
    private TableView<RestaurantReview> reviewsTable;

    @FXML
    private TableColumn<RestaurantReview, Integer> reviewIdColumn;

    @FXML
    private TableColumn<RestaurantReview, Integer> reviewCustomerIdColumn;

    @FXML
    private TableColumn<RestaurantReview, String> reviewCustomerNameColumn;

    @FXML
    private TableColumn<RestaurantReview, Integer> reviewRestaurantIdColumn;

    @FXML
    private TableColumn<RestaurantReview, String> reviewRestaurantNameColumn;

    @FXML
    private TableColumn<RestaurantReview, Integer> reviewRatingColumn;

    @FXML
    private TableColumn<RestaurantReview, String> reviewCommentColumn;

    @FXML
    private TextField reviewSearchField;

    @FXML
    private Label reviewMessageLabel;

    private final ObservableList<RestaurantReview> reviewsList = FXCollections.observableArrayList();


    // ================== INITIALIZE ==================

    @FXML
    public void initialize() {

        // Restaurants table setup
        restaurantIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        restaurantPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        restaurantLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        restaurantRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        loadRestaurants();

        restaurantsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedRestaurant) -> {
                    if (selectedRestaurant != null) {
                        restaurantNameField.setText(selectedRestaurant.getName());
                        restaurantPhoneField.setText(selectedRestaurant.getPhone());
                        restaurantLocationField.setText(selectedRestaurant.getLocation());
                        restaurantRatingField.setText(String.valueOf(selectedRestaurant.getRating()));
                    }
                }
        );


        // Restaurant Managers table setup
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        managerRestaurantIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        managerRestaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));

        loadManagerComboBox();
        loadManagers();

        managersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedManager) -> {
                    if (selectedManager != null) {
                        managerNameField.setText(selectedManager.getName());
                        managerPhoneField.setText(selectedManager.getPhone());
                        selectRestaurantInManagerCombo(selectedManager.getRestaurantId());
                    }
                }
        );


        // Categories table setup
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        loadCategories();

        categoriesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedCategory) -> {
                    if (selectedCategory != null) {
                        categoryNameField.setText(selectedCategory.getCategoryName());
                    }
                }
        );


        // Menus table setup
        menuIdColumn.setCellValueFactory(new PropertyValueFactory<>("menuId"));
        menuRestaurantIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        menuRestaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));

        loadMenuComboBox();
        loadMenus();

        menusTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedMenu) -> {
                    if (selectedMenu != null) {
                        selectRestaurantInMenuCombo(selectedMenu.getRestaurantId());
                    }
                }
        );


        // Menu Items table setup
        menuItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        menuItemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        menuItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        menuItemAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        menuItemRestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        menuItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        menuItemAvailabilityComboBox.setItems(
                FXCollections.observableArrayList("Available", "Unavailable")
        );

        loadMenuItemComboBoxes();
        loadMenuItems();

        menuItemsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedItem) -> {
                    if (selectedItem != null) {
                        menuItemNameField.setText(selectedItem.getItemName());
                        menuItemDescriptionField.setText(selectedItem.getDescription());
                        menuItemPriceField.setText(String.valueOf(selectedItem.getPrice()));
                        menuItemAvailabilityComboBox.setValue(selectedItem.getAvailability());

                        selectMenuInMenuItemCombo(selectedItem.getMenuId());
                        selectCategoryInMenuItemCombo(selectedItem.getCategoryId());
                    }
                }
        );


        // Restaurant Reviews table setup
        reviewIdColumn.setCellValueFactory(new PropertyValueFactory<>("reviewId"));
        reviewCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        reviewCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        reviewRestaurantIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));
        reviewRestaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        reviewRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        reviewCommentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        loadReviews();
    }


    // ================== RESTAURANTS CRUD ==================

    @FXML
    void loadRestaurants() {
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

            restaurantsTable.setItems(restaurantsList);
            loadMenuComboBox();
            loadManagerComboBox();
            showRestaurantSuccess("Restaurants loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showRestaurantError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchRestaurants(ActionEvent event) {
        String keyword = restaurantSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadRestaurants();
            return;
        }

        restaurantsList.clear();

        String sql =
                "SELECT restaurant_id, name, phone, location, rating " +
                "FROM restaurant " +
                "WHERE name LIKE ? OR phone LIKE ? OR location LIKE ? " +
                "ORDER BY restaurant_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);

            ResultSet resultSet = statement.executeQuery();

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

            restaurantsTable.setItems(restaurantsList);
            loadMenuComboBox();
            loadManagerComboBox();
            resultSet.close();

            if (restaurantsList.isEmpty()) {
                showRestaurantError("No restaurants found");
            } else {
                showRestaurantSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showRestaurantError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addRestaurant(ActionEvent event) {
        String name = restaurantNameField.getText().trim();
        String phone = restaurantPhoneField.getText().trim();
        String location = restaurantLocationField.getText().trim();
        String ratingText = restaurantRatingField.getText().trim();

        if (!validateRestaurantFields(name, phone, ratingText)) {
            return;
        }

        double rating = Double.parseDouble(ratingText);

        String sql = "INSERT INTO restaurant (name, phone, location, rating) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);

            if (location.isEmpty()) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, location);
            }

            statement.setDouble(4, rating);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showRestaurantSuccess("Restaurant added successfully");
                clearRestaurantFields();
                loadRestaurants();
                loadMenus();
                loadManagers();
                loadMenuItemComboBoxes();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showRestaurantError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateRestaurant(ActionEvent event) {
        Restaurant selectedRestaurant = restaurantsTable.getSelectionModel().getSelectedItem();

        if (selectedRestaurant == null) {
            showRestaurantError("Please select a restaurant to update");
            return;
        }

        String name = restaurantNameField.getText().trim();
        String phone = restaurantPhoneField.getText().trim();
        String location = restaurantLocationField.getText().trim();
        String ratingText = restaurantRatingField.getText().trim();

        if (!validateRestaurantFields(name, phone, ratingText)) {
            return;
        }

        double rating = Double.parseDouble(ratingText);

        String sql =
                "UPDATE restaurant " +
                "SET name = ?, phone = ?, location = ?, rating = ? " +
                "WHERE restaurant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);

            if (location.isEmpty()) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, location);
            }

            statement.setDouble(4, rating);
            statement.setInt(5, selectedRestaurant.getRestaurantId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showRestaurantSuccess("Restaurant updated successfully");
                clearRestaurantFields();
                loadRestaurants();
                loadMenus();
                loadManagers();
                loadMenuItems();
                loadReviews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showRestaurantError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteRestaurant(ActionEvent event) {
        Restaurant selectedRestaurant = restaurantsTable.getSelectionModel().getSelectedItem();

        if (selectedRestaurant == null) {
            showRestaurantError("Please select a restaurant to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Restaurant");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(
                "Are you sure you want to delete this restaurant? Related menus, menu items, orders, managers, and reviews may also be deleted."
        );

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedRestaurant.getRestaurantId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showRestaurantSuccess("Restaurant deleted successfully");
                clearRestaurantFields();
                loadRestaurants();
                loadMenus();
                loadManagers();
                loadMenuItems();
                loadReviews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showRestaurantError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearRestaurantFields() {
        restaurantsTable.getSelectionModel().clearSelection();
        restaurantNameField.clear();
        restaurantPhoneField.clear();
        restaurantLocationField.clear();
        restaurantRatingField.clear();
        restaurantSearchField.clear();
        restaurantMessageLabel.setText("");
    }


    // ================== RESTAURANT MANAGERS CRUD ==================

    @FXML
    void loadManagers() {
        managersList.clear();

        String sql =
                "SELECT rm.manager_id, rm.restaurant_id, r.name AS restaurant_name, rm.name, rm.phone " +
                "FROM restaurant_manager rm " +
                "JOIN restaurant r ON rm.restaurant_id = r.restaurant_id " +
                "ORDER BY rm.manager_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RestaurantManager manager = new RestaurantManager(
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("name"),
                        resultSet.getString("phone")
                );

                managersList.add(manager);
            }

            managersTable.setItems(managersList);
            showManagerSuccess("Managers loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showManagerError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchManagers(ActionEvent event) {
        String keyword = managerSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadManagers();
            return;
        }

        managersList.clear();

        String sql =
                "SELECT rm.manager_id, rm.restaurant_id, r.name AS restaurant_name, rm.name, rm.phone " +
                "FROM restaurant_manager rm " +
                "JOIN restaurant r ON rm.restaurant_id = r.restaurant_id " +
                "WHERE rm.name LIKE ? OR rm.phone LIKE ? OR r.name LIKE ? " +
                "ORDER BY rm.manager_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RestaurantManager manager = new RestaurantManager(
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getString("name"),
                        resultSet.getString("phone")
                );

                managersList.add(manager);
            }

            managersTable.setItems(managersList);
            resultSet.close();

            if (managersList.isEmpty()) {
                showManagerError("No managers found");
            } else {
                showManagerSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showManagerError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addManager(ActionEvent event) {
        String name = managerNameField.getText().trim();
        String phone = managerPhoneField.getText().trim();
        Restaurant selectedRestaurant = managerRestaurantComboBox.getValue();

        if (!validateManagerFields(name, phone, selectedRestaurant)) {
            return;
        }

        String sql = "INSERT INTO restaurant_manager (name, phone, restaurant_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setInt(3, selectedRestaurant.getRestaurantId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showManagerSuccess("Manager added successfully");
                clearManagerFields();
                loadManagers();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showManagerError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateManager(ActionEvent event) {
        RestaurantManager selectedManager = managersTable.getSelectionModel().getSelectedItem();

        if (selectedManager == null) {
            showManagerError("Please select a manager to update");
            return;
        }

        String name = managerNameField.getText().trim();
        String phone = managerPhoneField.getText().trim();
        Restaurant selectedRestaurant = managerRestaurantComboBox.getValue();

        if (!validateManagerFields(name, phone, selectedRestaurant)) {
            return;
        }

        String sql =
                "UPDATE restaurant_manager " +
                "SET name = ?, phone = ?, restaurant_id = ? " +
                "WHERE manager_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setInt(3, selectedRestaurant.getRestaurantId());
            statement.setInt(4, selectedManager.getManagerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showManagerSuccess("Manager updated successfully");
                clearManagerFields();
                loadManagers();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showManagerError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteManager(ActionEvent event) {
        RestaurantManager selectedManager = managersTable.getSelectionModel().getSelectedItem();

        if (selectedManager == null) {
            showManagerError("Please select a manager to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Manager");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this manager?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM restaurant_manager WHERE manager_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedManager.getManagerId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showManagerSuccess("Manager deleted successfully");
                clearManagerFields();
                loadManagers();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showManagerError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearManagerFields() {
        managersTable.getSelectionModel().clearSelection();
        managerNameField.clear();
        managerPhoneField.clear();
        managerSearchField.clear();
        managerRestaurantComboBox.getSelectionModel().clearSelection();
        managerMessageLabel.setText("");
    }

    private void loadManagerComboBox() {
        managerRestaurantComboBox.setItems(restaurantsList);
    }

    private void selectRestaurantInManagerCombo(int restaurantId) {
        for (Restaurant restaurant : restaurantsList) {
            if (restaurant.getRestaurantId() == restaurantId) {
                managerRestaurantComboBox.setValue(restaurant);
                return;
            }
        }
    }


    // ================== CATEGORIES CRUD ==================

    @FXML
    void loadCategories() {
        categoriesList.clear();

        String sql = "SELECT category_id, category_name FROM category ORDER BY category_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );

                categoriesList.add(category);
            }

            categoriesTable.setItems(categoriesList);
            loadMenuItemComboBoxes();
            showCategorySuccess("Categories loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showCategoryError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchCategories(ActionEvent event) {
        String keyword = categorySearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadCategories();
            return;
        }

        categoriesList.clear();

        String sql =
                "SELECT category_id, category_name " +
                "FROM category " +
                "WHERE category_name LIKE ? " +
                "ORDER BY category_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";
            statement.setString(1, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );

                categoriesList.add(category);
            }

            categoriesTable.setItems(categoriesList);
            loadMenuItemComboBoxes();
            resultSet.close();

            if (categoriesList.isEmpty()) {
                showCategoryError("No categories found");
            } else {
                showCategorySuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCategoryError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addCategory(ActionEvent event) {
        String categoryName = categoryNameField.getText().trim();

        if (!validateCategoryFields(categoryName)) {
            return;
        }

        String sql = "INSERT INTO category (category_name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, categoryName);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCategorySuccess("Category added successfully");
                clearCategoryFields();
                loadCategories();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCategoryError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateCategory(ActionEvent event) {
        Category selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            showCategoryError("Please select a category to update");
            return;
        }

        String categoryName = categoryNameField.getText().trim();

        if (!validateCategoryFields(categoryName)) {
            return;
        }

        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, categoryName);
            statement.setInt(2, selectedCategory.getCategoryId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCategorySuccess("Category updated successfully");
                clearCategoryFields();
                loadCategories();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCategoryError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteCategory(ActionEvent event) {
        Category selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            showCategoryError("Please select a category to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Category");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(
                "Are you sure you want to delete this category? Menu items using this category may be affected."
        );

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM category WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedCategory.getCategoryId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showCategorySuccess("Category deleted successfully");
                clearCategoryFields();
                loadCategories();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showCategoryError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearCategoryFields() {
        categoriesTable.getSelectionModel().clearSelection();
        categoryNameField.clear();
        categorySearchField.clear();
        categoryMessageLabel.setText("");
    }


    // ================== MENUS CRUD ==================

    @FXML
    void loadMenus() {
        menusList.clear();

        String sql =
                "SELECT m.menu_id, r.restaurant_id, r.name AS restaurant_name " +
                "FROM menu m " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "ORDER BY m.menu_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Menu menu = new Menu(
                        resultSet.getInt("menu_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name")
                );

                menusList.add(menu);
            }

            menusTable.setItems(menusList);
            loadMenuItemComboBoxes();
            showMenuSuccess("Menus loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showMenuError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchMenus(ActionEvent event) {
        String keyword = menuSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadMenus();
            return;
        }

        menusList.clear();

        String sql =
                "SELECT m.menu_id, r.restaurant_id, r.name AS restaurant_name " +
                "FROM menu m " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "WHERE r.name LIKE ? " +
                "ORDER BY m.menu_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";
            statement.setString(1, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Menu menu = new Menu(
                        resultSet.getInt("menu_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name")
                );

                menusList.add(menu);
            }

            menusTable.setItems(menusList);
            loadMenuItemComboBoxes();
            resultSet.close();

            if (menusList.isEmpty()) {
                showMenuError("No menus found");
            } else {
                showMenuSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addMenu(ActionEvent event) {
        Restaurant selectedRestaurant = menuRestaurantComboBox.getValue();

        if (selectedRestaurant == null) {
            showMenuError("Please select a restaurant");
            return;
        }

        String sql = "INSERT INTO menu (restaurant_id) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedRestaurant.getRestaurantId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showMenuSuccess("Menu added successfully");
                clearMenuFields();
                loadMenus();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateMenu(ActionEvent event) {
        Menu selectedMenu = menusTable.getSelectionModel().getSelectedItem();

        if (selectedMenu == null) {
            showMenuError("Please select a menu to update");
            return;
        }

        Restaurant selectedRestaurant = menuRestaurantComboBox.getValue();

        if (selectedRestaurant == null) {
            showMenuError("Please select a restaurant");
            return;
        }

        String sql = "UPDATE menu SET restaurant_id = ? WHERE menu_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedRestaurant.getRestaurantId());
            statement.setInt(2, selectedMenu.getMenuId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showMenuSuccess("Menu updated successfully");
                clearMenuFields();
                loadMenus();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteMenu(ActionEvent event) {
        Menu selectedMenu = menusTable.getSelectionModel().getSelectedItem();

        if (selectedMenu == null) {
            showMenuError("Please select a menu to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Menu");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(
                "Are you sure you want to delete this menu? Related menu items may also be deleted."
        );

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM menu WHERE menu_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedMenu.getMenuId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showMenuSuccess("Menu deleted successfully");
                clearMenuFields();
                loadMenus();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearMenuFields() {
        menusTable.getSelectionModel().clearSelection();
        menuRestaurantComboBox.getSelectionModel().clearSelection();
        menuSearchField.clear();
        menuMessageLabel.setText("");
    }

    private void loadMenuComboBox() {
        menuRestaurantComboBox.setItems(restaurantsList);
    }

    private void selectRestaurantInMenuCombo(int restaurantId) {
        for (Restaurant restaurant : restaurantsList) {
            if (restaurant.getRestaurantId() == restaurantId) {
                menuRestaurantComboBox.setValue(restaurant);
                return;
            }
        }
    }


    // ================== MENU ITEMS CRUD ==================

    @FXML
    void loadMenuItems() {
        menuItemsList.clear();

        String sql =
                "SELECT mi.item_id, mi.name AS item_name, mi.description, mi.price, mi.availability, " +
                "m.menu_id, r.name AS restaurant_name, " +
                "c.category_id, c.category_name " +
                "FROM menu_item mi " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "LEFT JOIN category c ON mi.category_id = c.category_id " +
                "ORDER BY mi.item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                MenuItem item = new MenuItem(
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("availability") == 1 ? "Available" : "Unavailable",
                        resultSet.getInt("menu_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );

                menuItemsList.add(item);
            }

            menuItemsTable.setItems(menuItemsList);
            showMenuItemSuccess("Menu items loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showMenuItemError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    void searchMenuItems(ActionEvent event) {
        String keyword = menuItemSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadMenuItems();
            return;
        }

        menuItemsList.clear();

        String sql =
                "SELECT mi.item_id, mi.name AS item_name, mi.description, mi.price, mi.availability, " +
                "m.menu_id, r.name AS restaurant_name, " +
                "c.category_id, c.category_name " +
                "FROM menu_item mi " +
                "JOIN menu m ON mi.menu_id = m.menu_id " +
                "JOIN restaurant r ON m.restaurant_id = r.restaurant_id " +
                "LEFT JOIN category c ON mi.category_id = c.category_id " +
                "WHERE mi.name LIKE ? OR mi.description LIKE ? OR r.name LIKE ? OR c.category_name LIKE ? " +
                "ORDER BY mi.item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MenuItem item = new MenuItem(
                        resultSet.getInt("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("availability") == 1 ? "Available" : "Unavailable",
                        resultSet.getInt("menu_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );

                menuItemsList.add(item);
            }

            menuItemsTable.setItems(menuItemsList);
            resultSet.close();

            if (menuItemsList.isEmpty()) {
                showMenuItemError("No menu items found");
            } else {
                showMenuItemSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuItemError("Search failed: " + e.getMessage());
        }
    }

    @FXML
    void addMenuItem(ActionEvent event) {
        String name = menuItemNameField.getText().trim();
        String description = menuItemDescriptionField.getText().trim();
        String priceText = menuItemPriceField.getText().trim();
        String availability = menuItemAvailabilityComboBox.getValue();

        Menu selectedMenu = menuItemMenuComboBox.getValue();
        Category selectedCategory = menuItemCategoryComboBox.getValue();

        if (!validateMenuItemFields(name, priceText, availability, selectedMenu, selectedCategory)) {
            return;
        }

        double price = Double.parseDouble(priceText);
        int availabilityValue = availability.equals("Available") ? 1 : 0;

        String sql =
                "INSERT INTO menu_item (name, description, price, availability, menu_id, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            if (description.isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, description);
            }

            statement.setDouble(3, price);
            statement.setInt(4, availabilityValue);
            statement.setInt(5, selectedMenu.getMenuId());
            statement.setInt(6, selectedCategory.getCategoryId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showMenuItemSuccess("Menu item added successfully");
                clearMenuItemFields();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuItemError("Add failed: " + e.getMessage());
        }
    }

    @FXML
    void updateMenuItem(ActionEvent event) {
        MenuItem selectedItem = menuItemsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showMenuItemError("Please select a menu item to update");
            return;
        }

        String name = menuItemNameField.getText().trim();
        String description = menuItemDescriptionField.getText().trim();
        String priceText = menuItemPriceField.getText().trim();
        String availability = menuItemAvailabilityComboBox.getValue();

        Menu selectedMenu = menuItemMenuComboBox.getValue();
        Category selectedCategory = menuItemCategoryComboBox.getValue();

        if (!validateMenuItemFields(name, priceText, availability, selectedMenu, selectedCategory)) {
            return;
        }

        double price = Double.parseDouble(priceText);
        int availabilityValue = availability.equals("Available") ? 1 : 0;

        String sql =
                "UPDATE menu_item " +
                "SET name = ?, description = ?, price = ?, availability = ?, menu_id = ?, category_id = ? " +
                "WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            if (description.isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, description);
            }

            statement.setDouble(3, price);
            statement.setInt(4, availabilityValue);
            statement.setInt(5, selectedMenu.getMenuId());
            statement.setInt(6, selectedCategory.getCategoryId());
            statement.setInt(7, selectedItem.getItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showMenuItemSuccess("Menu item updated successfully");
                clearMenuItemFields();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuItemError("Update failed: " + e.getMessage());
        }
    }

    @FXML
    void deleteMenuItem(ActionEvent event) {
        MenuItem selectedItem = menuItemsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showMenuItemError("Please select a menu item to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Menu Item");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this menu item?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        String sql = "DELETE FROM menu_item WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, selectedItem.getItemId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                showMenuItemSuccess("Menu item deleted successfully");
                clearMenuItemFields();
                loadMenuItems();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMenuItemError("Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void clearMenuItemFields() {
        menuItemsTable.getSelectionModel().clearSelection();
        menuItemNameField.clear();
        menuItemDescriptionField.clear();
        menuItemPriceField.clear();
        menuItemSearchField.clear();
        menuItemAvailabilityComboBox.getSelectionModel().clearSelection();
        menuItemMenuComboBox.getSelectionModel().clearSelection();
        menuItemCategoryComboBox.getSelectionModel().clearSelection();
        menuItemMessageLabel.setText("");
    }

    private void loadMenuItemComboBoxes() {
        menuItemMenuComboBox.setItems(menusList);
        menuItemCategoryComboBox.setItems(categoriesList);
    }

    private void selectMenuInMenuItemCombo(int menuId) {
        for (Menu menu : menusList) {
            if (menu.getMenuId() == menuId) {
                menuItemMenuComboBox.setValue(menu);
                return;
            }
        }
    }

    private void selectCategoryInMenuItemCombo(int categoryId) {
        for (Category category : categoriesList) {
            if (category.getCategoryId() == categoryId) {
                menuItemCategoryComboBox.setValue(category);
                return;
            }
        }
    }


    // ================== RESTAURANT REVIEWS CRUD ==================

   @FXML
void loadReviews() {
    reviewsList.clear();

    String sql =
            "SELECT rr.review_id, c.customer_id, c.name AS customer_name, " +
            "r.restaurant_id, r.name AS restaurant_name, rr.rating, rr.comment " +
            "FROM review rr " +
            "JOIN customer c ON rr.customer_id = c.customer_id " +
            "JOIN restaurant r ON rr.restaurant_id = r.restaurant_id " +
            "ORDER BY rr.review_id";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            RestaurantReview review = new RestaurantReview(
                    resultSet.getInt("review_id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getInt("restaurant_id"),
                    resultSet.getString("restaurant_name"),
                    resultSet.getInt("rating"),
                    resultSet.getString("comment")
            );

            reviewsList.add(review);
        }

        reviewsTable.setItems(reviewsList);
        showReviewSuccess("Reviews loaded successfully");

    } catch (Exception e) {
        e.printStackTrace();
        showReviewError("Load failed: " + e.getMessage());
    }
}

    @FXML
    void searchReviews(ActionEvent event) {
        String keyword = reviewSearchField.getText().trim();

        if (keyword.isEmpty()) {
            loadReviews();
            return;
        }

        reviewsList.clear();

        String sql =
                "SELECT rr.review_id, c.customer_id, c.name AS customer_name, " +
                "r.restaurant_id, r.name AS restaurant_name, rr.rating, rr.comment " +
                "FROM restaurant_review rr " +
                "JOIN customer c ON rr.customer_id = c.customer_id " +
                "JOIN restaurant r ON rr.restaurant_id = r.restaurant_id " +
                "WHERE c.name LIKE ? OR r.name LIKE ? OR rr.comment LIKE ? OR CAST(rr.rating AS CHAR) LIKE ? " +
                "ORDER BY rr.review_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RestaurantReview review = new RestaurantReview(
                        resultSet.getInt("review_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment")
                );

                reviewsList.add(review);
            }

            reviewsTable.setItems(reviewsList);
            resultSet.close();

            if (reviewsList.isEmpty()) {
                showReviewError("No reviews found");
            } else {
                showReviewSuccess("Search completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showReviewError("Search failed: " + e.getMessage());
        }
    }

   @FXML
void deleteReview(ActionEvent event) {
    RestaurantReview selectedReview = reviewsTable.getSelectionModel().getSelectedItem();

    if (selectedReview == null) {
        showReviewError("Please select a review to delete");
        return;
    }

    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Delete Review");
    confirmAlert.setHeaderText(null);
    confirmAlert.setContentText("Are you sure you want to delete this review?");

    if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
        return;
    }

    String sql = "DELETE FROM review WHERE review_id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, selectedReview.getReviewId());

        int rows = statement.executeUpdate();

        if (rows > 0) {
            showReviewSuccess("Review deleted successfully");
            clearReviewFields();
            loadReviews();
        }

    } catch (Exception e) {
        e.printStackTrace();
        showReviewError("Delete failed: " + e.getMessage());
    }
}

    @FXML
    void clearReviewFields() {
        reviewsTable.getSelectionModel().clearSelection();
        reviewSearchField.clear();
        reviewMessageLabel.setText("");
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
            showRestaurantError("Cannot open dashboard: " + e.getMessage());
        }
    }


    // ================== VALIDATION ==================

    private boolean validateRestaurantFields(String name, String phone, String ratingText) {
        if (name.isEmpty() || phone.isEmpty() || ratingText.isEmpty()) {
            showRestaurantError("Name, phone, and rating are required");
            return false;
        }

        if (!phone.matches("[0-9]+")) {
            showRestaurantError("Phone must contain numbers only");
            return false;
        }

        double rating;

        try {
            rating = Double.parseDouble(ratingText);
        } catch (NumberFormatException e) {
            showRestaurantError("Rating must be a number");
            return false;
        }

        if (rating < 0 || rating > 5) {
            showRestaurantError("Rating must be between 0 and 5");
            return false;
        }

        return true;
    }

    private boolean validateManagerFields(String name, String phone, Restaurant selectedRestaurant) {
        if (name.isEmpty() || phone.isEmpty() || selectedRestaurant == null) {
            showManagerError("Name, phone, and restaurant are required");
            return false;
        }

        if (!phone.matches("[0-9]+")) {
            showManagerError("Phone must contain numbers only");
            return false;
        }

        if (phone.length() < 7) {
            showManagerError("Phone number is too short");
            return false;
        }

        return true;
    }

    private boolean validateCategoryFields(String categoryName) {
        if (categoryName.isEmpty()) {
            showCategoryError("Category name is required");
            return false;
        }

        if (categoryName.length() < 2) {
            showCategoryError("Category name is too short");
            return false;
        }

        return true;
    }

    private boolean validateMenuItemFields(String name, String priceText, String availability,
                                           Menu selectedMenu, Category selectedCategory) {
        if (name.isEmpty() || priceText.isEmpty() || availability == null
                || selectedMenu == null || selectedCategory == null) {
            showMenuItemError("Name, price, availability, menu, and category are required");
            return false;
        }

        double price;

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showMenuItemError("Price must be a number");
            return false;
        }

        if (price < 0) {
            showMenuItemError("Price cannot be negative");
            return false;
        }

        return true;
    }


    // ================== MESSAGES ==================

    private void showRestaurantError(String message) {
        restaurantMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        restaurantMessageLabel.setText(message);
    }

    private void showRestaurantSuccess(String message) {
        restaurantMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        restaurantMessageLabel.setText(message);
    }

    private void showManagerError(String message) {
        managerMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        managerMessageLabel.setText(message);
    }

    private void showManagerSuccess(String message) {
        managerMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        managerMessageLabel.setText(message);
    }

    private void showCategoryError(String message) {
        categoryMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        categoryMessageLabel.setText(message);
    }

    private void showCategorySuccess(String message) {
        categoryMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        categoryMessageLabel.setText(message);
    }

    private void showMenuError(String message) {
        menuMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        menuMessageLabel.setText(message);
    }

    private void showMenuSuccess(String message) {
        menuMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        menuMessageLabel.setText(message);
    }

    private void showMenuItemError(String message) {
        menuItemMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        menuItemMessageLabel.setText(message);
    }

    private void showMenuItemSuccess(String message) {
        menuItemMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        menuItemMessageLabel.setText(message);
    }

    private void showReviewError(String message) {
        reviewMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
        reviewMessageLabel.setText(message);
    }

    private void showReviewSuccess(String message) {
        reviewMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
        reviewMessageLabel.setText(message);
    }
}