package com.example.db_localisation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.ResourceBundle;

public class DB_Localisation extends Application {

    private ResourceBundle bundle;
    private Locale locale_en = new Locale("en", "US");
    private Locale locale_fa = new Locale("fa", "IR");
    private Locale locale_ja = new Locale("ja", "JP");

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        ComboBox<String> comboBoxLanguage = new ComboBox<>();
        comboBoxLanguage.getItems().addAll("English", "Farsi", "Japanese");
        comboBoxLanguage.setValue("English");

        Label labelChooseLanguage = new Label("Choose Language:");
        GridPane.setConstraints(labelChooseLanguage, 0, 0);
        GridPane.setConstraints(comboBoxLanguage, 1, 0);

        Label labelFirstName = new Label("First Name:");
        TextField inputFirstName = new TextField();
        GridPane.setConstraints(labelFirstName, 0, 1);
        GridPane.setConstraints(inputFirstName, 1, 1);

        Label labelLastName = new Label("Last Name:");
        TextField inputLastName = new TextField();
        GridPane.setConstraints(labelLastName, 0, 2);
        GridPane.setConstraints(inputLastName, 1, 2);

        Label labelEmail = new Label("Email:");
        TextField inputEmail = new TextField();
        GridPane.setConstraints(labelEmail, 0, 3);
        GridPane.setConstraints(inputEmail, 1, 3);

        Button button = new Button("Save");
        GridPane.setConstraints(button, 1, 4);

        comboBoxLanguage.setOnAction(event -> {
            String selectedLanguage = comboBoxLanguage.getValue();
            if (selectedLanguage.equals("Farsi")) {
                bundle = ResourceBundle.getBundle("com.example.db_localisation.messages", locale_fa);
            } else if (selectedLanguage.equals("Japanese")) {
                bundle = ResourceBundle.getBundle("com.example.db_localisation.messages", locale_ja);
            } else {
                //bundle = ResourceBundle.getBundle("messages", locale_en);
                bundle = ResourceBundle.getBundle("com.example.db_localisation.messages", locale_en);

            }
            updateUI(labelFirstName, labelLastName, labelEmail, button, labelChooseLanguage);
        });

        //bundle = ResourceBundle.getBundle("messages", locale_en);
        bundle = ResourceBundle.getBundle("com.example.db_localisation.messages", locale_en);

        updateUI(labelFirstName, labelLastName, labelEmail, button, labelChooseLanguage);

        button.setOnAction(e -> saveData(inputFirstName.getText(), inputLastName.getText(), inputEmail.getText(), comboBoxLanguage.getValue()));

        grid.getChildren().addAll(labelChooseLanguage, comboBoxLanguage, labelFirstName, inputFirstName, labelLastName, inputLastName, labelEmail, inputEmail, button);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setTitle("Database Localization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateUI(Label labelFirstName, Label labelLastName, Label labelEmail, Button button, Label labelChooseLanguage) {
        labelFirstName.setText(bundle.getString("label.firstName"));
        labelLastName.setText(bundle.getString("label.lastName"));
        labelEmail.setText(bundle.getString("label.email"));
        button.setText(bundle.getString("button.save"));
        labelChooseLanguage.setText(bundle.getString("label.chooseLanguage"));
    }

    private void saveData(String firstName, String lastName, String email, String selectedLanguage) {
        // Connection details should be properly managed and secured
        String jdbcUrl = "jdbc:mysql://localhost:3306/localise_db";
        String dbUser = "root";
        String dbPassword = "test";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String tableName = switch (selectedLanguage) {
                    case "Farsi" -> "employee_fa";
                    case "Japanese" -> "employee_ja";
                    default -> "employee_en";
                };

                String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, firstName);
                    statement.setString(2, lastName);
                    statement.setString(3, email);
                    statement.executeUpdate();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText(bundle.getString("message.saved"));
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to save data.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
