package hellofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button login_button;

    @FXML
    private Hyperlink login_createAccount;

    @FXML
    private BorderPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Hyperlink register_alreadyAccount;

    @FXML
    private Button register_btn;

    @FXML
    private BorderPane register_form;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    public void loginAccount() {
        String selectData = "SELECT username, password FROM user WHERE username = '"
                + login_username.getText() + "' and password = '" + login_password.getText() + "'";
        connect = database.connectDB();

        try {
            if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
                alertMsg("Error", "Please fill all the fields");
            }
            else {
                prepare = connect.prepareStatement(selectData);
                result = prepare.executeQuery();

                if (result.next()) {
                    data.username = login_username.getText();
                    alertMsg("Information", "Successfully logged in");

                    // TO HIDE THE LOGIN FORM
                    login_button.getScene().getWindow().hide();

                    // TO SHOW THE MAIN FORM
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

                        Stage stage = new Stage();
                        stage.setTitle("Planify");
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    alertMsg("Error", "Incorrect Username or Password");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerAccount() {
        String insertData = "INSERT into user (username, password, date) VALUES (?, ?, ?)";
        connect = database.connectDB();

        try {
            if (register_username.getText().isEmpty() || register_password.getText().isEmpty()) {
                alertMsg("Error", "Please fill all the fields");
            }
            else {
                String checkUsername = "SELECT username FROM user WHERE username = '"
                        + register_username.getText() + "'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alertMsg("Error", register_username.getText() + " was already taken");
                }
                else {
                    if (register_password.getText().length() < 8) {
                        alertMsg("Error", "Invalid password, at least 8 characters needed");
                    }
                    else {
                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, register_username.getText());
                        prepare.setString(2, register_password.getText());

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(3, String.valueOf(sqlDate));

                        prepare.executeUpdate();

                        alertMsg("Information", "Successfully registered");

                        register_username.setText("");
                        register_password.setText("");
                        register_form.setVisible(false);
                        login_form.setVisible(true);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void alertMsg(String type, String msg) {
        Map alerts = new HashMap();
        alerts.put("Error", Alert.AlertType.ERROR);
        alerts.put("Information", Alert.AlertType.INFORMATION);

        alert = new Alert((Alert.AlertType) alerts.get(type));
        alert.setTitle(type + " Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == login_createAccount) {
            register_form.setVisible(true);
            login_form.setVisible(false);
        }
        else if (event.getSource() == register_alreadyAccount) {
            register_form.setVisible(false);
            login_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}