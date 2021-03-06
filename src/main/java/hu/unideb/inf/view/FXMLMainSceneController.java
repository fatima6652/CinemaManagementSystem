package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Customers;
import hu.unideb.inf.Model.Data;
import hu.unideb.inf.Model.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLMainSceneController implements Initializable {

    @FXML
    private TextField userNameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginbutton;

    @FXML
    private Button exitButton;

    private ResultSet rs = null;
    private PreparedStatement pst = null;
    @FXML
    private Label noAccountButton;
    @FXML
    private TextField userNameText1;
    @FXML
    private PasswordField passwordText1;
    @FXML
    private Button loginbutton1;

    @FXML
    void ExitButtonClicked(MouseEvent event) {
        BasicFucntions.Exit();
    }

    @FXML
    void NoAccountButtonCicked(MouseEvent evnt) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLSignUpScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign Up Window");
        stage.setScene(new Scene(loader.load()));

        stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);   
        Stage old_win = (Stage) loginbutton.getScene().getWindow();
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        old_win.close();

    }

    @FXML
    void loginButtonClicked(MouseEvent event) throws IOException, SQLException {
        if (isValidated()) {

            String username = userNameText.getText().trim();
            String password = passwordText.getText().trim();

            String sql = "Select * from Customers where username=? and password=?";
            Connection conn =null;
            try {
                conn = MainApp.ConnectToDb();
                
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
               
                String EncryptedPass =BasicFucntions.cryptWithMD5(password);
                if(EncryptedPass==null){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Encryption Error");
                    alert.show();
                    return;
                }
                pst.setString(2, EncryptedPass);

                rs = pst.executeQuery();
                if (rs.next()) {
                    Customers loggedInCust = new Customers(rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("email"),
                            rs.getInt("gender"), username, password);
Data.setLoggedInCustomer(loggedInCust);
                    FXMLLoader fxmlFile = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLDashboardScene.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("MoviesInfo");
                    stage.setScene(new Scene(fxmlFile.load()));
                    stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);   
                    Stage thisWin = (Stage) loginbutton.getScene().getWindow();
                    
                    stage.setResizable(false);
                    stage.show();
                    thisWin.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Autentication Failed");
                    alert.setContentText("Credentials are incorrect");
                    alert.show();
                }

            } catch (SQLException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something Went Wrong. Sorry!!!");
                alert.showAndWait();
                

            } finally {
                if (pst != null) {
                    pst.close();
                   
                }

            }
        }
    }

    
    private boolean isValidated() {
        if ("".equals(userNameText.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is required");
            alert.show();
            userNameText.requestFocus();
            
            
            return false;
        }
        if ("".equals(passwordText.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password is required");
            alert.show();
            
            passwordText.requestFocus();

            return false;
        }
        return true;
    }


    private boolean isValidated1() {
        if ("".equals(userNameText1.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is required");
            alert.show();
            userNameText1.requestFocus();
            
            
            return false;
        }
        if ("".equals(passwordText1.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password is required");
            alert.show();
            
            passwordText1.requestFocus();

            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        

    }
    void initialize(){
    }

    @FXML
    private void loginButton1Clicked(MouseEvent event) throws SQLException, IOException {
        if (isValidated1()) {
            String username = userNameText1.getText().trim();
            String password = passwordText1.getText().trim();

            String sql = "Select * from users where username=? and password=?";
            Connection conn =null;
            try {
                conn = MainApp.ConnectToDb();
                
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
               
                String EncryptedPass =BasicFucntions.cryptWithMD5(password);
                if(EncryptedPass==null){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Encryption Error");
                    alert.show();
                    return;
                }
                pst.setString(2, EncryptedPass);

                rs = pst.executeQuery();
                if (rs.next()) {
                    Users loggedInuser = new Users(rs.getInt("userId"),
                            rs.getString("name"),rs.getString("email"),username, password);

                    FXMLLoader fxmlFile = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLUserDashboardScene.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Dashboard");
                    stage.setScene(new Scene(fxmlFile.load()));
                    stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);   
                    Stage thisWin = (Stage) loginbutton.getScene().getWindow();
                    FXMLUserDashboardSceneController dashboard = fxmlFile.getController();

                    dashboard.setModel(loggedInuser);
                    stage.setResizable(false);
                    stage.show();
                    thisWin.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Autentication Failed");
                    alert.setContentText("Credentials are incorrect");
                    alert.show();
                }

            } catch (SQLException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something Went Wrong. Sorry!!!");
                alert.showAndWait();
                

            } finally {
                if (pst != null) {
                    pst.close();
                   
                }

            }
        
        }
    }
}
