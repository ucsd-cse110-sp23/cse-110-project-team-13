import java.io.IOException;
import javax.swing.JTextField;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


//Tests for Logging Into SayIt App
public class LoginTest {

    LoginFrame testLoginFrame;
    String testUser;
    String testPassword; 

    //User inputs nothing trying to login. 
    @Test
    public void testBlankFields() throws Exception {
        //test blank input
        testUser = "";
        testPassword = "";

        //create instance of login frame
        this.testLoginFrame = new LoginFrame();
        assertFalse(testLoginFrame.login(testUser, testPassword));

        //close the frame
        this.testLoginFrame.closeFrame();
    }

    //User tries to login with an invalid account. 
    @Test
    public void testInvalidEmail() throws Exception {
        //make a user that is not registered
        testUser = "TestNewUser";
        testPassword = "TestNewPassword"; 

        //test that the account does not login
        this.testLoginFrame = new LoginFrame();
        assertFalse(testLoginFrame.login(testUser, testPassword)); 

        //close the frame
        this.testLoginFrame.closeFrame();
    } 



    //User logins with the incorrect password. 
    @Test
    public void testWrongPassword() throws Exception {
        //test a valid user but with wrong password login
        testUser = "TestNewUser";
        testPassword = "TestNewPassword";

        //create the account
        Create.addLoginInfo(testUser, testPassword); 

        //test that the account successfully logins
        this.testLoginFrame = new LoginFrame();
        assertFalse(testLoginFrame.login(testUser, "WrongPassword")); 

        //close the frame
        this.testLoginFrame.closeFrame();

        //clear the database
        Delete.clearDatabase();
    }

    //User logins with a valid email. 
    @Test
    public void testValidEmail() throws Exception {
        //make a user
        testUser = "TestNewUser";
        testPassword = "TestNewPassword";

        //register the account
        Create.addLoginInfo(testUser, testPassword); 

        //test that the account successfully logins
        this.testLoginFrame = new LoginFrame();
        assertTrue(testLoginFrame.login(testUser, testPassword)); 

        //close the frame
        this.testLoginFrame.closeFrame();

         //clear the database
         Delete.clearDatabase();
    } 

    /* 
    //User wants to remain automatically logged into SayIt App. 
    @Test
    public void testYesAutomaticLogin() throws Exception {
    }


    //User does not want to remain automatically logged into SayIt App. 
    @Test
    public void testNoAutomaticLogin() throws Exception {
    } */

}